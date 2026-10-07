package com.example.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.AuthManager
import com.example.data.AuthResult
import com.example.data.CloudSyncManager
import com.example.data.DefaultQuestions
import com.example.data.QuestionRepository
import com.example.data.UserProgressStore
import com.example.data.UserSession
import com.example.data.UserSummaryStats
import com.example.data.model.QuestionEntity
import com.example.data.model.QuizAttemptEntity
import com.example.data.model.StudyMaterialEntity
import com.example.util.NetworkMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed interface AppScreen {
    data object Home : AppScreen
    data object CategoryList : AppScreen
    data object QuizPlay : AppScreen
    data object QuizResult : AppScreen
    data object QuestionBank : AppScreen
    data object Bookmarks : AppScreen
    data object Mistakes : AppScreen
    data class StudyMode(val category: String? = null) : AppScreen
    data class UnitSubtopics(val category: String) : AppScreen
}

data class ActiveQuizState(
    val title: String = "",
    val category: String = "",
    val questions: List<QuestionEntity> = emptyList(),
    val currentIndex: Int = 0,
    val userAnswers: Map<Long, Int> = emptyMap(),
    val isSubmitted: Boolean = false,
    val showInstantExplanation: Boolean = true,
    val timeStartedMillis: Long = System.currentTimeMillis()
) {
    val currentQuestion: QuestionEntity?
        get() = questions.getOrNull(currentIndex)

    val currentAnswer: Int?
        get() = currentQuestion?.let { userAnswers[it.id] }

    val correctAnswersCount: Int
        get() = questions.count { q -> userAnswers[q.id] == q.correctOption }

    val wrongAnswersCount: Int
        get() = questions.count { q ->
            val ans = userAnswers[q.id]
            ans != null && ans != q.correctOption
        }

    val unattemptedCount: Int
        get() = questions.size - userAnswers.size

    val progressFraction: Float
        get() = if (questions.isNotEmpty()) (currentIndex + 1).toFloat() / questions.size else 0f
}

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: QuestionRepository
    private val progressStore: UserProgressStore
    private val networkMonitor: NetworkMonitor

    init {
        val database = AppDatabase.getDatabase(application, viewModelScope)
        progressStore = UserProgressStore(application)
        networkMonitor = NetworkMonitor(application)

        repository = QuestionRepository(
            database.questionDao(),
            database.quizAttemptDao(),
            database.studyMaterialDao(),
            progressStore
        )

        viewModelScope.launch {
            repository.syncAllQuestionsAndPreserveProgress()
        }
    }

    // Screen navigation stack
    private val _currentScreen = MutableStateFlow<AppScreen>(AppScreen.Home)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val screenHistory = mutableListOf<AppScreen>()

    // Network
    val isOnline: StateFlow<Boolean> get() = networkMonitor.isOnline

    // Data streams from Room
    val allQuestions: StateFlow<List<QuestionEntity>> = repository.allQuestions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bookmarkedQuestions: StateFlow<List<QuestionEntity>> = repository.bookmarkedQuestions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val mistakeQuestions: StateFlow<List<QuestionEntity>> = repository.mistakeQuestions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userAddedQuestions: StateFlow<List<QuestionEntity>> = repository.userAddedQuestions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val categories: StateFlow<List<String>> = repository.categories
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DefaultQuestions.allCategories)

    val recentAttempts: StateFlow<List<QuizAttemptEntity>> = repository.quizAttempts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allStudyMaterials: StateFlow<List<StudyMaterialEntity>> = repository.allStudyMaterials
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Active Quiz Session
    private val _quizState = MutableStateFlow(ActiveQuizState())
    val quizState: StateFlow<ActiveQuizState> = _quizState.asStateFlow()

    // Status / Feedback message
    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

    fun navigateTo(screen: AppScreen) {
        screenHistory.add(_currentScreen.value)
        _currentScreen.value = screen
    }

    fun navigateToHome() {
        screenHistory.clear()
        _currentScreen.value = AppScreen.Home
    }

    fun navigateBack(): Boolean {
        return if (screenHistory.isNotEmpty()) {
            _currentScreen.value = screenHistory.removeAt(screenHistory.size - 1)
            true
        } else {
            false
        }
    }

    fun clearStatusMessage() {
        _statusMessage.value = null
    }

    // Helper to guarantee every question in an active quiz has a distinct positive ID
    private fun List<QuestionEntity>.ensureUniqueIds(): List<QuestionEntity> {
        val seenIds = mutableSetOf<Long>()
        var nextAvailableId = 1_000_000L
        return this.map { question ->
            if (question.id <= 0L || question.id in seenIds) {
                while (nextAvailableId in seenIds) {
                    nextAvailableId++
                }
                seenIds.add(nextAvailableId)
                val assignedId = nextAvailableId++
                question.copy(id = assignedId)
            } else {
                seenIds.add(question.id)
                question
            }
        }
    }

    // Toggle bookmark for any question
    fun toggleBookmark(question: QuestionEntity) {
        viewModelScope.launch {
            if (question.id > 0L) {
                repository.toggleBookmark(question.id, question.isBookmarked)
            }
            val activeQ = _quizState.value.questions
            if (activeQ.isNotEmpty()) {
                val updated = activeQ.map {
                    if (it.id == question.id || it.questionHindi == question.questionHindi) {
                        it.copy(isBookmarked = !it.isBookmarked)
                    } else it
                }
                _quizState.value = _quizState.value.copy(questions = updated)
            }
        }
    }

    // Start Daily Quiz
    fun startDailyQuiz() {
        viewModelScope.launch {
            val list = repository.getRandomQuestions(10)
            val questions = if (list.isNotEmpty()) list else allQuestions.value.take(10)
            if (questions.isEmpty()) {
                _statusMessage.value = "क्विज़ के लिए प्रश्न लोड हो रहे हैं..."
                return@launch
            }
            _quizState.value = ActiveQuizState(
                title = "दैनिक अभ्यास क्विज़",
                category = "दैनिक अभ्यास",
                questions = questions.ensureUniqueIds(),
                currentIndex = 0,
                userAnswers = emptyMap(),
                isSubmitted = false,
                showInstantExplanation = true
            )
            navigateTo(AppScreen.QuizPlay)
        }
    }

    // Navigate to unit subtopics screen
    fun navigateToUnitSubtopics(categoryName: String) {
        navigateTo(AppScreen.UnitSubtopics(categoryName))
    }

    // Start Subtopic specific quiz / DPP
    fun startSubtopicQuiz(categoryName: String, subTopicTitle: String, questions: List<QuestionEntity>) {
        if (questions.isEmpty()) {
            _statusMessage.value = "इस उप-विषय में अभी कोई प्रश्न उपलब्ध नहीं है।"
            return
        }
        _quizState.value = ActiveQuizState(
            title = "$subTopicTitle • DPP",
            category = categoryName,
            questions = questions.ensureUniqueIds(),
            currentIndex = 0,
            userAnswers = emptyMap(),
            isSubmitted = false,
            showInstantExplanation = true
        )
        navigateTo(AppScreen.QuizPlay)
    }

    // Start Category Quiz
    fun startCategoryQuiz(categoryName: String) {
        viewModelScope.launch {
            val isExtra = DefaultQuestions.isExtraQuestionsUnit(categoryName)
            val filtered = allQuestions.value.filter {
                if (isExtra) DefaultQuestions.isExtraQuestionsUnit(it.category)
                else it.category == categoryName
            }
            if (filtered.isEmpty()) {
                _statusMessage.value = "इस इकाई में अभी कोई प्रश्न उपलब्ध नहीं है।"
                return@launch
            }
            _quizState.value = ActiveQuizState(
                title = categoryName,
                category = categoryName,
                questions = filtered.shuffled().ensureUniqueIds(),
                currentIndex = 0,
                userAnswers = emptyMap(),
                isSubmitted = false,
                showInstantExplanation = true
            )
            navigateTo(AppScreen.QuizPlay)
        }
    }

    // Start Full Mock Test
    fun startFullMockTest() {
        viewModelScope.launch {
            val all = allQuestions.value
            if (all.isEmpty()) {
                _statusMessage.value = "मॉक टेस्ट के लिए प्रश्न लोड हो रहे हैं..."
                return@launch
            }
            val mockQuestions = all.shuffled().take(20)
            _quizState.value = ActiveQuizState(
                title = "सम्पूर्ण पाठ्यक्रम मॉक टेस्ट",
                category = "मॉक टेस्ट",
                questions = mockQuestions.ensureUniqueIds(),
                currentIndex = 0,
                userAnswers = emptyMap(),
                isSubmitted = false,
                showInstantExplanation = true
            )
            navigateTo(AppScreen.QuizPlay)
        }
    }

    // Start Mistakes Revision Quiz
    fun startMistakeRevision() {
        val mistakes = mistakeQuestions.value
        if (mistakes.isEmpty()) {
            _statusMessage.value = "पुनरीक्षण के लिए कोई गलत प्रश्न नहीं है। बहुत खूब!"
            return
        }
        _quizState.value = ActiveQuizState(
            title = "गलत प्रश्नों का सुधार अभ्यास",
            category = "पुनरीक्षण",
            questions = mistakes.shuffled().ensureUniqueIds(),
            currentIndex = 0,
            userAnswers = emptyMap(),
            isSubmitted = false,
            showInstantExplanation = true
        )
        navigateTo(AppScreen.QuizPlay)
    }

    // Start Bookmarked Questions Practice
    fun startBookmarkedQuiz() {
        val saved = bookmarkedQuestions.value
        if (saved.isEmpty()) {
            _statusMessage.value = "आपने अभी तक कोई प्रश्न बुकमार्क नहीं किया है।"
            return
        }
        _quizState.value = ActiveQuizState(
            title = "महत्वपूर्ण प्रश्न (बुकमार्क)",
            category = "बुकमार्क",
            questions = saved.ensureUniqueIds(),
            currentIndex = 0,
            userAnswers = emptyMap(),
            isSubmitted = false,
            showInstantExplanation = true
        )
        navigateTo(AppScreen.QuizPlay)
    }

    // Restart the current quiz session from beginning
    fun restartQuiz() {
        val state = _quizState.value
        _quizState.value = state.copy(
            currentIndex = 0,
            userAnswers = emptyMap(),
            isSubmitted = false,
            timeStartedMillis = System.currentTimeMillis()
        )
        navigateTo(AppScreen.QuizPlay)
    }

    // Re-attempt only the incorrect questions from the current quiz session
    fun retryQuizMistakes() {
        val state = _quizState.value
        val mistakes = state.questions.filter { q ->
            val ans = state.userAnswers[q.id]
            ans != null && ans != q.correctOption
        }
        if (mistakes.isNotEmpty()) {
            _quizState.value = ActiveQuizState(
                title = "${state.title} (गलत प्रश्नों का अभ्यास)",
                category = state.category,
                questions = mistakes.ensureUniqueIds(),
                currentIndex = 0,
                userAnswers = emptyMap(),
                isSubmitted = false,
                showInstantExplanation = true
            )
            navigateTo(AppScreen.QuizPlay)
        } else {
            _statusMessage.value = "इस टेस्ट में कोई गलत प्रश्न नहीं था!"
        }
    }

    // Answer a question in quiz
    fun selectAnswer(optionNumber: Int) {
        val current = _quizState.value.currentQuestion ?: return
        if (_quizState.value.userAnswers.containsKey(current.id)) return // Prevent double-answering

        val currentAnswers = _quizState.value.userAnswers.toMutableMap()
        currentAnswers[current.id] = optionNumber
        _quizState.value = _quizState.value.copy(userAnswers = currentAnswers)

        // Record attempt in database
        if (current.id > 0L) {
            viewModelScope.launch {
                repository.recordQuestionAttempt(current.id, optionNumber)
            }
        }
    }

    fun nextQuestion() {
        val state = _quizState.value
        if (state.currentIndex < state.questions.size - 1) {
            _quizState.value = state.copy(currentIndex = state.currentIndex + 1)
        } else {
            finishQuiz()
        }
    }

    fun previousQuestion() {
        val state = _quizState.value
        if (state.currentIndex > 0) {
            _quizState.value = state.copy(currentIndex = state.currentIndex - 1)
        }
    }

    fun toggleInstantExplanation() {
        _quizState.value = _quizState.value.copy(
            showInstantExplanation = !_quizState.value.showInstantExplanation
        )
    }

    fun finishQuiz() {
        val state = _quizState.value
        if (state.isSubmitted) return
        _quizState.value = state.copy(isSubmitted = true)

        viewModelScope.launch {
            val attempt = QuizAttemptEntity(
                quizTitle = state.title,
                categoryName = state.category,
                totalQuestions = state.questions.size,
                correctCount = state.correctAnswersCount,
                wrongCount = state.wrongAnswersCount
            )
            repository.saveQuizAttempt(attempt)
        }

        while (screenHistory.isNotEmpty() && screenHistory.last() is AppScreen.QuizPlay) {
            screenHistory.removeAt(screenHistory.size - 1)
        }
        _currentScreen.value = AppScreen.QuizResult
    }

    // Start a quiz immediately with custom questions
    fun startQuizWithCustomQuestions(title: String, category: String, questions: List<QuestionEntity>) {
        if (questions.isEmpty()) {
            _statusMessage.value = "क्विज़ के लिए प्रश्न उपलब्ध नहीं हैं।"
            return
        }
        _quizState.value = ActiveQuizState(
            title = title,
            category = category,
            questions = questions.ensureUniqueIds(),
            currentIndex = 0,
            userAnswers = emptyMap(),
            isSubmitted = false,
            showInstantExplanation = true
        )
        navigateTo(AppScreen.QuizPlay)
    }
}
