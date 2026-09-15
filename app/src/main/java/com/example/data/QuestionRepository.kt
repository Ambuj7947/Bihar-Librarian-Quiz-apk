package com.example.data

import com.example.data.dao.QuestionDao
import com.example.data.dao.QuizAttemptDao
import com.example.data.model.QuestionEntity
import com.example.data.model.QuizAttemptEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class QuestionRepository(
    private val questionDao: QuestionDao,
    private val quizAttemptDao: QuizAttemptDao
) {
    val allQuestions: Flow<List<QuestionEntity>> = questionDao.getAllQuestionsFlow()
    val bookmarkedQuestions: Flow<List<QuestionEntity>> = questionDao.getBookmarkedQuestionsFlow()
    val mistakeQuestions: Flow<List<QuestionEntity>> = questionDao.getMistakeQuestionsFlow()
    val userAddedQuestions: Flow<List<QuestionEntity>> = questionDao.getUserAddedQuestionsFlow()
    val categories: Flow<List<String>> = questionDao.getDistinctCategoriesFlow().map { dbCategories ->
        (DefaultQuestions.allCategories + dbCategories).distinct()
    }
    val quizAttempts: Flow<List<QuizAttemptEntity>> = quizAttemptDao.getAllAttemptsFlow()

    fun getQuestionsByCategory(category: String): Flow<List<QuestionEntity>> {
        return questionDao.getQuestionsByCategoryFlow(category)
    }

    suspend fun resetToFreshUnits() = withContext(Dispatchers.IO) {
        // Clear all previous pre-seeded questions, notes, attempts, and old categories
        questionDao.deleteAllQuestions()
        quizAttemptDao.clearHistory()
    }

    suspend fun insertQuestion(question: QuestionEntity): Long = withContext(Dispatchers.IO) {
        questionDao.insertQuestion(question)
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
}
