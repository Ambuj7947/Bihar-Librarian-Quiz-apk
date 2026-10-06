package com.example.data

import android.content.Context
import android.util.Log
import com.example.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

data class UserSummaryStats(
    val totalAttempted: Int = 0,
    val totalCorrect: Int = 0,
    val accuracy: Int = 0
)

data class CloudProgressItem(
    val questionKey: String = "",
    val timesAttempted: Int = 0,
    val timesCorrect: Int = 0,
    val lastAttemptOption: Int = 0,
    val isBookmarked: Boolean = false
)

data class CloudProgressBundle(
    val userId: String = "",
    val displayName: String = "",
    val totalAttempted: Int = 0,
    val totalCorrect: Int = 0,
    val accuracy: Int = 0,
    val lastSyncMillis: Long = 0L,
    val progressItems: List<CloudProgressItem> = emptyList()
)

class CloudSyncManager(private val context: Context) {

    private val db: FirebaseFirestore by lazy {
        val databaseId = context.getString(R.string.firestore_database_id)
        FirebaseFirestore.getInstance(databaseId)
    }

    /**
     * Upload user learning progress, stats, and individual question history to Firestore.
     * Backed by Firestore offline persistence so data is stored locally if offline,
     * and automatically dispatched to cloud as soon as the device comes online.
     */
    suspend fun syncProgressToCloud(
        userId: String,
        displayName: String,
        emailOrId: String,
        stats: UserSummaryStats,
        progressMap: Map<String, QuestionProgressRecord>
    ): Boolean = withContext(Dispatchers.IO) {
        if (userId.isBlank()) return@withContext false

        try {
            val userDocRef = db.collection("users").document(userId)

            val profileData = hashMapOf(
                "userId" to userId,
                "displayName" to displayName,
                "email" to emailOrId,
                "totalAttempted" to stats.totalAttempted,
                "totalCorrect" to stats.totalCorrect,
                "accuracy" to stats.accuracy,
                "lastSyncMillis" to System.currentTimeMillis()
            )

            // Save summary profile
            userDocRef.set(profileData, SetOptions.merge()).await()

            // Save question items in batch or subcollection
            val progressList = progressMap.entries.map { (key, record) ->
                hashMapOf(
                    "questionKey" to key,
                    "timesAttempted" to record.timesAttempted,
                    "timesCorrect" to record.timesCorrect,
                    "lastAttemptOption" to record.lastAttemptOption,
                    "isBookmarked" to record.isBookmarked,
                    "lastUpdatedMillis" to record.lastUpdatedMillis
                )
            }

            if (progressList.isNotEmpty()) {
                val progressDoc = userDocRef.collection("progress").document("all_questions")
                progressDoc.set(mapOf("items" to progressList), SetOptions.merge()).await()
            }

            Log.d(TAG, "Successfully synced ${progressMap.size} questions to cloud for $userId")
            true
        } catch (e: Exception) {
            Log.w(TAG, "Cloud sync queued or failed (will sync when online): ${e.message}")
            false
        }
    }

    /**
     * Download saved progress from Firestore when user logs in on a new device or updated app.
     */
    suspend fun restoreProgressFromCloud(userId: String): CloudProgressBundle? = withContext(Dispatchers.IO) {
        if (userId.isBlank()) return@withContext null

        try {
            val userDocRef = db.collection("users").document(userId)
            val snapshot = userDocRef.get().await()

            if (!snapshot.exists()) {
                Log.d(TAG, "No remote profile found for $userId")
                return@withContext null
            }

            val displayName = snapshot.getString("displayName") ?: ""
            val totalAttempted = snapshot.getLong("totalAttempted")?.toInt() ?: 0
            val totalCorrect = snapshot.getLong("totalCorrect")?.toInt() ?: 0
            val accuracy = snapshot.getLong("accuracy")?.toInt() ?: 0
            val lastSyncMillis = snapshot.getLong("lastSyncMillis") ?: 0L

            val items = mutableListOf<CloudProgressItem>()
            try {
                val progressDoc = userDocRef.collection("progress").document("all_questions").get().await()
                if (progressDoc.exists()) {
                    val rawList = progressDoc.get("items") as? List<Map<String, Any>>
                    rawList?.forEach { map ->
                        val key = map["questionKey"] as? String ?: ""
                        if (key.isNotBlank()) {
                            items.add(
                                CloudProgressItem(
                                    questionKey = key,
                                    timesAttempted = (map["timesAttempted"] as? Number)?.toInt() ?: 0,
                                    timesCorrect = (map["timesCorrect"] as? Number)?.toInt() ?: 0,
                                    lastAttemptOption = (map["lastAttemptOption"] as? Number)?.toInt() ?: 0,
                                    isBookmarked = map["isBookmarked"] as? Boolean ?: false
                                )
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                Log.w(TAG, "Could not fetch detailed progress items: ${e.message}")
            }

            CloudProgressBundle(
                userId = userId,
                displayName = displayName,
                totalAttempted = totalAttempted,
                totalCorrect = totalCorrect,
                accuracy = accuracy,
                lastSyncMillis = lastSyncMillis,
                progressItems = items
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to restore progress from cloud: ${e.message}", e)
            null
        }
    }

    companion object {
        private const val TAG = "CloudSyncManager"
    }
}
