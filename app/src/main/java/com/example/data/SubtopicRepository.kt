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
    val duration: String = "45m",
    val dateString: String = "2026 Batch",
    val notesContent: String = "",
    val questions: List<QuestionEntity> = emptyList(),
    val isCustomUserAdded: Boolean = false
)

data class SubtopicSeedInfo(
    val num: Int,
    val title: String,
    val subtitle: String,
    val keyTag: String,
    val notes: String,
    val questions: List<QuestionEntity>
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
                        tag = "उपविषय 01",
                        subtitle = "Basic Concepts of Library Science",
                        duration = "${if (q1.isNotEmpty()) q1.size else 11} प्रश्न (DPP)",
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
                        tag = "उपविषय 02",
                        subtitle = "Types of Library (Academic, Public, Special, National)",
                        duration = "${if (q2.isNotEmpty()) q2.size else 11} प्रश्न (DPP)",
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
                        tag = "उपविषय 03",
                        subtitle = "Public Library System & Legislation",
                        duration = "${if (q3.isNotEmpty()) q3.size else 11} प्रश्न (DPP)",
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
                        tag = "उपविषय 04",
                        subtitle = "National Library of India (Kolkata) & INB",
                        duration = "${if (q4.isNotEmpty()) q4.size else 11} प्रश्न (DPP)",
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
                        tag = "उपविषय 05",
                        subtitle = "Special Library (ISRO, DRDO, CSIR, ICAR)",
                        duration = "${if (q5.isNotEmpty()) q5.size else 11} प्रश्न (DPP)",
                        notesContent = DefaultQuestions.getUnit1Subtopic5Notes(),
                        questions = if (q5.isNotEmpty()) q5 else DefaultQuestions.getUnit1Subtopic5Questions()
                    )
                )
            }

            category.contains("इकाई 2") || category.contains("वर्गीकरण") || category.contains("सूचीकरण") -> {
                val u2Data = listOf(
                    SubtopicSeedInfo(1, "पुस्तकालय वर्गीकरण के सिद्धांत एवं पद्धतियां", "DDC, CC, UDC एवं वर्गीकरण पद्धतियां", "पुस्तकालय वर्गीकरण के सिद्धांत", UnitsContentData.getUnit2Subtopic1Notes(), emptyList()),
                    SubtopicSeedInfo(2, "द्विविंदु वर्गीकरण (CC) एवं मेलविल डेवी (DDC) विस्तृत संरचना", "CC PMEST पक्ष विश्लेषण एवं DDC मुख्य वर्ग", "द्विविंदु वर्गीकरण एवं DDC", UnitsContentData.getUnit2Subtopic2Notes(), emptyList()),
                    SubtopicSeedInfo(3, "पुस्तकालय सूचीकरण: CCC एवं AACR-2", "डॉ. रंगनाथन का CCC एवं AACR-2", "पुस्तकालय सूचीकरण", UnitsContentData.getUnit2Subtopic3Notes(), emptyList()),
                    SubtopicSeedInfo(4, "सूची प्रविष्टियों के प्रकार एवं OPAC", "मुख्य प्रविष्टि, सहायक प्रविष्टियां एवं OPAC खोज", "सूची प्रविष्टियां एवं OPAC", UnitsContentData.getUnit2Subtopic4Notes(), emptyList()),
                    SubtopicSeedInfo(5, "मेटाडेटा मानक (MARC 21, Dublin Core) एवं विषय अनुक्रमण", "MARC 21 टैग्स, डबलिन कोर 15 तत्व व विषय सूचियां", "मेटाडेटा मानक", UnitsContentData.getUnit2Subtopic5Notes(), emptyList())
                )
                u2Data.forEach { (num, title, sub, keyTag, notes, _) ->
                    list.add(
                        SubtopicItem(
                            id = "u2_s$num",
                            unitCategory = category,
                            subtopicNumber = num,
                            title = title,
                            tag = "उपविषय 0$num",
                            subtitle = sub,
                            duration = "अध्ययन नोट्स",
                            notesContent = notes,
                            questions = emptyList()
                        )
                    )
                }
            }

            category.contains("इकाई 3") || category.contains("प्रबंधन") -> {
                val u3Data = listOf(
                    SubtopicSeedInfo(1, "पुस्तकालय प्रबंधन: POSDCORB, टेलर व फेयोल के सिद्धांत", "प्रबंधन के कार्य, 14 सिद्धांत, वैज्ञानिक प्रबंधन", "पुस्तकालय प्रबंधन के सिद्धांत", UnitsContentData.getUnit3Subtopic1Notes(), emptyList()),
                    SubtopicSeedInfo(2, "अर्जन अनुभाग: पुस्तक चयन सिद्धांत (डेवी, ड्रूरी, रंगनाथन)", "पुस्तक चयन सिद्धांत एवं परिग्रहण पंजिका", "पुस्तक चयन एवं अर्जन", UnitsContentData.getUnit3Subtopic2Notes(), emptyList()),
                    SubtopicSeedInfo(3, "तकनीकी एवं परिसंचरण अनुभाग: ब्राउन व नेवार्क प्रणाली", "ब्राउन व नेवार्क चार्जिंग सिस्टम एवं कॉल नंबर", "परिसंचरण प्रणालियां", UnitsContentData.getUnit3Subtopic3Notes(), emptyList()),
                    SubtopicSeedInfo(4, "पुस्तकालय बजट निर्माण: ZBB, PPBS एवं वित्तीय प्रबंधन", "शून्य आधारित बजट (ZBB), PPBS एवं अनुदान नियम", "पुस्तकालय बजट निर्माण", UnitsContentData.getUnit3Subtopic4Notes(), emptyList()),
                    SubtopicSeedInfo(5, "भंडार सत्यापन (Stock Verification), वीडिंग आउट एवं संरक्षण", "शेल्फ लिस्ट, GFR नियम, वीडिंग आउट व संरक्षण", "भंडार सत्यापन एवं संरक्षण", UnitsContentData.getUnit3Subtopic5Notes(), emptyList())
                )
                u3Data.forEach { (num, title, sub, keyTag, notes, _) ->
                    list.add(
                        SubtopicItem(
                            id = "u3_s$num",
                            unitCategory = category,
                            subtopicNumber = num,
                            title = title,
                            tag = "उपविषय 0$num",
                            subtitle = sub,
                            duration = "अध्ययन नोट्स",
                            notesContent = notes,
                            questions = emptyList()
                        )
                    )
                }
            }

            category.contains("इकाई 4") || category.contains("सूचना") -> {
                val u4Data = listOf(
                    SubtopicSeedInfo(1, "सूचना स्रोत: प्राथमिक, द्वितीयक एवं तृतीयक स्रोत", "हैनसन व ग्रोगन वर्गीकरण, जर्नल्स, पेटेंट व संदर्भ ग्रंथ", "सूचना स्रोतों का वर्गीकरण", UnitsContentData.getUnit4Subtopic1Notes(), emptyList()),
                    SubtopicSeedInfo(2, "संदर्भ सेवा: तैयार संदर्भ एवं दीर्घकालीन संदर्भ सेवा", "रंगनाथन व जेम्स आई. वायर संदर्भ सेवा सिद्धांत", "संदर्भ सेवा", UnitsContentData.getUnit4Subtopic2Notes(), emptyList()),
                    SubtopicSeedInfo(3, "सामयिक चेतना सेवा (CAS) एवं चयनित सूचना प्रसार (SDI)", "एच. पी. लुहन (1958) SDI घटक व फीडबैक लूप", "CAS एवं SDI सेवाएं", UnitsContentData.getUnit4Subtopic3Notes(), emptyList()),
                    SubtopicSeedInfo(4, "अनुक्रमण एवं सारकरण सेवाएं (KWIC, PRECIS, POPSI)", "KWIC, PRECIS, POPSI, SCI एवं श्रृंखला प्रक्रिया", "अनुक्रमण पद्धतियां", UnitsContentData.getUnit4Subtopic4Notes(), emptyList()),
                    SubtopicSeedInfo(5, "राष्ट्रीय एवं अंतरराष्ट्रीय सूचना प्रणालियां व नेटवर्क", "INFLIBNET गांधीनगर, शोधगंगा, DELNET, INIS, AGRIS", "सूचना प्रणालियां व नेटवर्क", UnitsContentData.getUnit4Subtopic5Notes(), emptyList())
                )
                u4Data.forEach { (num, title, sub, keyTag, notes, _) ->
                    list.add(
                        SubtopicItem(
                            id = "u4_s$num",
                            unitCategory = category,
                            subtopicNumber = num,
                            title = title,
                            tag = "उपविषय 0$num",
                            subtitle = sub,
                            duration = "अध्ययन नोट्स",
                            notesContent = notes,
                            questions = emptyList()
                        )
                    )
                }
            }

            category.contains("इकाई 5") || category.contains("कंप्यूटर") -> {
                val u5Data = listOf(
                    SubtopicSeedInfo(1, "कंप्यूटर की मूलभूत अवधारणा: हार्डवेयर, सॉफ्टवेयर व पीढ़ियां", "वैक्यूम ट्यूब से AI तक, RAM/ROM व मेमोरी माप", "कंप्यूटर की मूलभूत अवधारणा", UnitsContentData.getUnit5Subtopic1Notes(), emptyList()),
                    SubtopicSeedInfo(2, "पुस्तकालय स्वचालन: आवश्यकता, योजना एवं प्रमुख घटक", "ILS मॉड्यूल्स, कारडेक्स डिजिटाइजेशन व Z39.50", "पुस्तकालय स्वचालन", UnitsContentData.getUnit5Subtopic2Notes(), emptyList()),
                    SubtopicSeedInfo(3, "ओपन सोर्स लाइब्रेरी सॉफ्टवेयर: कोहा (Koha), सोउल (SOUL), DSpace", "Koha (2000), SOUL 3.0 (INFLIBNET) व DSpace रिपॉजिटरी", "ओपन सोर्स लाइब्रेरी सॉफ्टवेयर", UnitsContentData.getUnit5Subtopic3Notes(), emptyList()),
                    SubtopicSeedInfo(4, "बारकोड एवं आरएफआईडी (RFID) तकनीक पुस्तकालय में", "RFID टैग, EAS सुरक्षा गेट, कियोस्क व हैंडहेल्ड स्कैनर", "बारकोड एवं RFID तकनीक", UnitsContentData.getUnit5Subtopic4Notes(), emptyList()),
                    SubtopicSeedInfo(5, "डिजिटल लाइब्रेरी, इंटरनेट एवं ई-संसाधन (NDLI, शोधगंगा)", "NDLI (IIT खड़गपुर), शोधगंगा, ई-शोधसिंधु, DOAJ, DOI", "डिजिटल लाइब्रेरी एवं ई-संसाधन", UnitsContentData.getUnit5Subtopic5Notes(), emptyList())
                )
                u5Data.forEach { (num, title, sub, keyTag, notes, _) ->
                    list.add(
                        SubtopicItem(
                            id = "u5_s$num",
                            unitCategory = category,
                            subtopicNumber = num,
                            title = title,
                            tag = "उपविषय 0$num",
                            subtitle = sub,
                            duration = "अध्ययन नोट्स",
                            notesContent = notes,
                            questions = emptyList()
                        )
                    )
                }
            }

            category.contains("इकाई 6") || category.contains("एक्स्ट्रा") || category.contains("Extra") -> {
                // Unit 6 Extra Questions - 18 Practice Sets (175 MCQs total) + any custom user questions
                val allSets = Unit6Questions.getAllSetsInfo()
                val usedQuestionIds = mutableSetOf<Long>()

                allSets.forEach { setInfo ->
                    val setRegex = Regex("""(?:सेट|Set)\s*0?${setInfo.setNumber}(?:\D|$)""", RegexOption.IGNORE_CASE)
                    val qSet = allQuestions.filter { q ->
                        (q.category == category || DefaultQuestions.isExtraQuestionsUnit(q.category)) &&
                                setRegex.containsMatchIn(q.keyHighlight)
                    }
                    val finalQuestions = if (qSet.isNotEmpty()) qSet else setInfo.questions
                    finalQuestions.forEach { if (it.id > 0) usedQuestionIds.add(it.id) }

                    list.add(
                        SubtopicItem(
                            id = "u6_s${setInfo.setNumber}",
                            unitCategory = category,
                            subtopicNumber = setInfo.setNumber,
                            title = setInfo.title,
                            tag = setInfo.tag,
                            subtitle = setInfo.subtitle,
                            duration = "${finalQuestions.size} प्रश्न",
                            notesContent = setInfo.notesSummary,
                            questions = finalQuestions
                        )
                    )
                }

                // Any extra questions in Unit 6 that aren't part of standard sets 1..18
                val remainingUnit6Questions = allQuestions.filter { q ->
                    (q.category == category || DefaultQuestions.isExtraQuestionsUnit(q.category)) &&
                            (q.id == 0L || q.id !in usedQuestionIds) &&
                            allSets.none { setInfo ->
                                Regex("""(?:सेट|Set)\s*0?${setInfo.setNumber}(?:\D|$)""", RegexOption.IGNORE_CASE).containsMatchIn(q.keyHighlight)
                            }
                }
                if (remainingUnit6Questions.isNotEmpty()) {
                    val customGroups = remainingUnit6Questions.groupBy { it.keyHighlight.ifBlank { "अतिरिक्त कस्टम अभ्यास प्रश्न" } }
                    customGroups.forEach { (customTitle, groupQuestions) ->
                        list.add(
                            SubtopicItem(
                                id = "u6_extra_${customTitle.hashCode()}",
                                unitCategory = category,
                                subtopicNumber = list.size + 1,
                                title = customTitle,
                                tag = "प्रैक्टिस सेट ${list.size + 1}",
                                subtitle = "${groupQuestions.size} जोड़े गए अभ्यास प्रश्न",
                                duration = "${groupQuestions.size} प्रश्न",
                                notesContent = "उपयोगकर्ता द्वारा जोड़े गए अतिरिक्त अभ्यास प्रश्न।",
                                questions = groupQuestions,
                                isCustomUserAdded = true
                            )
                        )
                    }
                }
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
                    title = mat.subTopic.ifBlank { "उपविषय ${list.size + 1}: उपयोगकर्ता सामग्री" },
                    tag = "उपविषय ${list.size + 1}",
                    subtitle = "कस्टम अध्ययन सामग्री",
                    duration = "${customQuestions.size} प्रश्न (DPP)",
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
