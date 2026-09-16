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

            category.contains("इकाई 6") || category.contains("एक्स्ट्रा") || category.contains("Extra") -> {
                // Unit 6 Extra Questions - 8 Practice Sets (80 MCQs)
                val qSet1 = allQuestions.filter { it.category == category && it.keyHighlight.contains("सेट 1") }
                val qSet2 = allQuestions.filter { it.category == category && it.keyHighlight.contains("सेट 2") }
                val qSet3 = allQuestions.filter { it.category == category && it.keyHighlight.contains("सेट 3") }
                val qSet4 = allQuestions.filter { it.category == category && it.keyHighlight.contains("सेट 4") }
                val qSet5 = allQuestions.filter { it.category == category && it.keyHighlight.contains("सेट 5") }
                val qSet6 = allQuestions.filter { it.category == category && it.keyHighlight.contains("सेट 6") }
                val qSet7 = allQuestions.filter { it.category == category && it.keyHighlight.contains("सेट 7") }
                val qSet8 = allQuestions.filter { it.category == category && it.keyHighlight.contains("सेट 8") }

                list.add(
                    SubtopicItem(
                        id = "u6_s1",
                        unitCategory = category,
                        subtopicNumber = 1,
                        title = "सेट 1: पुस्तकालय विज्ञान की मूलभूत अवधारणा",
                        tag = "प्रैक्टिस सेट 01",
                        subtitle = "10 बहुविकल्पीय अभ्यास प्रश्न (MCQs)",
                        duration = "10 प्रश्न",
                        notesContent = "इस सेट में पुस्तकालय शब्द की व्युत्पत्ति, सामाजिक संस्थान, NAPLIS 1985, ओपन एक्सेस और राष्ट्रीय ज्ञान आयोग पर आधारित 10 महत्वपूर्ण प्रश्न संकलित हैं।",
                        questions = if (qSet1.isNotEmpty()) qSet1 else Unit6Questions.getSet1Questions()
                    )
                )
                list.add(
                    SubtopicItem(
                        id = "u6_s2",
                        unitCategory = category,
                        subtopicNumber = 2,
                        title = "सेट 2: पुस्तकालय आधार एवं पाँच सूत्र",
                        tag = "प्रैक्टिस सेट 02",
                        subtitle = "10 बहुविकल्पीय अभ्यास प्रश्न (MCQs)",
                        duration = "10 प्रश्न",
                        notesContent = "डॉ. एस. आर. रंगनाथन द्वारा 1928 में प्रतिपादित Five Laws of Library Science पर आधारित 10 विशिष्ट परीक्षा उपयोगी प्रश्न।",
                        questions = if (qSet2.isNotEmpty()) qSet2 else Unit6Questions.getSet2Questions()
                    )
                )
                list.add(
                    SubtopicItem(
                        id = "u6_s3",
                        unitCategory = category,
                        subtopicNumber = 3,
                        title = "सेट 3: सार्वजनिक पुस्तकालय एवं विधान",
                        tag = "प्रैक्टिस सेट 03",
                        subtitle = "10 बहुविकल्पीय अभ्यास प्रश्न (MCQs)",
                        duration = "10 प्रश्न",
                        notesContent = "यूनेस्को पब्लिक लाइब्रेरी मेनिफेस्टो, भारत के पुस्तकालय अधिनियम, सेस एवं RRRLF कोलकाता पर 10 बहुविकल्पीय प्रश्न।",
                        questions = if (qSet3.isNotEmpty()) qSet3 else Unit6Questions.getSet3Questions()
                    )
                )
                list.add(
                    SubtopicItem(
                        id = "u6_s4",
                        unitCategory = category,
                        subtopicNumber = 4,
                        title = "सेट 4: UNESCO, IFLA एवं समितियां",
                        tag = "प्रैक्टिस सेट 04",
                        subtitle = "10 बहुविकल्पीय अभ्यास प्रश्न (MCQs)",
                        duration = "10 प्रश्न",
                        notesContent = "IFLA, UGC पुस्तकालय समिति 1957, राधाकृष्णन व कोठारी आयोग, INFLIBNET और NML पर 10 महत्वपूर्ण प्रश्न।",
                        questions = if (qSet4.isNotEmpty()) qSet4 else Unit6Questions.getSet4Questions()
                    )
                )
                list.add(
                    SubtopicItem(
                        id = "u6_s5",
                        unitCategory = category,
                        subtopicNumber = 5,
                        title = "सेट 5: विशिष्ट पुस्तकालय एवं सूचना सेवाएं",
                        tag = "प्रैक्टिस सेट 05",
                        subtitle = "10 बहुविकल्पीय अभ्यास प्रश्न (MCQs)",
                        duration = "10 प्रश्न",
                        notesContent = "विशिष्ट पुस्तकालय, SDI सेवा (H.P. Luhn), CAS, DESIDOC, SENDOC एवं अनुवाद सेवाओं पर 10 प्रश्न।",
                        questions = if (qSet5.isNotEmpty()) qSet5 else Unit6Questions.getSet5Questions()
                    )
                )
                list.add(
                    SubtopicItem(
                        id = "u6_s6",
                        unitCategory = category,
                        subtopicNumber = 6,
                        title = "सेट 6: पुस्तकालयों के प्रकार एवं संगठन",
                        tag = "प्रैक्टिस सेट 06",
                        subtitle = "10 बहुविकल्पीय अभ्यास प्रश्न (MCQs)",
                        duration = "10 प्रश्न",
                        notesContent = "शैक्षणिक, विश्वविद्यालय, डिजिटल पुस्तकालय (NDLI), लाइब्रेरी ऑफ कांग्रेस एवं ब्रिटिश लाइब्रेरी पर 10 प्रश्न।",
                        questions = if (qSet6.isNotEmpty()) qSet6 else Unit6Questions.getSet6Questions()
                    )
                )
                list.add(
                    SubtopicItem(
                        id = "u6_s7",
                        unitCategory = category,
                        subtopicNumber = 7,
                        title = "सेट 7: डॉ. एस. आर. रंगनाथन एवं योगदान",
                        tag = "प्रैक्टिस सेट 07",
                        subtitle = "10 बहुविकल्पीय अभ्यास प्रश्न (MCQs)",
                        duration = "10 प्रश्न",
                        notesContent = "डॉ. रंगनाथन की जीवनी, 12 अगस्त राष्ट्रीय लाइब्रेरियन दिवस, CC 1933, CCC 1934, PMEST, DRTC एवं पद्मश्री सम्मान पर 10 प्रश्न।",
                        questions = if (qSet7.isNotEmpty()) qSet7 else Unit6Questions.getSet7Questions()
                    )
                )
                list.add(
                    SubtopicItem(
                        id = "u6_s8",
                        unitCategory = category,
                        subtopicNumber = 8,
                        title = "सेट 8: राष्ट्रीय पुस्तकालय एवं डिलीवरी एक्ट",
                        tag = "प्रैक्टिस सेट 08",
                        subtitle = "10 बहुविकल्पीय अभ्यास प्रश्न (MCQs)",
                        duration = "10 प्रश्न",
                        notesContent = "कलकत्ता पब्लिक लाइब्रेरी 1836, इंपीरियल लाइब्रेरी, राष्ट्रीय पुस्तकालय 1948, डिलीवरी ऑफ बुक्स एक्ट 1954 व INB पर 10 प्रश्न।",
                        questions = if (qSet8.isNotEmpty()) qSet8 else Unit6Questions.getSet8Questions()
                    )
                )
            }
        }

        // Add any user-created study materials from database for this category,
        // strictly ignoring the seeded materials that correspond to the base subtopics
        val knownSubtopics = setOf(
            DefaultQuestions.UNIT_1_SUBTOPIC_1,
            DefaultQuestions.UNIT_1_SUBTOPIC_2,
            DefaultQuestions.UNIT_1_SUBTOPIC_3,
            DefaultQuestions.UNIT_1_SUBTOPIC_4,
            DefaultQuestions.UNIT_1_SUBTOPIC_5
        )

        val userCreatedForUnit = userStudyMaterials.filter { mat ->
            mat.unitCategory == category && knownSubtopics.none { known ->
                mat.subTopic.isNotBlank() && (mat.subTopic.contains(known) || known.contains(mat.subTopic))
            }
        }

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
