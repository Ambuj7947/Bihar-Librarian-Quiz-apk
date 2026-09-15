package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.DefaultQuestions
import com.example.data.QuestionRepository
import com.example.data.model.QuestionEntity
import com.example.data.model.QuizAttemptEntity
import com.example.data.model.StudyMaterialEntity
import com.example.util.ContentSeparator
import com.example.util.ParsedQuestionItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface AppScreen {
    data object Home : AppScreen
    data object CategoryList : AppScreen
    data object QuizPlay : AppScreen
    data object QuizResult : AppScreen
    data object AddQuestion : AppScreen
    data object ContentCreatorHub : AppScreen
    data object QuestionBank : AppScreen
    data object Bookmarks : AppScreen
    data object Mistakes : AppScreen
    data class StudyMode(val category: String? = null) : AppScreen
}

data class ActiveQuizState(
    val title: String = "",
    val category: String = "",
    val questions: List<QuestionEntity> = emptyList(),
    val currentIndex: Int = 0,
    val userAnswers: Map<Long, Int> = emptyMap(),
    val isSubmitted: Boolean = false,
    val showInstantExplanation: Boolean = true, // By default enabled for study mode
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

    init {
        val database = AppDatabase.getDatabase(application, viewModelScope)
        repository = QuestionRepository(
            database.questionDao(),
            database.quizAttemptDao(),
            database.studyMaterialDao()
        )
    }

    // Screen navigation stack
    private val _currentScreen = MutableStateFlow<AppScreen>(AppScreen.Home)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val screenHistory = mutableListOf<AppScreen>()

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

    // Toggle bookmark for any question
    fun toggleBookmark(question: QuestionEntity) {
        viewModelScope.launch {
            repository.toggleBookmark(question.id, question.isBookmarked)
            // Update active quiz question if currently in quiz
            val activeQ = _quizState.value.questions
            if (activeQ.isNotEmpty()) {
                val updated = activeQ.map {
                    if (it.id == question.id) it.copy(isBookmarked = !it.isBookmarked) else it
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
                _statusMessage.value = "क्विज़ के लिए अभी प्रश्न उपलब्ध नहीं हैं। कृपया 'नया प्रश्न जोड़ें' पर क्लिक करके प्रश्न जोड़ें।"
                return@launch
            }
            _quizState.value = ActiveQuizState(
                title = "दैनिक अभ्यास क्विज़",
                category = "दैनिक अभ्यास",
                questions = questions,
                currentIndex = 0,
                userAnswers = emptyMap(),
                isSubmitted = false,
                showInstantExplanation = true
            )
            navigateTo(AppScreen.QuizPlay)
        }
    }

    // Start Category Quiz
    fun startCategoryQuiz(categoryName: String) {
        viewModelScope.launch {
            val filtered = allQuestions.value.filter { it.category == categoryName }
            if (filtered.isEmpty()) {
                _statusMessage.value = "इस इकाई में अभी कोई प्रश्न नहीं है। कृपया 'नया प्रश्न जोड़ें' से प्रश्न जोड़ें।"
                return@launch
            }
            _quizState.value = ActiveQuizState(
                title = categoryName,
                category = categoryName,
                questions = filtered.shuffled(),
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
                _statusMessage.value = "मॉक टेस्ट शुरू करने के लिए पहले प्रश्न जोड़ें।"
                return@launch
            }
            val mockQuestions = all.shuffled().take(20)
            _quizState.value = ActiveQuizState(
                title = "सम्पूर्ण पाठ्यक्रम मॉक टेस्ट",
                category = "मॉक टेस्ट",
                questions = mockQuestions,
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
            questions = mistakes.shuffled(),
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
            questions = saved,
            currentIndex = 0,
            userAnswers = emptyMap(),
            isSubmitted = false,
            showInstantExplanation = true
        )
        navigateTo(AppScreen.QuizPlay)
    }

    // Answer a question in quiz
    fun selectAnswer(optionNumber: Int) {
        val current = _quizState.value.currentQuestion ?: return
        val currentAnswers = _quizState.value.userAnswers.toMutableMap()
        currentAnswers[current.id] = optionNumber
        _quizState.value = _quizState.value.copy(userAnswers = currentAnswers)

        // Record attempt in database
        viewModelScope.launch {
            repository.recordQuestionAttempt(current.id, optionNumber)
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

        navigateTo(AppScreen.QuizResult)
    }

    // Add New Question by User (Daily basis)
    fun addNewQuestion(
        category: String,
        questionHindi: String,
        optionA: String,
        optionB: String,
        optionC: String,
        optionD: String,
        correctOption: Int,
        explanationHindi: String,
        keyHighlight: String
    ): Boolean {
        if (questionHindi.isBlank() || optionA.isBlank() || optionB.isBlank() ||
            optionC.isBlank() || optionD.isBlank() || explanationHindi.isBlank()
        ) {
            _statusMessage.value = "कृपया प्रश्न, चारों विकल्प एवं व्याख्या भरें।"
            return false
        }

        viewModelScope.launch {
            val newQuestion = QuestionEntity(
                category = category.ifBlank { DefaultQuestions.UNIT_1 },
                questionHindi = questionHindi.trim(),
                optionA = optionA.trim(),
                optionB = optionB.trim(),
                optionC = optionC.trim(),
                optionD = optionD.trim(),
                correctOption = correctOption,
                explanationHindi = explanationHindi.trim(),
                keyHighlight = keyHighlight.trim(),
                isUserAdded = true,
                dateAddedMillis = System.currentTimeMillis()
            )
            repository.insertQuestion(newQuestion)
            _statusMessage.value = "नया प्रश्न सफलतापूर्वक जोड़ा गया!"
        }
        return true
    }

    // Delete question
    fun deleteQuestion(question: QuestionEntity) {
        viewModelScope.launch {
            repository.deleteQuestion(question)
            _statusMessage.value = "प्रश्न हटा दिया गया।"
        }
    }

    // Reset all attempts for a fresh start
    fun resetAllProgress() {
        viewModelScope.launch {
            repository.resetAllAttempts()
            _statusMessage.value = "अभ्यास प्रगति रीसेट कर दी गई।"
        }
    }

    // Save Content Hub Data: Lecture link, Notes, and Quiz Questions
    fun saveContentHubData(
        unitCategory: String,
        subTopic: String,
        youtubeUrl: String,
        youtubeTitle: String,
        timestampNotes: String,
        notesContent: String,
        parsedQuestions: List<ParsedQuestionItem>,
        onSaved: (insertedQuestions: List<QuestionEntity>) -> Unit
    ) {
        viewModelScope.launch {
            val videoId = ContentSeparator.extractYouTubeVideoId(youtubeUrl) ?: ""
            val entities = parsedQuestions.map { it.toQuestionEntity(unitCategory) }

            if (entities.isNotEmpty()) {
                repository.insertQuestions(entities)
            }

            val isExtraUnit = DefaultQuestions.isExtraQuestionsUnit(unitCategory)
            if (!isExtraUnit && (youtubeUrl.isNotBlank() || notesContent.isNotBlank() || entities.isNotEmpty())) {
                val material = StudyMaterialEntity(
                    unitCategory = unitCategory,
                    subTopic = subTopic.trim(),
                    youtubeUrl = youtubeUrl.trim(),
                    youtubeVideoId = videoId,
                    youtubeTitle = youtubeTitle.trim().ifEmpty { "यूट्यूब वीडियो लेक्चर - $unitCategory" },
                    timestampNotes = timestampNotes.trim(),
                    notesContent = notesContent.trim(),
                    questionsCount = entities.size,
                    dateAddedMillis = System.currentTimeMillis()
                )
                repository.insertStudyMaterial(material)
            }

            val successMsg = if (isExtraUnit) {
                "सफलतापूर्वक सहेजा गया: ${entities.size} एक्स्ट्रा प्रश्न जोड़े गए।"
            } else {
                "सफलतापूर्वक सहेजा गया: ${entities.size} प्रश्न क्विज़ में जोड़े गए।"
            }
            _statusMessage.value = successMsg
            onSaved(entities)
        }
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
            questions = questions,
            currentIndex = 0,
            userAnswers = emptyMap(),
            isSubmitted = false,
            showInstantExplanation = true
        )
        navigateTo(AppScreen.QuizPlay)
    }

    // Delete a study material entry
    fun deleteStudyMaterial(material: StudyMaterialEntity) {
        viewModelScope.launch {
            repository.deleteStudyMaterial(material)
            _statusMessage.value = "अध्ययन सामग्री हटा दी गई।"
        }
    }
}
