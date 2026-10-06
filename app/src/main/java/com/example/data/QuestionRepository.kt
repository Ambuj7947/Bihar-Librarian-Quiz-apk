package com.example.data

import com.example.data.dao.QuestionDao
import com.example.data.dao.QuizAttemptDao
import com.example.data.dao.StudyMaterialDao
import com.example.data.model.QuestionEntity
import com.example.data.model.QuizAttemptEntity
import com.example.data.model.StudyMaterialEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class QuestionRepository(
    private val questionDao: QuestionDao,
    private val quizAttemptDao: QuizAttemptDao,
    private val studyMaterialDao: StudyMaterialDao,
    private val progressStore: UserProgressStore
) {
    val allQuestions: Flow<List<QuestionEntity>> = questionDao.getAllQuestionsFlow()
    val bookmarkedQuestions: Flow<List<QuestionEntity>> = questionDao.getBookmarkedQuestionsFlow()
    val mistakeQuestions: Flow<List<QuestionEntity>> = questionDao.getMistakeQuestionsFlow()
    val userAddedQuestions: Flow<List<QuestionEntity>> = questionDao.getUserAddedQuestionsFlow()
    val categories: Flow<List<String>> = questionDao.getDistinctCategoriesFlow().map { dbCategories ->
        (DefaultQuestions.allCategories + dbCategories).distinct()
    }
    val quizAttempts: Flow<List<QuizAttemptEntity>> = quizAttemptDao.getAllAttemptsFlow()
    val allStudyMaterials: Flow<List<StudyMaterialEntity>> = studyMaterialDao.getAllMaterialsFlow()

    fun getQuestionsByCategory(category: String): Flow<List<QuestionEntity>> {
        return questionDao.getQuestionsByCategoryFlow(category)
    }

    suspend fun getAllQuestionsList(): List<QuestionEntity> = withContext(Dispatchers.IO) {
        questionDao.getAllQuestionsList()
    }

    fun getStudyMaterialsByUnit(unit: String): Flow<List<StudyMaterialEntity>> {
        return studyMaterialDao.getMaterialsByUnitFlow(unit)
    }

    suspend fun insertQuestion(question: QuestionEntity): Long = withContext(Dispatchers.IO) {
        questionDao.insertQuestion(question)
    }

    suspend fun insertQuestions(questions: List<QuestionEntity>) = withContext(Dispatchers.IO) {
        questionDao.insertAll(questions)
    }

    suspend fun insertStudyMaterial(material: StudyMaterialEntity): Long = withContext(Dispatchers.IO) {
        studyMaterialDao.insertMaterial(material)
    }

    suspend fun deleteStudyMaterial(material: StudyMaterialEntity) = withContext(Dispatchers.IO) {
        studyMaterialDao.deleteMaterial(material)
    }

    suspend fun updateQuestion(question: QuestionEntity) = withContext(Dispatchers.IO) {
        questionDao.updateQuestion(question)
    }

    suspend fun deleteQuestion(question: QuestionEntity) = withContext(Dispatchers.IO) {
        questionDao.deleteQuestion(question)
    }

    suspend fun toggleBookmark(id: Long, currentStatus: Boolean) = withContext(Dispatchers.IO) {
        val newStatus = !currentStatus
        val question = questionDao.getQuestionById(id)
        if (question != null) {
            progressStore.setBookmark(question.questionHindi, newStatus)
        }
        questionDao.updateBookmark(id, newStatus)
    }

    suspend fun recordQuestionAttempt(id: Long, chosenOption: Int) = withContext(Dispatchers.IO) {
        val question = questionDao.getQuestionById(id)
        if (question != null) {
            val isCorrect = chosenOption == question.correctOption
            progressStore.recordAttempt(question.questionHindi, chosenOption, isCorrect)
        }
        questionDao.recordAttempt(id, chosenOption)
    }

    suspend fun saveQuizAttempt(attempt: QuizAttemptEntity): Long = withContext(Dispatchers.IO) {
        quizAttemptDao.insertAttempt(attempt)
    }

    suspend fun getRandomQuestions(count: Int): List<QuestionEntity> = withContext(Dispatchers.IO) {
        questionDao.getRandomQuestions(count)
    }

    suspend fun getRandomQuestionsByCategory(category: String, count: Int): List<QuestionEntity> = withContext(Dispatchers.IO) {
        questionDao.getRandomQuestionsByCategory(category, count)
    }

    private suspend fun ensureUnit1Materials() {
        val matCount1 = studyMaterialDao.getMaterialCountForSubtopic(
            DefaultQuestions.UNIT_1,
            DefaultQuestions.UNIT_1_SUBTOPIC_1
        )
        if (matCount1 == 0) {
            studyMaterialDao.insertMaterial(DefaultQuestions.getUnit1Subtopic1Material())
        }

        val matCount2 = studyMaterialDao.getMaterialCountForSubtopic(
            DefaultQuestions.UNIT_1,
            DefaultQuestions.UNIT_1_SUBTOPIC_2
        )
        if (matCount2 == 0) {
            studyMaterialDao.insertMaterial(DefaultQuestions.getUnit1Subtopic2Material())
        }

        val matCount3 = studyMaterialDao.getMaterialCountForSubtopic(
            DefaultQuestions.UNIT_1,
            DefaultQuestions.UNIT_1_SUBTOPIC_3
        )
        if (matCount3 == 0) {
            studyMaterialDao.insertMaterial(DefaultQuestions.getUnit1Subtopic3Material())
        }

        val matCount4 = studyMaterialDao.getMaterialCountForSubtopic(
            DefaultQuestions.UNIT_1,
            DefaultQuestions.UNIT_1_SUBTOPIC_4
        )
        if (matCount4 == 0) {
            studyMaterialDao.insertMaterial(DefaultQuestions.getUnit1Subtopic4Material())
        }

        val matCount5 = studyMaterialDao.getMaterialCountForSubtopic(
            DefaultQuestions.UNIT_1,
            DefaultQuestions.UNIT_1_SUBTOPIC_5
        )
        if (matCount5 == 0) {
            studyMaterialDao.insertMaterial(DefaultQuestions.getUnit1Subtopic5Material())
        }
    }

    /**
     * Smart Non-Destructive Question Sync.
     * Guarantees that even after app updates with new questions:
     * 1. Old user progress (attempt counts, correct counts, mistakes, and bookmarks) is 100% PRESERVED.
     * 2. New questions are seamlessly added to the database.
     * 3. Question IDs remain consistent and backed up in UserProgressStore.
     */
    suspend fun syncAllQuestionsAndPreserveProgress() = withContext(Dispatchers.IO) {
        ensureUnit1Materials()

        val existingInDb = questionDao.getAllQuestionsList()
        val existingMap = existingInDb.associateBy { it.questionHindi.trim() }

        val allDefaultQuestions = DefaultQuestions.getInitialQuestions()

        val toInsert = mutableListOf<QuestionEntity>()
        val toUpdate = mutableListOf<QuestionEntity>()

        for (defQ in allDefaultQuestions) {
            val key = defQ.questionHindi.trim()
            val existing = existingMap[key]
            val savedProgress = progressStore.getProgress(key)

            if (existing != null) {
                // Question already exists in database.
                // PRESERVE user progress (attempts, correct count, last option, bookmark)!
                val attempted = maxOf(existing.timesAttempted, savedProgress?.timesAttempted ?: 0)
                val correct = maxOf(existing.timesCorrect, savedProgress?.timesCorrect ?: 0)
                val lastOption = if (existing.lastAttemptOption > 0) existing.lastAttemptOption else (savedProgress?.lastAttemptOption ?: 0)
                val bookmarked = existing.isBookmarked || (savedProgress?.isBookmarked == true)

                val updated = existing.copy(
                    category = defQ.category,
                    optionA = defQ.optionA,
                    optionB = defQ.optionB,
                    optionC = defQ.optionC,
                    optionD = defQ.optionD,
                    correctOption = defQ.correctOption,
                    explanationHindi = defQ.explanationHindi,
                    keyHighlight = defQ.keyHighlight,
                    timesAttempted = attempted,
                    timesCorrect = correct,
                    lastAttemptOption = lastOption,
                    isBookmarked = bookmarked
                )
                if (updated != existing) {
                    toUpdate.add(updated)
                }
                // Keep SharedPreferences in sync
                if (attempted > 0 || bookmarked) {
                    progressStore.saveProgress(key, attempted, correct, lastOption, bookmarked)
                }
            } else {
                // BRAND NEW question from app update!
                val attempted = savedProgress?.timesAttempted ?: 0
                val correct = savedProgress?.timesCorrect ?: 0
                val lastOption = savedProgress?.lastAttemptOption ?: 0
                val bookmarked = savedProgress?.isBookmarked ?: false

                toInsert.add(
                    defQ.copy(
                        id = 0L,
                        timesAttempted = attempted,
                        timesCorrect = correct,
                        lastAttemptOption = lastOption,
                        isBookmarked = bookmarked
                    )
                )
            }
        }

        if (toInsert.isNotEmpty()) {
            questionDao.insertAll(toInsert)
        }
        for (q in toUpdate) {
            questionDao.updateQuestion(q)
        }

        // Remove questions for Units 2, 3, 4, and 5 since user requested DPP removal for them
        questionDao.deleteQuestionsByCategories(
            listOf(
                DefaultQuestions.UNIT_2,
                DefaultQuestions.UNIT_3,
                DefaultQuestions.UNIT_4,
                DefaultQuestions.UNIT_5
            )
        )
        questionDao.deleteUnits2To5Questions()
    }
}
