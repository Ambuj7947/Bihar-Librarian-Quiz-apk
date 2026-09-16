package com.example.data

import com.example.data.model.QuestionEntity
import com.example.data.model.StudyMaterialEntity

object DefaultQuestions {

    const val UNIT_1 = "इकाई 1: पुस्तकालय विज्ञान का आधार"
    const val UNIT_2 = "इकाई 2: पुस्तकालय वर्गीकरण एवं सूचीकरण"
    const val UNIT_3 = "इकाई 3: पुस्तकालय प्रबंधन एवं विभाग"
    const val UNIT_4 = "इकाई 4: सूचना स्रोत एवं सूचना सेवाएं"
    const val UNIT_5 = "इकाई 5: सामान्य कंप्यूटर / बेसिक कंप्यूटर"
    const val UNIT_6 = "इकाई 6: एक्स्ट्रा क्वेश्चंस (Extra Questions)"

    // Unit 1 Subtopic 1 details
    const val UNIT_1_SUBTOPIC_1 = "पुस्तकालय की बेसिक अवधारणा"
    const val UNIT_1_SUBTOPIC_1_YOUTUBE_URL = "https://www.youtube.com/live/XDdMEc3Kvh4?si=tUI3sqQ7en3pCUjn"
    const val UNIT_1_SUBTOPIC_1_YOUTUBE_TITLE = "Bihar Librarian LET 2026 | UNIT-1 Basic Concepts of Library | पुस्तकालय की मूलभूत अवधारणा"

    // Unit 1 Subtopic 2 details
    const val UNIT_1_SUBTOPIC_2 = "पुस्तकालय के प्रकार (Types of Library)"
    const val UNIT_1_SUBTOPIC_2_YOUTUBE_URL = "https://www.youtube.com/live/wYSPC7-MJIo?si=5T6N6yc7NPTXYnnr"
    const val UNIT_1_SUBTOPIC_2_YOUTUBE_TITLE = "Bihar Librarian LET 2026 | UNIT-1 Types of Library | पुस्तकालय के प्रकार | Complete Class"

    // Unit 1 Subtopic 3 details
    const val UNIT_1_SUBTOPIC_3 = "सार्वजनिक लाइब्रेरी (Public Library)"
    const val UNIT_1_SUBTOPIC_3_YOUTUBE_URL = "https://www.youtube.com/live/uCEcYZpYJzU?si=h833VB2zim-UX-3S"
    const val UNIT_1_SUBTOPIC_3_YOUTUBE_TITLE = "Bihar Librarian LET 2026 | UNIT-1 Public Library | सार्वजनिक पुस्तकालय 📚"

    // Unit 1 Subtopic 4 details
    const val UNIT_1_SUBTOPIC_4 = "भारत के राष्ट्रीय लाइब्रेरी (National Library of India)"
    const val UNIT_1_SUBTOPIC_4_YOUTUBE_URL = "https://www.youtube.com/live/YdvxrwbvaAs?si=xSxRqQty50dXkmCk"
    const val UNIT_1_SUBTOPIC_4_YOUTUBE_TITLE = "Bihar Librarian LET 2026 | UNIT-1 National Library of India | भारत का राष्ट्रीय पुस्तकालय 📚"

    // Unit 1 Subtopic 5 details
    const val UNIT_1_SUBTOPIC_5 = "विशिष्ट लाइब्रेरी (Special Library)"
    const val UNIT_1_SUBTOPIC_5_YOUTUBE_URL = "https://www.youtube.com/live/wD8Bue1vdGQ?si=WU4c12D48xx0vc-8"
    const val UNIT_1_SUBTOPIC_5_YOUTUBE_TITLE = "Bihar Librarian LET 2026 | UNIT-1 विशिष्ट पुस्तकालय | Special Library 📚 बिल्कुल बेसिक से"

    val allCategories = listOf(
        UNIT_1,
        UNIT_2,
        UNIT_3,
        UNIT_4,
        UNIT_5,
        UNIT_6
    )

    val unitEnglishSubtitles = mapOf(
        UNIT_1 to "Foundation of Library Science",
        UNIT_2 to "Library Classification and Cataloging",
        UNIT_3 to "Library Management and Library Sections",
        UNIT_4 to "Information Sources and Services",
        UNIT_5 to "Basic Computer / ICT",
        UNIT_6 to "Extra Practice Sets (Q&A Only)"
    )

    fun isExtraQuestionsUnit(category: String?): Boolean {
        if (category == null) return false
        return category == UNIT_6 || category.contains("इकाई 6") || category.contains("एक्स्ट्रा") || category.contains("Extra Questions")
    }

    fun getUnit1Subtopic1Notes(): String {
        return """
* पुस्तकालय शब्द की व्युत्पत्ति (Etymology):
   * हिंदी अर्थ: 'पुस्तकालय' दो शब्दों से मिलकर बना है—पुस्तक + आलय, जिसका शाब्दिक अर्थ है "पुस्तकों का घर" या "पुस्तकों का आलय"।
   * अंग्रेजी शब्द: 'Library' शब्द की उत्पत्ति लैटिन भाषा के मूल शब्द 'Liber' (लिबर) से हुई है, जिसका अर्थ पेड़ की छाल (Inner bark of a tree) या पुस्तक होता है।
   * लैटिन के 'Liber' से फ्रेंच शब्द 'Librairie' बना, जिससे कालांतर में अंग्रेजी का 'Library' शब्द विकसित हुआ।

* पुस्तकालय की आधुनिक परिभाषा और स्वरूप:
   * पारंपरिक दृष्टिकोण में पुस्तकालय केवल पुस्तकों के संग्रह और संरक्षण का स्थल था, लेकिन आधुनिक दृष्टिकोण में यह ज्ञान, सूचना और जनसेवा का एक सक्रिय सामाजिक केंद्र है।
   * पुस्तकालय समाज का एक अनिवार्य शैक्षणिक और सांस्कृतिक अंग है, जो बिना किसी भेदभाव के सभी को सूचना और स्वाध्याय के समान अवसर प्रदान करता है।

* पुस्तकालय के प्रमुख घटक (Core Components / Trinity of Library):
   * डॉ. एस. आर. रंगनाथन के अनुसार पुस्तकालय को एक त्रिमूर्ति (Trinity) माना गया है, जिसमें तीन घटक शामिल होते हैं:
      1. पाठक (Reader / User): पुस्तकालय का सबसे महत्वपूर्ण घटक, जिसके उपयोग हेतु सामग्री व्यवस्थित की जाती है।
      2. पुस्तकें/अध्ययन सामग्री (Books / Information Resources): ज्ञान के स्रोत।
      3. कर्मचारी (Staff / Librarian): जो पाठक और पुस्तक के बीच सेतु (मध्यस्थ) का कार्य करते हैं।

* पुस्तकालय विज्ञान की औपचारिक शुरुआत:
   * विश्व स्तर पर पुस्तकालय विज्ञान के जनक मेलविल डेवी (Melvil Dewey) माने जाते हैं, जिन्होंने 1887 में कोलंबिया कॉलेज में पहला स्कूल ऑफ लाइब्रेरी इकोनॉमी शुरू किया था।
   * भारत में पुस्तकालय आंदोलन और पुस्तकालय विज्ञान के जनक डॉ. एस. आर. रंगनाथन (Dr. S. R. Ranganathan) हैं।
   * रंगनाथन जी ने पुस्तकालय विज्ञान के पाँच मूल सूत्र (Five Laws of Library Science) प्रतिपादित किए, जो आधुनिक पुस्तकालय प्रणाली की रीढ़ हैं।

* पुस्तकालय के प्रकार (Types of Libraries):
   * सार्वजनिक पुस्तकालय (Public Library): आम जनता के लिए निशुल्क या नाममात्र शुल्क पर खुली संस्था।
   * शैक्षणिक पुस्तकालय (Academic Library): स्कूल, कॉलेज एवं विश्वविद्यालय पुस्तकालय।
   * विशिष्ट पुस्तकालय (Special Library): किसी विशिष्ट अनुसंधान संस्थान, उद्योग या विभाग से संबंधित।
   * राष्ट्रीय पुस्तकालय (National Library): देश की बौद्धिक संपदा का कानूनी निक्षेपागार (Depository Library)।
        """.trimIndent()
    }

    fun getUnit1Subtopic1Questions(): List<QuestionEntity> {
        return listOf(
            QuestionEntity(
                id = 1L,
                category = UNIT_1,
                questionHindi = "अंग्रेजी भाषा का 'Library' शब्द किस भाषा के मूल शब्द 'Liber' से व्युत्पन्न हुआ है?",
                optionA = "ग्रीक (Greek)",
                optionB = "लैटिन (Latin)",
                optionC = "फ्रेंच (French)",
                optionD = "जर्मन (German)",
                correctOption = 2,
                explanationHindi = "'Library' शब्द लैटिन भाषा के मूल शब्द 'Liber' से निकला है।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_1"
            ),
            QuestionEntity(
                id = 2L,
                category = UNIT_1,
                questionHindi = "'Liber' शब्द का मूल शाब्दिक अर्थ क्या होता है?",
                optionA = "कागज का पन्ना",
                optionB = "पेड़ की छाल (Inner Bark)",
                optionC = "लोहे की पट्टिका",
                optionD = "स्याही",
                correctOption = 2,
                explanationHindi = "प्राचीन काल में 'Liber' का अर्थ पेड़ की भीतरी छाल होता था, जिस पर लिखा जाता था।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_1"
            ),
            QuestionEntity(
                id = 3L,
                category = UNIT_1,
                questionHindi = "'Library' शब्द लैटिन के 'Liber' से किस फ्रेंच शब्द के माध्यम से अंग्रेजी में आया?",
                optionA = "Liberte",
                optionB = "Librairie",
                optionC = "Libris",
                optionD = "Libre",
                correctOption = 2,
                explanationHindi = "लैटिन के 'Liber' से फ्रेंच शब्द 'Librairie' बना, जिससे अंग्रेजी शब्द 'Library' आया।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_1"
            ),
            QuestionEntity(
                id = 4L,
                category = UNIT_1,
                questionHindi = "हिंदी भाषा में 'पुस्तकालय' शब्द किन दो शब्दों के संधि-योग से बना है?",
                optionA = "पुस्तक + लय",
                optionB = "पुस्तक + आलय",
                optionC = "पुस्तिका + लय",
                optionD = "पुस्त + कालय",
                correctOption = 2,
                explanationHindi = "पुस्तक + आलय = पुस्तकालय (अर्थात पुस्तकों को सहेजने और पढ़ने का स्थान)।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_1"
            ),
            QuestionEntity(
                id = 5L,
                category = UNIT_1,
                questionHindi = "डॉ. एस. आर. रंगनाथन के अनुसार पुस्तकालय की 'त्रिमूर्ति' (Trinity of Library) में कौन-से तीन घटक शामिल हैं?",
                optionA = "पुस्तक, भवन और फर्नीचर",
                optionB = "पाठक, पुस्तक और कर्मचारी (Librarian)",
                optionC = "कंप्यूटर, इंटरनेट और सॉफ्टवेयर",
                optionD = "बजट, प्रबंधन और पाठक",
                correctOption = 2,
                explanationHindi = "पुस्तकालय के तीन अनिवार्य अंग पाठक, पुस्तक और कर्मचारी माने गए हैं।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_1"
            ),
            QuestionEntity(
                id = 6L,
                category = UNIT_1,
                questionHindi = "भारत में \"पुस्तकालय विज्ञान के जनक\" (Father of Library Science in India) के रूप में किन्हें जाना जाता है?",
                optionA = "डब्ल्यू. ए. बोर्डन",
                optionB = "सी. ए. कटर",
                optionC = "डॉ. एस. आर. रंगनाथन",
                optionD = "बी. एस. केशवन",
                correctOption = 3,
                explanationHindi = "भारत में पुस्तकालय आंदोलन और शिक्षा के प्रणेता डॉ. एस. आर. रंगनाथन हैं।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_1"
            ),
            QuestionEntity(
                id = 7L,
                category = UNIT_1,
                questionHindi = "विश्व स्तर पर प्रथम 'स्कूल ऑफ लाइब्रेरी इकोनॉमी' (1887) की स्थापना किसके द्वारा की गई थी?",
                optionA = "मेलविल डेवी",
                optionB = "चार्ल्स एमी कटर",
                optionC = "हेनरी ला फोंटेन",
                optionD = "पॉल ओटलेट",
                correctOption = 1,
                explanationHindi = "मेलविल डेवी ने 1887 में कोलंबिया कॉलेज में पुस्तकालय शिक्षा का पहला संस्थान स्थापित किया था।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_1"
            ),
            QuestionEntity(
                id = 8L,
                category = UNIT_1,
                questionHindi = "पुस्तकालय के आधुनिक दृष्टिकोण के संबंध में कौन-सा कथन सर्वाधिक उपयुक्त है?",
                optionA = "यह केवल दुर्लभ पुस्तकों का संग्रहालय है।",
                optionB = "यह केवल धनवान वर्ग के लिए अध्ययन कक्ष है।",
                optionC = "यह सूचना प्रसार और निरंतर स्वाध्याय का एक सक्रिय सामाजिक केंद्र है।",
                optionD = "यह केवल परीक्षा की तैयारी के लिए बैठने का स्थान है।",
                correctOption = 3,
                explanationHindi = "आधुनिक पुस्तकालय केवल पुस्तकों का संग्रह न होकर सूचना व ज्ञान के प्रसार का सामाजिक केंद्र है।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_1"
            ),
            QuestionEntity(
                id = 9L,
                category = UNIT_1,
                questionHindi = "डॉ. एस. आर. रंगनाथन द्वारा प्रतिपादित 'पुस्तकालय विज्ञान के पाँच सूत्र' (Five Laws of Library Science) का मुख्य उद्देश्य क्या है?",
                optionA = "पुस्तकों की बिक्री बढ़ाना",
                optionB = "पुस्तकालय प्रबंधन और सेवाओं को अधिकतम पाठक-उन्मुख बनाना",
                optionC = "केवल कर्मचारियों की संख्या सीमित करना",
                optionD = "पुस्तकालय को बंद रखना",
                correctOption = 2,
                explanationHindi = "पाँचों सूत्र पुस्तकालय सेवाओं, पाठकों के समय की बचत और अधिकतम उपयोग को सुनिश्चित करते हैं।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_1"
            ),
            QuestionEntity(
                id = 10L,
                category = UNIT_1,
                questionHindi = "विश्वविद्यालय और कॉलेज पुस्तकालय किस श्रेणी के अंतर्गत आते हैं?",
                optionA = "सार्वजनिक पुस्तकालय (Public Library)",
                optionB = "शैक्षणिक पुस्तकालय (Academic Library)",
                optionC = "विशिष्ट पुस्तकालय (Special Library)",
                optionD = "राष्ट्रीय पुस्तकालय (National Library)",
                correctOption = 2,
                explanationHindi = "स्कूल, कॉलेज और विश्वविद्यालय पुस्तकालय Academic Libraries (शैक्षणिक पुस्तकालय) कहलाते हैं।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_1"
            )
        )
    }

    fun getUnit1Subtopic1Material(): StudyMaterialEntity {
        return StudyMaterialEntity(
            id = 1L,
            unitCategory = UNIT_1,
            subTopic = UNIT_1_SUBTOPIC_1,
            youtubeUrl = UNIT_1_SUBTOPIC_1_YOUTUBE_URL,
            youtubeVideoId = "XDdMEc3Kvh4",
            youtubeTitle = UNIT_1_SUBTOPIC_1_YOUTUBE_TITLE,
            timestampNotes = "00:00 - पुस्तकालय शब्द की व्युत्पत्ति एवं अर्थ, 12:40 - आधुनिक परिभाषा एवं त्रिमूर्ति, 26:15 - पुस्तकालय विज्ञान के जनक व सूत्र, 42:00 - वस्तुनिष्ठ प्रश्नोत्तरी",
            notesContent = getUnit1Subtopic1Notes(),
            questionsCount = 10,
            dateAddedMillis = System.currentTimeMillis()
        )
    }

    fun getUnit1Subtopic2Notes(): String {
        return """
* पुस्तकालय का वर्गीकरण आधार (Basis of Classification):
   * पुस्तकालय को मुख्य रूप से उनकी सेवा प्रकृति (Service Nature), लक्षित पाठक वर्ग (Target Users), और उद्देश्य के आधार पर चार प्रमुख श्रेणियों में विभाजित किया जाता है:
      1. शैक्षणिक पुस्तकालय (Academic Library)
      2. सार्वजनिक पुस्तकालय (Public Library)
      3. विशिष्ट पुस्तकालय (Special Library)
      4. राष्ट्रीय पुस्तकालय (National Library)

* 1. शैक्षणिक पुस्तकालय (Academic Library):
   * औपचारिक शिक्षा प्रणाली का अभिन्न अंग, जिसका मुख्य उद्देश्य छात्रों और शिक्षकों के पठन-पाठन व शोध आवश्यकताओं की पूर्ति करना है।
   * इसके तीन उप-प्रकार होते हैं:
      * स्कूल पुस्तकालय (School Library): प्राथमिक, माध्यमिक एवं उच्चतर माध्यमिक स्तर पर बच्चों में पढ़ने की आदत विकसित करना।
      * कॉलेज पुस्तकालय (College Library): स्नातक एवं स्नातकोत्तर स्तर पर पाठ्यचर्या व संदर्भ अध्ययन की सहायता करना।
      * विश्वविद्यालय पुस्तकालय (University Library): उच्च शिक्षा, उन्नत अनुसंधान (Research) और ज्ञान के सृजन का केंद्र। विश्वविद्यालय पुस्तकालय को विश्वविद्यालय का 'हृदय' (Heart of the University) कहा जाता है (राधाकृष्णन आयोग / UGC दृष्टिकोण)।

* 2. सार्वजनिक पुस्तकालय (Public Library):
   * समाज के सभी वर्गों (बिना किसी जाति, धर्म, लिंग, आयु या शैक्षणिक योग्यता के भेदभाव) के लिए खुला पुस्तकालय।
   * इसे "जनता का विश्वविद्यालय" (People's University) भी कहा जाता है।
   * यूनेस्को सार्वजनिक पुस्तकालय घोषणापत्र (UNESCO Public Library Manifesto): पहली बार 1949 में जारी किया गया (बाद में 1972 और 1994 में IFLA के सहयोग से संशोधित हुआ)। यह घोषणापत्र सार्वजनिक पुस्तकालय को आजीवन सीखने, निर्णय लेने और सांस्कृतिक विकास का जीवित बल मानता है।
   * वित्तीय सहायता: सार्वजनिक पुस्तकालय मुख्यतः सरकार द्वारा लगाए जाने वाले पुस्तकालय उपकर (Library Cess) अथवा सार्वजनिक अनुदान पर निर्भर करते हैं।

* 3. विशिष्ट पुस्तकालय (Special Library):
   * किसी विशिष्ट विषय (जैसे—चिकित्सा, विधि, कृषि, अभियांत्रिकी) अथवा किसी विशिष्ट संगठन/उद्योग/शोध संस्थान (जैसे—ISRO, DRDO, CSIR, ICAR) की जरूरतों को पूरा करने के लिए स्थापित।
   * इसके पाठक सामान्य जनता न होकर विशेषज्ञ, वैज्ञानिक अथवा शोधार्थी होते हैं।
   * यहाँ पारंपरिक पुस्तकों से अधिक शोध पत्रिकाओं (Research Journals), तकनीकी रिपोर्ट, पेटेंट और मानकों (Standards) को प्राथमिकता दी जाती है।

* 4. राष्ट्रीय पुस्तकालय (National Library):
   * किसी भी देश का सर्वोच्च पुस्तकालय, जो पूरे राष्ट्र की बौद्धिक और प्रकाशित धरोहर को संग्रहित व संरक्षित करने के लिए उत्तरदायी होता है।
   * भारत का राष्ट्रीय पुस्तकालय कोलकाता (National Library of India, Kolkata) में स्थित है।
   * डिलीवरी ऑफ बुक्स (पब्लिक लाइब्रेरीज़) एक्ट, 1954 (संशोधित 1956—समाचार पत्रों हेतु) के तहत भारत में प्रकाशित प्रत्येक पुस्तक/अखबार की एक प्रति राष्ट्रीय पुस्तकालय और तीन अन्य निक्षेपागार पुस्तकालयों (Depository Libraries) में जमा करना अनिवार्य है।
        """.trimIndent()
    }

    fun getUnit1Subtopic2Questions(): List<QuestionEntity> {
        return listOf(
            QuestionEntity(
                id = 11L,
                category = UNIT_1,
                questionHindi = "पुस्तकालयों को उनके कार्य और पाठक वर्ग के आधार पर सामान्यतः कितने मुख्य प्रकारों में वर्गीकृत किया जाता है?",
                optionA = "दो",
                optionB = "तीन",
                optionC = "चार",
                optionD = "छह",
                correctOption = 3,
                explanationHindi = "पुस्तकालयों को मुख्यतः 4 वर्गों—शैक्षणिक, सार्वजनिक, विशिष्ट और राष्ट्रीय पुस्तकालय में बांटा जाता है।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_2"
            ),
            QuestionEntity(
                id = 12L,
                category = UNIT_1,
                questionHindi = "निम्नलिखित में से किसे \"जनता का विश्वविद्यालय\" (People's University) की संज्ञा दी गई है?",
                optionA = "राष्ट्रीय पुस्तकालय",
                optionB = "शैक्षणिक पुस्तकालय",
                optionC = "विशिष्ट पुस्तकालय",
                optionD = "सार्वजनिक पुस्तकालय",
                correctOption = 4,
                explanationHindi = "सार्वजनिक पुस्तकालय बिना किसी भेद के सभी नागरिकों के निरंतर स्व-अध्ययन का केंद्र होता है, इसलिए इसे \"People's University\" कहते हैं।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_2"
            ),
            QuestionEntity(
                id = 13L,
                category = UNIT_1,
                questionHindi = "शैक्षणिक पुस्तकालय (Academic Library) के अंतर्गत निम्नलिखित में से कौन-सा शामिल नहीं है?",
                optionA = "स्कूल पुस्तकालय",
                optionB = "अनुसंधान संस्थान पुस्तकालय (Research Institute Library)",
                optionC = "कॉलेज पुस्तकालय",
                optionD = "विश्वविद्यालय पुस्तकालय",
                correctOption = 2,
                explanationHindi = "अनुसंधान संस्थान पुस्तकालय 'विशिष्ट पुस्तकालय' (Special Library) के अंतर्गत आता है, शैक्षणिक नहीं।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_2"
            ),
            QuestionEntity(
                id = 14L,
                category = UNIT_1,
                questionHindi = "\"पुस्तकालय किसी भी विश्वविद्यालय का हृदय स्थल होता है\" (Library is the heart of the university) — यह विचार मुख्यतः किस आयोग/सिद्धांत से जुड़ा है?",
                optionA = "मुदालियर आयोग",
                optionB = "राधाकृष्णन आयोग (विश्वविद्यालय शिक्षा आयोग)",
                optionC = "हंटर आयोग",
                optionD = "कोठारी आयोग",
                correctOption = 2,
                explanationHindi = "डॉ. सर्वपल्ली राधाकृष्णन की अध्यक्षता वाले विश्वविद्यालय शिक्षा आयोग (1948-49) ने पुस्तकालय को विश्वविद्यालय का हृदय बताया था।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_2"
            ),
            QuestionEntity(
                id = 15L,
                category = UNIT_1,
                questionHindi = "यूनेस्को (UNESCO) द्वारा 'सार्वजनिक पुस्तकालय घोषणापत्र' (Public Library Manifesto) सर्वप्रथम किस वर्ष जारी किया गया था?",
                optionA = "1931",
                optionB = "1949",
                optionC = "1972",
                optionD = "1994",
                correctOption = 2,
                explanationHindi = "यूनेस्को ने सार्वजनिक पुस्तकालयों के मार्गदर्शक सिद्धांतों हेतु पहला मैनिफेस्टो 1949 में जारी किया था।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_2"
            ),
            QuestionEntity(
                id = 16L,
                category = UNIT_1,
                questionHindi = "किसी विशिष्ट विषय (जैसे—चिकित्सा, कृषि, अंतरिक्ष विज्ञान) या विशेष संस्थान के वैज्ञानिकों/शोधार्थियों को सेवा प्रदान करने वाले पुस्तकालय को क्या कहा जाता है?",
                optionA = "सार्वजनिक पुस्तकालय",
                optionB = "विशिष्ट पुस्तकालय (Special Library)",
                optionC = "राष्ट्रीय पुस्तकालय",
                optionD = "मोबाइल पुस्तकालय",
                correctOption = 2,
                explanationHindi = "किसी खास विषय क्षेत्र या शोध संस्थान हेतु कार्य करने वाले पुस्तकालय Special Libraries कहलाते हैं।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_2"
            ),
            QuestionEntity(
                id = 17L,
                category = UNIT_1,
                questionHindi = "भारत का राष्ट्रीय पुस्तकालय (National Library of India) कहाँ स्थित है?",
                optionA = "नई दिल्ली",
                optionB = "मुंबई",
                optionC = "कोलकाता",
                optionD = "चेन्नई",
                correctOption = 3,
                explanationHindi = "भारत का राष्ट्रीय पुस्तकालय कोलकाता के बेलवेडियर एस्टेट (Belvedere Estate) में स्थित है।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_2"
            ),
            QuestionEntity(
                id = 18L,
                category = UNIT_1,
                questionHindi = "डिलीवरी ऑफ बुक्स एक्ट (Delivery of Books Act) भारत में किस वर्ष पारित किया गया था?",
                optionA = "1948",
                optionB = "1951",
                optionC = "1954",
                optionD = "1962",
                correctOption = 3,
                explanationHindi = "डिलीवरी ऑफ बुक्स एक्ट 1954 में पारित हुआ (तथा 1956 में इसमें समाचार पत्रों को शामिल किया गया)।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_2"
            ),
            QuestionEntity(
                id = 19L,
                category = UNIT_1,
                questionHindi = "सार्वजनिक पुस्तकालयों के संचालन एवं विकास हेतु सरकार द्वारा एकत्र किए जाने वाले कर को क्या कहा जाता है?",
                optionA = "व्यावसायिक कर",
                optionB = "पुस्तकालय उपकर (Library Cess)",
                optionC = "संपत्ति अधिभार",
                optionD = "मनोरंजन कर",
                correctOption = 2,
                explanationHindi = "कई राज्यों के सार्वजनिक पुस्तकालय अधिनियमों में पुस्तकालयों के वित्तीय पोषण हेतु Library Cess (पुस्तकालय उपकर) का प्रावधान है।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_2"
            ),
            QuestionEntity(
                id = 20L,
                category = UNIT_1,
                questionHindi = "विशिष्ट पुस्तकालयों (Special Libraries) में सामान्यतः किस प्रकार की अध्ययन सामग्री का संग्रह सबसे अधिक महत्वपूर्ण माना जाता है?",
                optionA = "प्राथमिक विद्यालयी पाठ्यपुस्तकें",
                optionB = "दैनिक समाचार पत्र एवं पत्रिकाएँ",
                optionC = "शोध पत्रिकाएँ, तकनीकी रिपोर्ट्स और पेटेंट्स",
                optionD = "उपन्यास एवं कहानियों की पुस्तकें",
                correctOption = 3,
                explanationHindi = "विशिष्ट पुस्तकालयों में नवीनतम अनुसंधान, जर्नल्स, तकनीकी रिपोर्ट्स और पेटेंट सबसे प्राथमिक सामग्री होते हैं।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_2"
            )
        )
    }

    fun getUnit1Subtopic2Material(): StudyMaterialEntity {
        return StudyMaterialEntity(
            id = 2L,
            unitCategory = UNIT_1,
            subTopic = UNIT_1_SUBTOPIC_2,
            youtubeUrl = UNIT_1_SUBTOPIC_2_YOUTUBE_URL,
            youtubeVideoId = "wYSPC7-MJIo",
            youtubeTitle = UNIT_1_SUBTOPIC_2_YOUTUBE_TITLE,
            timestampNotes = "00:16 - पुस्तकालय वर्गीकरण आधार, शैक्षणिक, सार्वजनिक, विशिष्ट एवं राष्ट्रीय पुस्तकालय",
            notesContent = getUnit1Subtopic2Notes(),
            questionsCount = 10,
            dateAddedMillis = System.currentTimeMillis()
        )
    }

    fun getUnit1Subtopic3Notes(): String {
        return """
* सार्वजनिक पुस्तकालय की मूल अवधारणा और उद्देश्य:
   * सार्वजनिक पुस्तकालय वह संस्था है जो जनता के लिए, जनता द्वारा और जनता के धन से संचालित होती है।
   * यह समाज के प्रत्येक नागरिक—चाहे उसकी जाति, पंथ, धर्म, लिंग, आयु, भाषा या सामाजिक स्थिति कुछ भी हो—के लिए बिना किसी भेदभाव के निःशुल्क अथवा नाममात्र के शुल्क पर खुली रहती है।
   * इसे "जनता का विश्वविद्यालय" (People's University) कहा जाता है क्योंकि यह आजीवन स्व-शिक्षा (Lifelong Self-Education) और अनौपचारिक शिक्षा का सबसे सुलभ माध्यम है।

* यूनेस्को सार्वजनिक पुस्तकालय घोषणापत्र (UNESCO Public Library Manifesto):
   * यूनेस्को ने सार्वजनिक पुस्तकालयों के विकास और दर्शन को रेखांकित करने के लिए 1949 में पहला घोषणापत्र जारी किया।
   * इसके बाद इसमें 1972 (इंटरनेशनल बुक ईयर) और 1994 (IFLA के सहयोग से) में महत्वपूर्ण संशोधन किए गए। (2022 में IFLA/UNESCO का अद्यतन संस्करण भी आया)।
   * घोषणापत्र के अनुसार, सार्वजनिक पुस्तकालय सूचना, साक्षरता, शिक्षा और संस्कृति का एक अनिवार्य द्वार है।

* सार्वजनिक पुस्तकालयों का वित्तीय आधार एवं पुस्तकालय उपकर (Library Cess):
   * सार्वजनिक पुस्तकालयों के संचालन के लिए सतत वित्तीय साधन की आवश्यकता होती है। इसके लिए पुस्तकालय उपकर (Library Cess) का प्रावधान किया जाता है।
   * यह उपकर संपत्ति कर (Property Tax), गृह कर (House Tax) या वाहन कर के साथ एक निश्चित प्रतिशत के रूप में लिया जाता है।

* भारत में सार्वजनिक पुस्तकालय अधिनियम (Public Library Acts in India):
   * भारत में सर्वप्रथम सार्वजनिक पुस्तकालय कानून मद्रास सार्वजनिक पुस्तकालय अधिनियम, 1948 के रूप में पारित हुआ (डॉ. एस. आर. रंगनाथन के प्रयासों से)।
   * दूसरा कानून आंध्र प्रदेश (1960) और तीसरा कर्नाटक (1965) में बना।
   * बिहार सार्वजनिक पुस्तकालय और सूचना केंद्र अधिनियम (Bihar Public Libraries and Information Centres Act) वर्ष 2008 में पारित किया गया (यह बिहार राज्य के संदर्भ में अत्यंत महत्वपूर्ण तथ्य है)।
   * भारत के कुल 19 राज्यों में सार्वजनिक पुस्तकालय अधिनियम पारित हो चुके हैं।

* राजा राममोहन राय पुस्तकालय प्रतिष्ठान (RRRLF):
   * स्थापना: मई 1972 (कोलकाता), राजा राममोहन राय की 200वीं जयंती के अवसर पर भारत सरकार के संस्कृति मंत्रालय (Ministry of Culture) द्वारा।
   * उद्देश्य: भारत में सार्वजनिक पुस्तकालय आंदोलन को वित्तीय, तकनीकी और नीतिगत सहयोग देकर सशक्त बनाना।
   * यह देश भर के सार्वजनिक पुस्तकालयों को पुस्तकें, उपकरण और बुनियादी ढांचा विकसित करने हेतु मैचिंग (Matching) और नॉन-मैचिंग अनुदान प्रदान करता है।
        """.trimIndent()
    }

    fun getUnit1Subtopic3Questions(): List<QuestionEntity> {
        return listOf(
            QuestionEntity(
                id = 21L,
                category = UNIT_1,
                questionHindi = "सार्वजनिक पुस्तकालय (Public Library) की सबसे प्रमुख विशेषता क्या है?",
                optionA = "केवल पंजीकृत विद्यार्थियों के लिए सीमित प्रवेश",
                optionB = "जाति, धर्म, लिंग और सामाजिक स्थिति के भेदभाव के बिना सभी नागरिकों के लिए खुला होना",
                optionC = "केवल सरकारी अधिकारियों के लिए दस्तावेज उपलब्ध कराना",
                optionD = "पुस्तकों का व्यावसायिक विक्रय करना",
                correctOption = 2,
                explanationHindi = "सार्वजनिक पुस्तकालय बिना किसी पूर्वाग्रह व भेदभाव के समाज के हर वर्ग के लिए निःशुल्क या सुलभ होता है।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_3"
            ),
            QuestionEntity(
                id = 22L,
                category = UNIT_1,
                questionHindi = "सार्वजनिक पुस्तकालय को अनौपचारिक शिक्षा का माध्यम होने के कारण किस संज्ञा से अभिहित किया जाता है?",
                optionA = "राष्ट्रीय अभिलेखागार",
                optionB = "विशिष्ट शोध केंद्र",
                optionC = "जनता का विश्वविद्यालय (People's University)",
                optionD = "शैक्षणिक संकुल",
                correctOption = 3,
                explanationHindi = "समाज के प्रत्येक आयु वर्ग को निरंतर स्वाध्याय का अवसर देने के कारण इसे \"People's University\" कहा जाता है।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_3"
            ),
            QuestionEntity(
                id = 23L,
                category = UNIT_1,
                questionHindi = "यूनेस्को (UNESCO) ने सार्वजनिक पुस्तकालय घोषणापत्र (Public Library Manifesto) पहली बार किस वर्ष जारी किया था?",
                optionA = "1931",
                optionB = "1949",
                optionC = "1972",
                optionD = "1994",
                correctOption = 2,
                explanationHindi = "यूनेस्को ने सार्वजनिक पुस्तकालयों के अंतरराष्ट्रीय मार्गदर्शक सिद्धांतों हेतु पहला मैनिफेस्टो 1949 में जारी किया था।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_3"
            ),
            QuestionEntity(
                id = 24L,
                category = UNIT_1,
                questionHindi = "स्वतंत्र भारत में सबसे पहला 'सार्वजनिक पुस्तकालय अधिनियम' (Public Library Act) किस राज्य में पारित किया गया था?",
                optionA = "बिहार (1950)",
                optionB = "मद्रास / तमिलनाडु (1948)",
                optionC = "आंध्र प्रदेश (1960)",
                optionD = "कर्नाटक (1965)",
                correctOption = 2,
                explanationHindi = "डॉ. एस. आर. रंगनाथन के मसौदे पर मद्रास पब्लिक लाइब्रेरी एक्ट (1948) स्वतंत्र भारत का पहला अधिनियम था।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_3"
            ),
            QuestionEntity(
                id = 25L,
                category = UNIT_1,
                questionHindi = "बिहार राज्य में 'बिहार राज्य सार्वजनिक पुस्तकालय एवं सूचना केंद्र अधिनियम' किस वर्ष लागू/पारित हुआ?",
                optionA = "1989",
                optionB = "2002",
                optionC = "2008",
                optionD = "2015",
                correctOption = 3,
                explanationHindi = "बिहार में पुस्तकालय अधिनियम वर्ष 2008 में अधिनियमित हुआ, जो राज्य में सार्वजनिक पुस्तकालय व्यवस्था की कानूनी रीढ़ है।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_3"
            ),
            QuestionEntity(
                id = 26L,
                category = UNIT_1,
                questionHindi = "सार्वजनिक पुस्तकालयों के विकास एवं नियमित वित्तीय पोषण हेतु सरकार द्वारा लगाया जाने वाला विशेष कर क्या कहलाता है?",
                optionA = "मनोरंजन कर",
                optionB = "पुस्तकालय उपकर (Library Cess)",
                optionC = "शिक्षा उपकर",
                optionD = "सेवा कर",
                correctOption = 2,
                explanationHindi = "अधिनियमित राज्यों में सार्वजनिक पुस्तकालयों की आय हेतु संपत्ति/गृह कर पर Library Cess (पुस्तकालय उपकर) लगाया जाता है।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_3"
            ),
            QuestionEntity(
                id = 27L,
                category = UNIT_1,
                questionHindi = "राजा राममोहन राय लाइब्रेरी फाउंडेशन (RRRLF) की स्थापना किस वर्ष और कहाँ की गई थी?",
                optionA = "1972, कोलकाता",
                optionB = "1954, नई दिल्ली",
                optionC = "1948, मद्रास",
                optionD = "1985, मुंबई",
                correctOption = 1,
                explanationHindi = "RRRLF की स्थापना मई 1972 में कोलकाता (पश्चिम बंगाल) में हुई थी।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_3"
            ),
            QuestionEntity(
                id = 28L,
                category = UNIT_1,
                questionHindi = "RRRLF (राजा राममोहन राय पुस्तकालय प्रतिष्ठान) भारत सरकार के किस मंत्रालय के अधीन एक स्वायत्त संस्था के रूप में कार्य करता है?",
                optionA = "शिक्षा मंत्रालय (Ministry of Education)",
                optionB = "संस्कृति मंत्रालय (Ministry of Culture)",
                optionC = "सूचना एवं प्रसारण मंत्रालय",
                optionD = "गृह मंत्रालय",
                correctOption = 2,
                explanationHindi = "RRRLF भारत सरकार के संस्कृति मंत्रालय (Ministry of Culture) के अधीन कार्य करता है।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_3"
            ),
            QuestionEntity(
                id = 29L,
                category = UNIT_1,
                questionHindi = "भारत में अब तक कुल कितने राज्यों में सार्वजनिक पुस्तकालय विधान (Public Library Acts) पारित किए जा चुके हैं?",
                optionA = "12 राज्यों में",
                optionB = "16 राज्यों में",
                optionC = "19 राज्यों में",
                optionD = "28 राज्यों में",
                correctOption = 3,
                explanationHindi = "भारत में वर्तमान में कुल 19 राज्यों ने अपने सार्वजनिक पुस्तकालय कानून लागू किए हैं।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_3"
            ),
            QuestionEntity(
                id = 30L,
                category = UNIT_1,
                questionHindi = "सार्वजनिक पुस्तकालय के सुचारू संचालन और समाज के सभी वर्गों तक उसकी पहुंच सुनिश्चित करने के लिए सबसे प्रभावी कानूनी उपाय क्या है?",
                optionA = "निजी दानदाताओं पर पूर्ण निर्भरता",
                optionB = "पुस्तकालय विधान (Library Legislation) का निर्माण एवं क्रियान्वयन",
                optionC = "सदस्यता शुल्क में अत्यधिक वृद्धि",
                optionD = "केवल बड़े शहरों तक सीमित रखना",
                correctOption = 2,
                explanationHindi = "पुस्तकालय विधान (Library Legislation) पुस्तकालयों को वैधानिक संरक्षण, स्थायी वित्तीय स्रोत और प्रशासनिक ढांचा प्रदान करता है।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_3"
            )
        )
    }

    fun getUnit1Subtopic3Material(): StudyMaterialEntity {
        return StudyMaterialEntity(
            id = 3L,
            unitCategory = UNIT_1,
            subTopic = UNIT_1_SUBTOPIC_3,
            youtubeUrl = UNIT_1_SUBTOPIC_3_YOUTUBE_URL,
            youtubeVideoId = "uCEcYZpYJzU",
            youtubeTitle = UNIT_1_SUBTOPIC_3_YOUTUBE_TITLE,
            timestampNotes = "00:19 - सार्वजनिक पुस्तकालय अवधारणा, UNESCO मैनिफेस्टो, Library Cess, पुस्तकालय अधिनियम एवं RRRLF",
            notesContent = getUnit1Subtopic3Notes(),
            questionsCount = 10,
            dateAddedMillis = System.currentTimeMillis()
        )
    }

    fun getUnit1Subtopic4Notes(): String {
        return """
* राष्ट्रीय पुस्तकालय का मुख्य उद्देश्य एवं संवैधानिक प्रावधान:
   * उद्देश्य: किसी भी देश के राष्ट्रीय पुस्तकालय की स्थापना मुख्यतः दो कारणों से की जाती है:
      1. राष्ट्रीय महत्व के साहित्य व दस्तावेजों (जैसे—भारत के मूल हस्तलिखित संविधान आदि) का संग्रह एवं स्थायी संरक्षण करना।
      2. राष्ट्रीय ग्रंथ सूची (National Bibliography - INB) का संकलन एवं नियमित प्रकाशन करना।
   * संवैधानिक स्थिति: भारतीय संविधान की 7वीं अनुसूची के अनुच्छेद 62 (संघ सूची / Union List, Entry 62) के तहत इसे 'राष्ट्रीय महत्व की संस्था' घोषित किया गया है।

* ऐतिहासिक विकासक्रम (Historical Evolution):
   1. कलकत्ता पब्लिक लाइब्रेरी (CPL):
      * स्थापना: 21 मार्च 1836।
      * प्रमुख संस्थापक सदस्य: द्वारकानाथ टैगोर और लॉर्ड चार्ल्स मेटकाफ के सहयोग से निजी उद्यम के रूप में स्थापित।
   2. इंपीरियल लाइब्रेरी (Imperial Library):
      * स्थापना: 1891 में विभिन्न सरकारी व सचिवालयीय पुस्तकालयों को मिलाकर की गई।
   3. विलय एवं पुनर्गठन (1902–1903):
      * इंपीरियल लाइब्रेरी एक्ट, 1902 के तहत तत्कालीन वायसराय लॉर्ड कर्जन के प्रयासों से कलकत्ता पब्लिक लाइब्रेरी और इंपीरियल लाइब्रेरी का विलय कर दिया गया।
      * 30 जनवरी 1903 को यह पुस्तकालय मेटकाफ हॉल में आम जनता के उपयोग हेतु विधिवत खोला गया।
   4. स्वतंत्रता उपरांत रूपांतरण (1948–1953):
      * इंपीरियल लाइब्रेरी (चेंज ऑफ नेम) एक्ट, 1948 द्वारा इसका नाम बदलकर 'नेशनल लाइब्रेरी ऑफ इंडिया' (National Library of India) किया गया और इसे बेलवेडियर एस्टेट (कोलकाता) में स्थानांतरित किया गया।
      * 1 फरवरी 1953 को भारत के तत्कालीन शिक्षा मंत्री मौलाना अबुल कलाम आज़ाद ने इसे राष्ट्र की आम जनता को समर्पित किया।

* प्रमुख लाइब्रेरियन एवं प्रशासनिक पद:
   * इंपीरियल लाइब्रेरी के प्रथम लाइब्रेरियन: जॉन मैकफर्लेन (John Macfarlane)।
   * प्रथम भारतीय लाइब्रेरियन: हरिनाथ डे (Harinath De), 1907–1911।
   * स्वतंत्रता पूर्व सर्वाधिक कार्यकाल वाले लाइब्रेरियन: के. एम. असदुल्लाह खान (K. M. Asadullah), 1930–1947।
   * स्वतंत्र भारत के राष्ट्रीय पुस्तकालय के प्रथम लाइब्रेरियन: बी. एस. केशवन (B. S. Kesavan), 1947–1962 (इन्हें INB का जनक कहा जाता है)।
   * 1977 में शीर्ष पद का नाम 'निदेशक' (Director) हुआ (प्रथम निदेशक: प्रो. आर. के. दासगुप्ता)।
   * वर्तमान में सर्वोच्च प्रशासनिक पद महानिदेशक (Director General - DG) का है; 13वें निदेशक प्रो. स्वप्न चक्रवर्ती प्रथम डीजी बने।

* डिलीवरी ऑफ बुक्स एक्ट एवं INB (Indian National Bibliography):
   * Delivery of Books Act, 1954 (1956 में समाचार पत्र संशोधित) के तहत देश में प्रकाशित प्रत्येक सामग्री की एक प्रति 30 दिनों के भीतर नेशनल लाइब्रेरी को भेजना अनिवार्य है।
   * इस अधिनियम के अंतर्गत प्राप्त पुस्तकों के आधार पर सेंट्रल रेफरेंस लाइब्रेरी (CRL, Kolkata) द्वारा 15 अगस्त 1958 को भारतीय राष्ट्रीय ग्रंथ सूची (INB) का प्रथम संस्करण जारी किया गया।
   * INB वर्ष 2000 से कंप्यूटरीकृत है तथा वर्तमान में इसकी प्रकाशन आवृत्ति मासिक (Monthly) है। इसमें डीडीसी (DDC) और सीसी (CC) दोनों वर्गीकरण पद्धतियों का उल्लेख रहता है।

* तथ्यात्मक विवरण एवं संचालन:
   * मंत्रालय: यह भारत सरकार के संस्कृति मंत्रालय (Ministry of Culture) के अधीन कार्य करता है।
   * कार्य दिवस व समय: वर्ष के 362 दिन खुला रहता है (केवल तीन राष्ट्रीय अवकाश—26 जनवरी, 15 अगस्त और 2 अक्टूबर को बंद)। दैनिक समय: सुबह 9:00 बजे से रात 8:00 बजे तक (11 घंटे)।
   * यह परिसर लगभग 30 एकड़ क्षेत्र में विस्तृत है।
        """.trimIndent()
    }

    fun getUnit1Subtopic4Questions(): List<QuestionEntity> {
        return listOf(
            QuestionEntity(
                id = 31L,
                category = UNIT_1,
                questionHindi = "भारतीय राष्ट्रीय पुस्तकालय (National Library of India) का उल्लेख भारतीय संविधान की किस अनुसूची एवं अनुच्छेद के अंतर्गत 'राष्ट्रीय महत्व की संस्था' के रूप में मिलता है?",
                optionA = "8वीं अनुसूची, अनुच्छेद 343",
                optionB = "7वीं अनुसूची, अनुच्छेद 62",
                optionC = "6वीं अनुसूची, अनुच्छेद 51A",
                optionD = "9वीं अनुसूची, अनुच्छेद 31",
                correctOption = 2,
                explanationHindi = "7वीं अनुसूची के अनुच्छेद 62 के तहत इसे संसद द्वारा घोषित राष्ट्रीय महत्व का संस्थान माना गया है।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_4"
            ),
            QuestionEntity(
                id = 32L,
                category = UNIT_1,
                questionHindi = "कलकत्ता पब्लिक लाइब्रेरी (Calcutta Public Library) की स्थापना किस तिथि को हुई थी?",
                optionA = "21 मार्च 1836",
                optionB = "15 अगस्त 1891",
                optionC = "30 जनवरी 1903",
                optionD = "1 फरवरी 1953",
                correctOption = 1,
                explanationHindi = "कलकत्ता पब्लिक लाइब्रेरी की स्थापना 21 मार्च 1836 को हुई थी।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_4"
            ),
            QuestionEntity(
                id = 33L,
                category = UNIT_1,
                questionHindi = "कलकत्ता पब्लिक लाइब्रेरी के प्रमुख संस्थापक एवं प्रथम प्रोप्राइटर कौन थे?",
                optionA = "राजा राममोहन राय",
                optionB = "ईश्वरचंद्र विद्यासागर",
                optionC = "द्वारकानाथ टैगोर",
                optionD = "बंकिम चंद्र चटर्जी",
                correctOption = 3,
                explanationHindi = "बाबू द्वारकानाथ टैगोर कलकत्ता पब्लिक लाइब्रेरी के पहले प्रोप्राइटर/संस्थापक सदस्य थे।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_4"
            ),
            QuestionEntity(
                id = 34L,
                category = UNIT_1,
                questionHindi = "किस वायसराय के प्रयासों से कलकत्ता पब्लिक लाइब्रेरी और इंपीरियल लाइब्रेरी का विलय कर 1902 में 'इंपीरियल लाइब्रेरी एक्ट' पारित किया गया?",
                optionA = "लॉर्ड विलियम बेंटिंक",
                optionB = "लॉर्ड डलहौजी",
                optionC = "लॉर्ड कर्जन",
                optionD = "लॉर्ड माउंटबेटन",
                correctOption = 3,
                explanationHindi = "तत्कालीन वायसराय लॉर्ड कर्जन ने 1902 में कानून बनाकर दोनों संस्थाओं का विलय किया।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_4"
            ),
            QuestionEntity(
                id = 35L,
                category = UNIT_1,
                questionHindi = "इंपीरियल लाइब्रेरी को मेटकाफ हॉल में आम जनता के अध्ययन हेतु किस तिथि को खोला गया था?",
                optionA = "21 मार्च 1836",
                optionB = "30 जनवरी 1903",
                optionC = "15 अगस्त 1947",
                optionD = "26 जनवरी 1950",
                correctOption = 2,
                explanationHindi = "मेटकाफ हॉल में इंपीरियल लाइब्रेरी को 30 जनवरी 1903 को जनता के लिए खोला गया।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_4"
            ),
            QuestionEntity(
                id = 36L,
                category = UNIT_1,
                questionHindi = "इंपीरियल लाइब्रेरी के प्रथम लाइब्रेरियन (Head Librarian) कौन नियुक्त किए गए थे?",
                optionA = "चार्ल्स मेटकाफ",
                optionB = "जॉन मैकफर्लेन",
                optionC = "बी. एस. केशवन",
                optionD = "डॉ. एस. आर. रंगनाथन",
                correctOption = 2,
                explanationHindi = "ब्रिटिश म्यूजियम के जॉन मैकफर्लेन इंपीरियल लाइब्रेरी के पहले हेड लाइब्रेरियन बने।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_4"
            ),
            QuestionEntity(
                id = 37L,
                category = UNIT_1,
                questionHindi = "इंपीरियल लाइब्रेरी के पद पर नियुक्त होने वाले प्रथम भारतीय (First Indian Librarian) कौन थे?",
                optionA = "हरिनाथ डे",
                optionB = "के. एम. असदुल्लाह",
                optionC = "बी. एस. केशवन",
                optionD = "आर. के. दासगुप्ता",
                correctOption = 1,
                explanationHindi = "प्रख्यात विद्वान व भाषाविद हरिनाथ डे 1907 से 1911 तक इसके पहले भारतीय लाइब्रेरियन रहे।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_4"
            ),
            QuestionEntity(
                id = 38L,
                category = UNIT_1,
                questionHindi = "स्वतंत्र भारत के राष्ट्रीय पुस्तकालय (National Library of India) के प्रथम लाइब्रेरियन कौन बने, जिन्हें \"फादर ऑफ INB\" भी कहा जाता है?",
                optionA = "पी. एन. कौला",
                optionB = "बी. एस. केशवन",
                optionC = "एस. बशीरुद्दीन",
                optionD = "डॉ. एस. आर. रंगनाथन",
                correctOption = 2,
                explanationHindi = "बी. एस. केशवन स्वतंत्र भारत के पहले लाइब्रेरियन थे और उन्होंने INB का संपादन शुरू किया।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_4"
            ),
            QuestionEntity(
                id = 39L,
                category = UNIT_1,
                questionHindi = "स्वतंत्रता के पश्चात 1 फरवरी 1953 को भारत के राष्ट्रीय पुस्तकालय का विधिवत उद्घाटन किसके द्वारा किया गया था?",
                optionA = "डॉ. राजेंद्र प्रसाद",
                optionB = "पंडित जवाहरलाल नेहरू",
                optionC = "मौलाना अबुल कलाम आज़ाद",
                optionD = "डॉ. सर्वपल्ली राधाकृष्णन",
                correctOption = 3,
                explanationHindi = "तत्कालीन केंद्रीय शिक्षा मंत्री मौलाना अबुल कलाम आज़ाद ने 1 फरवरी 1953 को इसका उद्घाटन किया।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_4"
            ),
            QuestionEntity(
                id = 40L,
                category = UNIT_1,
                questionHindi = "'इंडियन नेशनल बिब्लियोग्राफी' (INB) का प्रथम संस्करण किस ऐतिहासिक तिथि को प्रकाशित किया गया था?",
                optionA = "26 जनवरी 1950",
                optionB = "15 अगस्त 1958",
                optionC = "2 अक्टूबर 1962",
                optionD = "1 जनवरी 1954",
                correctOption = 2,
                explanationHindi = "डिलीवरी ऑफ बुक्स एक्ट के तहत प्राप्त पुस्तकों के आधार पर 15 अगस्त 1958 को पहला INB प्रकाशित हुआ।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_4"
            ),
            QuestionEntity(
                id = 41L,
                category = UNIT_1,
                questionHindi = "वर्तमान में 'इंडियन नेशनल बिब्लियोग्राफी' (INB) की प्रकाशन आवृत्ति (Frequency) क्या है?",
                optionA = "साप्ताहिक (Weekly)",
                optionB = "पाक्षिक (Fortnightly)",
                optionC = "मासिक (Monthly)",
                optionD = "त्रैमासिक (Quarterly)",
                correctOption = 3,
                explanationHindi = "INB वर्ष 2000 में कंप्यूटरीकृत हुई और वर्तमान में मासिक (Monthly) प्रकाशित होती है।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_4"
            ),
            QuestionEntity(
                id = 42L,
                category = UNIT_1,
                questionHindi = "भारतीय राष्ट्रीय पुस्तकालय वर्ष में कुल कितने दिन पाठकों के उपयोग हेतु खुला रहता है?",
                optionA = "300 दिन",
                optionB = "350 दिन",
                optionC = "362 दिन",
                optionD = "365 दिन",
                correctOption = 3,
                explanationHindi = "यह संस्थान वर्ष भर में 362 दिन खुला रहता है।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_4"
            ),
            QuestionEntity(
                id = 43L,
                category = UNIT_1,
                questionHindi = "भारतीय राष्ट्रीय पुस्तकालय वर्ष में किन तीन राष्ट्रीय अवकाशों पर पूर्णतः बंद रहता है?",
                optionA = "होली, दिवाली, ईद",
                optionB = "26 जनवरी, 15 अगस्त, 2 अक्टूबर",
                optionC = "1 जनवरी, 15 अगस्त, 25 दिसंबर",
                optionD = "बुद्ध पूर्णिमा, महावीर जयंती, गुरु नानक जयंती",
                correctOption = 2,
                explanationHindi = "यह केवल तीन राष्ट्रीय पर्वों (26 जनवरी - गणतंत्र दिवस, 15 अगस्त - स्वतंत्रता दिवस, 2 अक्टूबर - गांधी जयंती) पर बंद रहता है।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_4"
            ),
            QuestionEntity(
                id = 44L,
                category = UNIT_1,
                questionHindi = "भारतीय राष्ट्रीय पुस्तकालय वर्तमान में भारत सरकार के किस मंत्रालय के प्रशासनिक नियंत्रण में कार्य करता है?",
                optionA = "शिक्षा मंत्रालय",
                optionB = "संस्कृति मंत्रालय (Ministry of Culture)",
                optionC = "विज्ञान एवं प्रौद्योगिकी मंत्रालय",
                optionD = "सूचना एवं प्रसारण मंत्रालय",
                correctOption = 2,
                explanationHindi = "राष्ट्रीय पुस्तकालय भारत सरकार के संस्कृति मंत्रालय (Ministry of Culture) के प्रशासनिक नियंत्रण में है।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_4"
            ),
            QuestionEntity(
                id = 45L,
                category = UNIT_1,
                questionHindi = "वर्ष 1977 में राष्ट्रीय पुस्तकालय के मुख्य प्रशासनिक पद को लाइब्रेरियन से बदलकर निदेशक (Director) किया गया; इसके प्रथम निदेशक कौन थे?",
                optionA = "प्रो. आर. के. दासगुप्ता",
                optionB = "प्रो. स्वप्न चक्रवर्ती",
                optionC = "प्रो. अजय प्रताप सिंह",
                optionD = "डॉ. बी. एस. केशवन",
                correctOption = 1,
                explanationHindi = "1977 में लाइब्रेरियन के स्थान पर निदेशक पद बनाया गया और प्रो. आर. के. दासगुप्ता इसके पहले निदेशक बने।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_4"
            )
        )
    }

    fun getUnit1Subtopic4Material(): StudyMaterialEntity {
        return StudyMaterialEntity(
            id = 4L,
            unitCategory = UNIT_1,
            subTopic = UNIT_1_SUBTOPIC_4,
            youtubeUrl = UNIT_1_SUBTOPIC_4_YOUTUBE_URL,
            youtubeVideoId = "YdvxrwbvaAs",
            youtubeTitle = UNIT_1_SUBTOPIC_4_YOUTUBE_TITLE,
            timestampNotes = "00:16 - राष्ट्रीय पुस्तकालय उद्देश्य, संवैधानिक स्थिति, CPL से नेशनल लाइब्रेरी विकासक्रम, प्रमुख लाइब्रेरियन व INB",
            notesContent = getUnit1Subtopic4Notes(),
            questionsCount = 15,
            dateAddedMillis = System.currentTimeMillis()
        )
    }

    fun getUnit1Subtopic5Notes(): String {
        return """
* विशिष्ट पुस्तकालय का अर्थ एवं परिभाषा:
   * विशिष्ट पुस्तकालय (Special Library) वह पुस्तकालय है जो मुख्य रूप से किसी विशेष विषय (Specific Subject), विशेष संस्था या विशेष पाठक वर्ग (Special Users) की सूचना संबंधी आवश्यकताओं को पूरा करने के लिए स्थापित किया जाता है।
   * इसमें दो चीजें "विशिष्ट" होती हैं:
      1. पाठक (User): जो सामान्य जनता न होकर वैज्ञानिक, शोधार्थी (Researcher), डॉक्टर, इंजीनियर या विशेषज्ञ होते हैं।
      2. संसाधन (Resources): जो पारंपरिक पुस्तकों के बजाय शोध-पत्रिकाओं और तकनीकी दस्तावेजों पर केंद्रित होते हैं।

* संग्रह एवं अध्ययन सामग्री (Collection & Resources):
   * विशिष्ट पुस्तकालयों में सामान्य ज्ञान या मनोरंजन की पुस्तकें (जैसे उपन्यास, कहानियाँ) नहीं रखी जाती हैं।
   * इनका मुख्य संग्रह अनुसंधान पत्रिकाओं (Research Journals), शोध प्रबंध (Thesis), तकनीकी रिपोर्ट (Technical Reports), पेटेंट (Patents), मानक (Standards) और गैर-पुस्तक सामग्री (Non-book materials जैसे- माइक्रोफिल्म, डेटाबेस) पर आधारित होता है।

* मुख्य उद्देश्य:
   * विशिष्ट पुस्तकालय का प्राथमिक उद्देश्य अपने मातृ संगठन (Parent Organization) के उद्देश्यों की प्राप्ति में सहायता करना है।
   * डॉ. एस. आर. रंगनाथन के अनुसार विशिष्ट पुस्तकालय का काम पाठकों को "Pin-pointed, Exhaustive and Expeditious" (सटीक, संपूर्ण और त्वरित) सूचना प्रदान करना है।

* विशिष्ट पुस्तकालयों द्वारा प्रदान की जाने वाली सेवाएँ (Services): विशिष्ट पुस्तकालय अपने पाठकों के समय की बचत करने के लिए कुछ उन्नत सेवाएँ प्रदान करते हैं:
   * CAS (Current Awareness Service - सामयिक अभिज्ञता सेवा): नवीनतम शोध और सूचनाओं से पाठकों को अवगत कराना।
   * SDI (Selective Dissemination of Information - चयनित सूचना प्रसार सेवा): पाठक की व्यक्तिगत रुचि (User Profile) के अनुसार चुनिंदा सूचना सीधे उस तक पहुँचाना।
   * अनुवाद सेवा (Translation Service): विदेशी भाषाओं के शोध पत्रों का स्थानीय भाषा में अनुवाद।
   * सारकरण एवं अनुक्रमणीकरण सेवा (Abstracting & Indexing Service): बड़े शोध पत्रों का संक्षिप्त रूप प्रदान करना।

* विशिष्ट पुस्तकालयों के उदाहरण:
   * चिकित्सा पुस्तकालय (Medical Libraries), विधि पुस्तकालय (Law Libraries), कृषि पुस्तकालय (Agricultural Libraries)।
   * अनुसंधान संस्थानों के पुस्तकालय जैसे: ISRO, DRDO, CSIR, ICAR आदि के पुस्तकालय।
   * औद्योगिक और कॉर्पोरेट घरानों के पुस्तकालय।
        """.trimIndent()
    }

    fun getUnit1Subtopic5Questions(): List<QuestionEntity> {
        return listOf(
            QuestionEntity(
                id = 46L,
                category = UNIT_1,
                questionHindi = "विशिष्ट पुस्तकालय (Special Library) मुख्य रूप से किसके लिए स्थापित किए जाते हैं?",
                optionA = "स्कूल के छोटे बच्चों के लिए",
                optionB = "आम जनता के मनोरंजन के लिए",
                optionC = "किसी विशेष संगठन के शोधार्थियों एवं विशेषज्ञों के लिए",
                optionD = "कॉलेज के स्नातक विद्यार्थियों के लिए",
                correctOption = 3,
                explanationHindi = "विशिष्ट पुस्तकालय केवल किसी विशिष्ट संगठन, उद्योग या शोध केंद्र के वैज्ञानिकों और विशेषज्ञों (Special Users) को सेवा देते हैं।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_5"
            ),
            QuestionEntity(
                id = 47L,
                category = UNIT_1,
                questionHindi = "निम्नलिखित में से कौन-सी अध्ययन सामग्री एक विशिष्ट पुस्तकालय का प्रमुख हिस्सा होती है?",
                optionA = "कॉमिक्स और बाल साहित्य",
                optionB = "उपन्यास और नाटक",
                optionC = "पेटेंट, मानक और शोध पत्रिकाएँ (Journals)",
                optionD = "सामान्य ज्ञान की गाइड बुक्स",
                correctOption = 3,
                explanationHindi = "इनमें पारंपरिक पुस्तकों की बजाय प्राथमिक स्रोत जैसे पेटेंट्स (Patents), मानक (Standards) और शोध पत्रिकाओं (Journals) का अधिक संग्रह होता है।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_5"
            ),
            QuestionEntity(
                id = 48L,
                category = UNIT_1,
                questionHindi = "विशिष्ट पुस्तकालयों में 'SDI' का पूर्ण रूप (Full Form) क्या है?",
                optionA = "Standard Document Information",
                optionB = "Selective Dissemination of Information",
                optionC = "System Design Interface",
                optionD = "Serial Data Indexing",
                correctOption = 2,
                explanationHindi = "SDI का अर्थ Selective Dissemination of Information (चयनित सूचना प्रसार सेवा) है, जिसे H.P. Luhn (1958) ने प्रतिपादित किया था।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_5"
            ),
            QuestionEntity(
                id = 49L,
                category = UNIT_1,
                questionHindi = "पाठकों को उनके विषय क्षेत्र में हो रहे नवीनतम विकास और शोध से निरंतर अवगत कराने वाली सेवा क्या कहलाती है?",
                optionA = "संदर्भ सेवा (Reference Service)",
                optionB = "सामयिक अभिज्ञता सेवा (CAS - Current Awareness Service)",
                optionC = "अनुवाद सेवा (Translation Service)",
                optionD = "परिसंचरण सेवा (Circulation Service)",
                correctOption = 2,
                explanationHindi = "CAS (Current Awareness Service) पाठकों को उनके क्षेत्र में हो रहे नवीनतम अनुसंधानों से अपडेट रखती है।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_5"
            ),
            QuestionEntity(
                id = 50L,
                category = UNIT_1,
                questionHindi = "\"सटीक, संपूर्ण और त्वरित (Pin-pointed, Exhaustive, and Expeditious) सूचना प्रदान करना\" किस पुस्तकालय का मुख्य लक्ष्य है?",
                optionA = "सार्वजनिक पुस्तकालय",
                optionB = "स्कूल पुस्तकालय",
                optionC = "विशिष्ट पुस्तकालय",
                optionD = "राष्ट्रीय पुस्तकालय",
                correctOption = 3,
                explanationHindi = "रंगनाथन जी के अनुसार, एक विशिष्ट पुस्तकालय का कार्य उपयोगकर्ता को कम से कम समय में बिल्कुल सटीक (Pin-pointed) सूचना उपलब्ध कराना है।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_5"
            ),
            QuestionEntity(
                id = 51L,
                category = UNIT_1,
                questionHindi = "निम्नलिखित में से कौन-सा एक विशिष्ट पुस्तकालय का उदाहरण है?",
                optionA = "दिल्ली पब्लिक लाइब्रेरी",
                optionB = "भारतीय अंतरिक्ष अनुसंधान संगठन (ISRO) का पुस्तकालय",
                optionC = "पटना विश्वविद्यालय पुस्तकालय",
                optionD = "राष्ट्रीय पुस्तकालय, कोलकाता",
                correctOption = 2,
                explanationHindi = "ISRO का पुस्तकालय केवल अंतरिक्ष विज्ञान के वैज्ञानिकों के लिए कार्य करता है, अतः यह एक विशिष्ट पुस्तकालय (Special Library) है।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_5"
            ),
            QuestionEntity(
                id = 52L,
                category = UNIT_1,
                questionHindi = "विशिष्ट पुस्तकालयों में किस प्रकार की सेवा की सर्वाधिक मांग रहती है जो सामान्य पुस्तकालयों में प्रायः नहीं दी जाती?",
                optionA = "पुस्तक उधार देना (Book Lending)",
                optionB = "वाचनालय सुविधा (Reading Room)",
                optionC = "सारकरण एवं अनुवाद सेवा (Abstracting & Translation Service)",
                optionD = "मोबाइल लाइब्रेरी सेवा",
                correctOption = 3,
                explanationHindi = "विदेशी भाषा के शोध पत्रों का अनुवाद (Translation) और बड़े लेखों का सार (Abstracting) बनाना विशिष्ट पुस्तकालयों की प्रमुख विशेषता है।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_5"
            ),
            QuestionEntity(
                id = 53L,
                category = UNIT_1,
                questionHindi = "किसी संस्था (जैसे- DRDO या CSIR) का पुस्तकालय जो पूर्णतः उस संस्था के कर्मचारियों और वैज्ञानिकों को सेवा देता है, कहलाता है?",
                optionA = "शैक्षणिक पुस्तकालय",
                optionB = "सार्वजनिक पुस्तकालय",
                optionC = "राष्ट्रीय पुस्तकालय",
                optionD = "विशिष्ट पुस्तकालय",
                correctOption = 4,
                explanationHindi = "मातृ संस्था (Parent Organization) के उद्देश्यों की पूर्ति के लिए स्थापित पुस्तकालय विशिष्ट पुस्तकालय की श्रेणी में आते हैं।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_5"
            ),
            QuestionEntity(
                id = 54L,
                category = UNIT_1,
                questionHindi = "डॉ. एस. आर. रंगनाथन के पुस्तकालय विज्ञान के किस सूत्र की पूर्ति विशिष्ट पुस्तकालयों की SDI और CAS सेवाओं द्वारा सबसे अधिक होती है?",
                optionA = "प्रथम सूत्र (पुस्तकें उपयोग के लिए हैं)",
                optionB = "द्वितीय सूत्र (प्रत्येक पाठक को उसकी पुस्तक मिले)",
                optionC = "चतुर्थ सूत्र (पाठक का समय बचाएं)",
                optionD = "पंचम सूत्र (पुस्तकालय एक वर्धनशील संस्था है)",
                correctOption = 3,
                explanationHindi = "CAS और SDI जैसी सेवाएं शोधार्थी को सूचना खोजने में लगने वाले समय को बचाती हैं, जो \"पाठक का समय बचाएं\" (Save the time of the user) सूत्र को चरितार्थ करता है।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_5"
            ),
            QuestionEntity(
                id = 55L,
                category = UNIT_1,
                questionHindi = "विशिष्ट पुस्तकालयों का बजट मुख्यतः किस पर निर्भर करता है?",
                optionA = "पुस्तकालय उपकर (Library Cess) पर",
                optionB = "मातृ संस्था (Parent Organization) द्वारा आवंटित अनुदान पर",
                optionC = "आम जनता के चंदे पर",
                optionD = "राज्य सरकार के शिक्षा विभाग पर",
                correctOption = 2,
                explanationHindi = "विशिष्ट पुस्तकालयों का अपना कोई स्वतंत्र आय का स्रोत नहीं होता; वे पूरी तरह से अपनी मातृ संस्था (Parent Institution) के बजट पर निर्भर होते हैं।",
                keyHighlight = "उप-विषय: $UNIT_1_SUBTOPIC_5"
            )
        )
    }

    fun getUnit1Subtopic5Material(): StudyMaterialEntity {
        return StudyMaterialEntity(
            id = 5L,
            unitCategory = UNIT_1,
            subTopic = UNIT_1_SUBTOPIC_5,
            youtubeUrl = UNIT_1_SUBTOPIC_5_YOUTUBE_URL,
            youtubeVideoId = "wD8Bue1vdGQ",
            youtubeTitle = UNIT_1_SUBTOPIC_5_YOUTUBE_TITLE,
            timestampNotes = "विशिष्ट पुस्तकालय अर्थ, संग्रह, उद्देश्य (Pin-pointed, Exhaustive & Expeditious), CAS, SDI, अनुवाद सेवा व बजट",
            notesContent = getUnit1Subtopic5Notes(),
            questionsCount = 10,
            dateAddedMillis = System.currentTimeMillis()
        )
    }

    fun getInitialQuestions(): List<QuestionEntity> =
        getUnit1Subtopic1Questions() +
        getUnit1Subtopic2Questions() +
        getUnit1Subtopic3Questions() +
        getUnit1Subtopic4Questions() +
        getUnit1Subtopic5Questions() +
        Unit6Questions.getAllQuestions()
}

