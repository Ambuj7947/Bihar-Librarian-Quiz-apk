package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.model.QuizAttemptEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizAttemptDao {
    @Query("SELECT * FROM quiz_attempts ORDER BY timestamp DESC")
    fun getAllAttemptsFlow(): Flow<List<QuizAttemptEntity>>

    @Query("SELECT * FROM quiz_attempts ORDER BY timestamp DESC LIMIT 5")
    fun getRecentAttemptsFlow(): Flow<List<QuizAttemptEntity>>

    @Insert
    suspend fun insertAttempt(attempt: QuizAttemptEntity): Long

    @Query("SELECT COUNT(*) FROM quiz_attempts")
    suspend fun getAttemptsCount(): Int

    @Query("DELETE FROM quiz_attempts")
    suspend fun clearHistory()
}
