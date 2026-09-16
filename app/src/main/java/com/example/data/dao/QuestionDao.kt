package com.example.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions ORDER BY id ASC")
    fun getAllQuestionsFlow(): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE category = :category ORDER BY id ASC")
    fun getQuestionsByCategoryFlow(category: String): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE category = :category ORDER BY id ASC")
    suspend fun getQuestionsByCategory(category: String): List<QuestionEntity>

    @Query("SELECT * FROM questions WHERE isBookmarked = 1 ORDER BY id DESC")
    fun getBookmarkedQuestionsFlow(): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE timesAttempted > 0 AND lastAttemptOption != correctOption ORDER BY id DESC")
    fun getMistakeQuestionsFlow(): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE isUserAdded = 1 ORDER BY dateAddedMillis DESC")
    fun getUserAddedQuestionsFlow(): Flow<List<QuestionEntity>>

    @Query("SELECT DISTINCT category FROM questions ORDER BY category ASC")
    fun getDistinctCategoriesFlow(): Flow<List<String>>

    @Query("SELECT COUNT(*) FROM questions")
    suspend fun getQuestionCount(): Int

    @Query("SELECT * FROM questions ORDER BY RANDOM() LIMIT :count")
    suspend fun getRandomQuestions(count: Int): List<QuestionEntity>

    @Query("SELECT * FROM questions WHERE category = :category ORDER BY RANDOM() LIMIT :count")
    suspend fun getRandomQuestionsByCategory(category: String, count: Int): List<QuestionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: QuestionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<QuestionEntity>)

    @Update
    suspend fun updateQuestion(question: QuestionEntity)

    @Delete
    suspend fun deleteQuestion(question: QuestionEntity)

    @Query("UPDATE questions SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun updateBookmark(id: Long, isBookmarked: Boolean)

    @Query("""
        UPDATE questions 
        SET timesAttempted = timesAttempted + 1,
            timesCorrect = timesCorrect + (CASE WHEN :chosenOption = correctOption THEN 1 ELSE 0 END),
            lastAttemptOption = :chosenOption
        WHERE id = :id
    """)
    suspend fun recordAttempt(id: Long, chosenOption: Int)

    @Query("UPDATE questions SET timesAttempted = 0, timesCorrect = 0, lastAttemptOption = 0")
    suspend fun resetAllAttempts()

    @Query("DELETE FROM questions")
    suspend fun deleteAllQuestions()

    @Query("DELETE FROM questions WHERE category NOT IN (:validCategories)")
    suspend fun deleteQuestionsNotInCategories(validCategories: List<String>)

    @Query("DELETE FROM questions WHERE category LIKE '%इकाई 6%' OR category LIKE '%एक्स्ट्रा%' OR category LIKE '%Extra%'")
    suspend fun deleteUnit6Questions()
}
