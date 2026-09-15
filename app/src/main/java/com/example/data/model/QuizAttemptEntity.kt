package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_attempts")
data class QuizAttemptEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val quizTitle: String,
    val categoryName: String, // "ALL" or specific category
    val totalQuestions: Int,
    val correctCount: Int,
    val wrongCount: Int,
    val timestamp: Long = System.currentTimeMillis()
) {
    val percentage: Int
        get() = if (totalQuestions > 0) (correctCount * 100) / totalQuestions else 0
}
