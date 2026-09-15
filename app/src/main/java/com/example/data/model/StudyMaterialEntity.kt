package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_materials")
data class StudyMaterialEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val unitCategory: String,
    val subTopic: String = "",
    val youtubeUrl: String = "",
    val youtubeVideoId: String = "",
    val youtubeTitle: String = "",
    val timestampNotes: String = "",
    val notesContent: String = "",
    val questionsCount: Int = 0,
    val dateAddedMillis: Long = System.currentTimeMillis()
)
