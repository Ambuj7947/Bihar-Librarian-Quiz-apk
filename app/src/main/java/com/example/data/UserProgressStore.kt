package com.example.data

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONObject

data class QuestionProgressRecord(
    val timesAttempted: Int = 0,
    val timesCorrect: Int = 0,
    val lastAttemptOption: Int = 0,
    val isBookmarked: Boolean = false,
    val lastUpdatedMillis: Long = System.currentTimeMillis()
)

/**
 * Persistent storage for user practice progress and bookmarks.
 * Backed by SharedPreferences so user progress is NEVER lost even when:
 * 1. The app is updated with a new version containing new questions.
 * 2. Room database tables are synced or rebuilt.
 * 3. Question IDs change.
 */
class UserProgressStore(context: Context) {

    private val prefs: SharedPreferences = context.applicationContext.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    fun getProgress(questionHindi: String): QuestionProgressRecord? {
        val key = normalizeKey(questionHindi)
        val raw = prefs.getString(key, null) ?: return null
        return try {
            val json = JSONObject(raw)
            QuestionProgressRecord(
                timesAttempted = json.optInt("timesAttempted", 0),
                timesCorrect = json.optInt("timesCorrect", 0),
                lastAttemptOption = json.optInt("lastAttemptOption", 0),
                isBookmarked = json.optBoolean("isBookmarked", false),
                lastUpdatedMillis = json.optLong("lastUpdatedMillis", 0L)
            )
        } catch (e: Exception) {
            null
        }
    }

    fun saveProgress(
        questionHindi: String,
        timesAttempted: Int,
        timesCorrect: Int,
        lastAttemptOption: Int,
        isBookmarked: Boolean
    ) {
        val key = normalizeKey(questionHindi)
        val json = JSONObject().apply {
            put("timesAttempted", timesAttempted)
            put("timesCorrect", timesCorrect)
            put("lastAttemptOption", lastAttemptOption)
            put("isBookmarked", isBookmarked)
            put("lastUpdatedMillis", System.currentTimeMillis())
        }
        prefs.edit().putString(key, json.toString()).apply()
    }

    fun recordAttempt(questionHindi: String, chosenOption: Int, isCorrect: Boolean) {
        val existing = getProgress(questionHindi) ?: QuestionProgressRecord()
        val updatedAttempted = existing.timesAttempted + 1
        val updatedCorrect = existing.timesCorrect + (if (isCorrect) 1 else 0)
        saveProgress(
            questionHindi = questionHindi,
            timesAttempted = updatedAttempted,
            timesCorrect = updatedCorrect,
            lastAttemptOption = chosenOption,
            isBookmarked = existing.isBookmarked
        )
    }

    fun setBookmark(questionHindi: String, isBookmarked: Boolean) {
        val existing = getProgress(questionHindi) ?: QuestionProgressRecord()
        saveProgress(
            questionHindi = questionHindi,
            timesAttempted = existing.timesAttempted,
            timesCorrect = existing.timesCorrect,
            lastAttemptOption = existing.lastAttemptOption,
            isBookmarked = isBookmarked
        )
    }

    fun getAllProgress(): Map<String, QuestionProgressRecord> {
        val result = mutableMapOf<String, QuestionProgressRecord>()
        val allEntries = prefs.all
        for ((k, v) in allEntries) {
            if (k.startsWith("q_") && v is String) {
                try {
                    val json = JSONObject(v)
                    result[k] = QuestionProgressRecord(
                        timesAttempted = json.optInt("timesAttempted", 0),
                        timesCorrect = json.optInt("timesCorrect", 0),
                        lastAttemptOption = json.optInt("lastAttemptOption", 0),
                        isBookmarked = json.optBoolean("isBookmarked", false),
                        lastUpdatedMillis = json.optLong("lastUpdatedMillis", 0L)
                    )
                } catch (_: Exception) {}
            }
        }
        return result
    }

    private fun normalizeKey(text: String): String {
        val trimmed = text.trim()
        val hash = (trimmed.hashCode().toLong() and 0xFFFFFFFFL).toString(16)
        val length = trimmed.length
        return "q_${hash}_${length}"
    }

    companion object {
        private const val PREFS_NAME = "blet_user_learning_progress_v1"
    }
}
