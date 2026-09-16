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
    private val studyMaterialDao: StudyMaterialDao
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

    fun getStudyMaterialsByUnit(unit: String): Flow<List<StudyMaterialEntity>> {
        return studyMaterialDao.getMaterialsByUnitFlow(unit)
    }

    suspend fun resetToFreshUnits() = withContext(Dispatchers.IO) {
        // Clear all previous pre-seeded questions, notes, attempts, and old categories
        questionDao.deleteAllQuestions()
        quizAttemptDao.clearHistory()
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
        questionDao.updateBookmark(id, !currentStatus)
    }

    suspend fun recordQuestionAttempt(id: Long, chosenOption: Int) = withContext(Dispatchers.IO) {
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

    suspend fun resetAllAttempts() = withContext(Dispatchers.IO) {
        questionDao.resetAllAttempts()
    }

    suspend fun ensureUnit1Seed() = withContext(Dispatchers.IO) {
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

        val existingQuestions = questionDao.getQuestionsByCategory(DefaultQuestions.UNIT_1)
        val hasQ1 = existingQuestions.any { it.keyHighlight.contains(DefaultQuestions.UNIT_1_SUBTOPIC_1) }
        if (!hasQ1) {
            questionDao.insertAll(DefaultQuestions.getUnit1Subtopic1Questions())
        }

        val hasQ2 = existingQuestions.any { it.keyHighlight.contains(DefaultQuestions.UNIT_1_SUBTOPIC_2) }
        if (!hasQ2) {
            questionDao.insertAll(DefaultQuestions.getUnit1Subtopic2Questions())
        }

        val hasQ3 = existingQuestions.any { it.keyHighlight.contains(DefaultQuestions.UNIT_1_SUBTOPIC_3) }
        if (!hasQ3) {
            questionDao.insertAll(DefaultQuestions.getUnit1Subtopic3Questions())
        }

        val hasQ4 = existingQuestions.any { it.keyHighlight.contains(DefaultQuestions.UNIT_1_SUBTOPIC_4) }
        if (!hasQ4) {
            questionDao.insertAll(DefaultQuestions.getUnit1Subtopic4Questions())
        }

        val hasQ5 = existingQuestions.any { it.keyHighlight.contains(DefaultQuestions.UNIT_1_SUBTOPIC_5) }
        if (!hasQ5) {
            questionDao.insertAll(DefaultQuestions.getUnit1Subtopic5Questions())
        }
    }

    suspend fun ensureUnit1Subtopic1Seed() = ensureUnit1Seed()
}
