package com.example.data

import com.example.data.model.QuestionEntity
import com.example.data.model.StudyMaterialEntity

data class SubtopicItem(
    val id: String,
    val unitCategory: String,
    val subtopicNumber: Int,
    val title: String,
    val tag: String,
    val subtitle: String = "",
    val youtubeUrl: String = "",
    val youtubeTitle: String = "",
    val duration: String = "45m",
    val dateString: String = "2026 Batch",
    val notesContent: String = "",
    val questions: List<QuestionEntity> = emptyList(),
    val isCustomUserAdded: Boolean = false
)

object SubtopicRepository {

    fun getSubtopicsForCategory(
        category: String,
        allQuestions: List<QuestionEntity>,
        userStudyMaterials: List<StudyMaterialEntity>
    ): List<SubtopicItem> {
        val list = mutableListOf<SubtopicItem>()

        when {
            category.contains("इकाई 1") || category.contains("आधार") -> {
                // Unit 1 Subtopics 1..5 added by user today
                val q1 = allQuestions.filter { it.category == category && it.keyHighlight.contains(DefaultQuestions.UNIT_1_SUBTOPIC_1) }
                val q2 = allQuestions.filter { it.category == category && it.keyHighlight.contains(DefaultQuestions.UNIT_1_SUBTOPIC_2) }
                val q3 = allQuestions.filter { it.category == category && it.keyHighlight.contains(DefaultQuestions.UNIT_1_SUBTOPIC_3) }
                val q4 = allQuestions.filter { it.category == category && it.keyHighlight.contains(DefaultQuestions.UNIT_1_SUBTOPIC_4) }
                val q5 = allQuestions.filter { it.category == category && it.keyHighlight.contains(DefaultQuestions.UNIT_1_SUBTOPIC_5) }

                list.add(
                    SubtopicItem(
                        id = "u1_s1",
                        unitCategory = category,
                        subtopicNumber = 1,
                        title = "पुस्तकालय की मूलभूत अवधारणा एवं परिभाषा",
                        tag = "कक्षा 01",
                        subtitle = "Basic Concepts of Library Science",
                        youtubeUrl = DefaultQuestions.UNIT_1_SUBTOPIC_1_YOUTUBE_URL,
                        youtubeTitle = DefaultQuestions.UNIT_1_SUBTOPIC_1_YOUTUBE_TITLE,
                        duration = "1h 15m",
                        notesContent = DefaultQuestions.getUnit1Subtopic1Notes(),
                        questions = if (q1.isNotEmpty()) q1 else DefaultQuestions.getUnit1Subtopic1Questions()
                    )
                )

                list.add(
                    SubtopicItem(
                        id = "u1_s2",
                        unitCategory = category,
                        subtopicNumber = 2,
                        title = "पुस्तकालय के प्रकार: शैक्षणिक, सार्वजनिक, विशिष्ट व राष्ट्रीय",
                        tag = "कक्षा 02",
                        subtitle = "Types of Library (Academic, Public, Special, National)",
                        youtubeUrl = DefaultQuestions.UNIT_1_SUBTOPIC_2_YOUTUBE_URL,
                        youtubeTitle = DefaultQuestions.UNIT_1_SUBTOPIC_2_YOUTUBE_TITLE,
                        duration = "1h 22m",
                        notesContent = DefaultQuestions.getUnit1Subtopic2Notes(),
                        questions = if (q2.isNotEmpty()) q2 else DefaultQuestions.getUnit1Subtopic2Questions()
                    )
                )

                list.add(
                    SubtopicItem(
                        id = "u1_s3",
                        unitCategory = category,
                        subtopicNumber = 3,
                        title = "सार्वजनिक पुस्तकालय: यूनेस्को घोषणापत्र, विधान एवं सेस",
                        tag = "कक्षा 03",
                        subtitle = "Public Library System & Legislation",
                        youtubeUrl = DefaultQuestions.UNIT_1_SUBTOPIC_3_YOUTUBE_URL,
                        youtubeTitle = DefaultQuestions.UNIT_1_SUBTOPIC_3_YOUTUBE_TITLE,
                        duration = "58m",
                        notesContent = DefaultQuestions.getUnit1Subtopic3Notes(),
                        questions = if (q3.isNotEmpty()) q3 else DefaultQuestions.getUnit1Subtopic3Questions()
                    )
                )

                list.add(
                    SubtopicItem(
                        id = "u1_s4",
                        unitCategory = category,
                        subtopicNumber = 4,
                        title = "भारत का राष्ट्रीय पुस्तकालय: इतिहास, डिलीवरी एक्ट व INB",
                        tag = "कक्षा 04",
                        subtitle = "National Library of India (Kolkata) & INB",
                        youtubeUrl = DefaultQuestions.UNIT_1_SUBTOPIC_4_YOUTUBE_URL,
                        youtubeTitle = DefaultQuestions.UNIT_1_SUBTOPIC_4_YOUTUBE_TITLE,
                        duration = "1h 45m",
                        notesContent = DefaultQuestions.getUnit1Subtopic4Notes(),
                        questions = if (q4.isNotEmpty()) q4 else DefaultQuestions.getUnit1Subtopic4Questions()
                    )
                )

                list.add(
                    SubtopicItem(
                        id = "u1_s5",
                        unitCategory = category,
                        subtopicNumber = 5,
                        title = "विशिष्ट पुस्तकालय: SDI, CAS, अनुवाद सेवा एवं शोध केंद्र",
                        tag = "कक्षा 05",
                        subtitle = "Special Library (ISRO, DRDO, CSIR, ICAR)",
                        youtubeUrl = DefaultQuestions.UNIT_1_SUBTOPIC_5_YOUTUBE_URL,
                        youtubeTitle = DefaultQuestions.UNIT_1_SUBTOPIC_5_YOUTUBE_TITLE,
                        duration = "50m",
                        notesContent = DefaultQuestions.getUnit1Subtopic5Notes(),
                        questions = if (q5.isNotEmpty()) q5 else DefaultQuestions.getUnit1Subtopic5Questions()
                    )
                )
            }
        }

        // Add any user-created study materials from database for this category
        val userCreatedForUnit = userStudyMaterials.filter { it.unitCategory == category }
        userCreatedForUnit.forEachIndexed { idx, mat ->
            val customQuestions = allQuestions.filter { 
                it.category == category && (it.keyHighlight.contains(mat.subTopic) || it.questionHindi.contains(mat.subTopic))
            }
            list.add(
                SubtopicItem(
                    id = "user_mat_${mat.id}",
                    unitCategory = category,
                    subtopicNumber = list.size + 1,
                    title = mat.subTopic.ifBlank { "कक्षा ${list.size + 1}: उपयोगकर्ता सामग्री" },
                    tag = "उपविषय ${list.size + 1}",
                    subtitle = mat.youtubeTitle.ifBlank { "कस्टम अध्ययन सामग्री" },
                    youtubeUrl = mat.youtubeUrl,
                    youtubeTitle = mat.youtubeTitle,
                    duration = "45m",
                    dateString = "नया जोड़ा गया",
                    notesContent = mat.notesContent,
                    questions = customQuestions,
                    isCustomUserAdded = true
                )
            )
        }

        return list
    }
}
