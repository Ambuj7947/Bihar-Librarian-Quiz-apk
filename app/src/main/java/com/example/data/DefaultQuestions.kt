package com.example.data

import com.example.data.model.QuestionEntity

object DefaultQuestions {

    const val UNIT_1 = "इकाई 1: पुस्तकालय विज्ञान का आधार"
    const val UNIT_2 = "इकाई 2: पुस्तकालय वर्गीकरण एवं सूचीकरण"
    const val UNIT_3 = "इकाई 3: पुस्तकालय प्रबंधन एवं विभाग"
    const val UNIT_4 = "इकाई 4: सूचना स्रोत एवं सूचना सेवाएं"
    const val UNIT_5 = "इकाई 5: सामान्य कंप्यूटर / बेसिक कंप्यूटर"

    val allCategories = listOf(
        UNIT_1,
        UNIT_2,
        UNIT_3,
        UNIT_4,
        UNIT_5
    )

    val unitEnglishSubtitles = mapOf(
        UNIT_1 to "Foundation of Library Science",
        UNIT_2 to "Library Classification and Cataloging",
        UNIT_3 to "Library Management and Library Sections",
        UNIT_4 to "Information Sources and Services",
        UNIT_5 to "Basic Computer / ICT"
    )

    fun getInitialQuestions(): List<QuestionEntity> = emptyList()
}
