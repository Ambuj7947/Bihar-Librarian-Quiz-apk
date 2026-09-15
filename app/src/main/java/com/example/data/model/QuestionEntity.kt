package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val category: String,
    val questionHindi: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctOption: Int, // 1 = A, 2 = B, 3 = C, 4 = D
    val explanationHindi: String,
    val keyHighlight: String = "",
    val isBookmarked: Boolean = false,
    val timesAttempted: Int = 0,
    val timesCorrect: Int = 0,
    val lastAttemptOption: Int = 0, // 0 = unattempted
    val dateAddedMillis: Long = System.currentTimeMillis(),
    val isUserAdded: Boolean = false
) {
    val isAnsweredIncorrectly: Boolean
        get() = timesAttempted > 0 && lastAttemptOption != correctOption

    fun getOptionText(optionNumber: Int): String = when (optionNumber) {
        1 -> optionA
        2 -> optionB
        3 -> optionC
        4 -> optionD
        else -> ""
    }
}
