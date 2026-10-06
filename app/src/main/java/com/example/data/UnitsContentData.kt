package com.example.data

import com.example.data.model.QuestionEntity

/**
 * Rich Study Notes and DPP Practice Questions for Units 2, 3, 4, and 5
 * Bihar Librarian Eligibility Test (BLET) 2026 Curriculum
 */
object UnitsContentData {

    // =========================================================================
    // UNIT 2: पुस्तकालय वर्गीकरण एवं सूचीकरण (Library Classification & Cataloging)
    // =========================================================================

    fun getUnit2Subtopic1Notes(): String = """
# 📖 उपविषय 01: पुस्तकालय वर्गीकरण के सिद्धांत एवं पद्धतियां
**परीक्षा दृष्टिकोण (BLET 2026 विशेष)**

---
### 1. पुस्तकालय वर्गीकरण (Library Classification) का अर्थ:
* **परिभाषा:** ज्ञान के अगाध सागर में प्रलेखों (पुस्तकों, पत्रिकाओं आदि) को उनकी विषय-वस्तु की समानता एवं असमानता के आधार पर एक व्यवस्थित क्रम में सजाना ही पुस्तकालय वर्गीकरण कहलाता है।
* **डॉ. एस. आर. रंगनाथन के अनुसार:** "वर्गीकरण किसी विशिष्ट विषय के अनुसार प्रलेखों का अनुक्रमिक प्रतिरूपण (Artificial Language of Ordinal Numbers) है।"

### 2. वर्गीकरण के उद्देश्य:
1. पाठकों को उनकी वांछित पुस्तक तुरंत उपलब्ध कराना (चतुर्थ सूत्र: पाठक का समय बचाएं)।
2. शेल्फ पर पुस्तकों को विषयवार व्यवस्थित (Helpful Sequence) रखना।
3. नई पुस्तकों को उनके उचित स्थान पर स्वतः प्रविष्ट कराना।
4. वीडिंग आउट (Weeding out) एवं भंडार सत्यापन में सहायता करना।

### 3. प्रमुख वर्गीकरण पद्धतियां (Major Schemes):
* **DDC (Dewey Decimal Classification):** 1876 में मेलविल डेवी (Melvil Dewey) द्वारा विकसित। शुद्ध अंकन (Pure Notation - इंडो-अरबी अंक 0-9)।
* **EC (Expansive Classification):** 1891 में सी. ए. कटर (C. A. Cutter) द्वारा।
* **UDC (Universal Decimal Classification):** 1905 में पॉल ऑटलेट (Paul Otlet) व हेनरी ला फोंटेन (Henri La Fontaine) द्वारा। FID द्वारा प्रबंधित।
* **LC (Library of Congress Classification):** 1904 में जे. सी. एम. हैनसन द्वारा। मिश्रित अंकन।
* **SC (Subject Classification):** 1906 में जे. डी. ब्राउन (J. D. Brown) द्वारा।
* **CC (Colon Classification):** 1933 में भारत में डॉ. एस. आर. रंगनाथन द्वारा। विश्लेषणात्मक-संश्लेषणात्मक (Analytico-Synthetic) पद्धति।
* **BC (Bibliographic Classification):** 1935 में एच. ई. ब्लिस (H. E. Bliss) द्वारा।
* **BSO (Broad System of Ordering):** 1978 में यूनेस्को/FID द्वारा।

### 4. अंकन (Notation):
* **शुद्ध अंकन (Pure Notation):** केवल एक प्रकार के प्रतीकों का प्रयोग (उदा. DDC में केवल 0-9 अंक)।
* **मिश्रित अंकन (Mixed Notation):** एक से अधिक प्रकार के प्रतीकों का प्रयोग (उदा. CC में अक्षर, अंक एवं विराम चिह्न)।
    """.trimIndent()

    fun getUnit2Subtopic1Questions(): List<QuestionEntity> = listOf(
        QuestionEntity(
            id = 201L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "विश्व की प्रथम आधुनिक पुस्तकालय वर्गीकरण पद्धति (DDC) का प्रथम संस्करण किस वर्ष प्रकाशित हुआ था?",
            optionA = "1870",
            optionB = "1876",
            optionC = "1885",
            optionD = "1891",
            correctOption = 2,
            explanationHindi = "मेलविल डेवी ने 1876 में Amherst College (USA) में DDC का प्रथम संस्करण 44 पृष्ठों में गुमनाम रूप से प्रकाशित किया था।",
            keyHighlight = "उप-विषय: पुस्तकालय वर्गीकरण के सिद्धांत"
        ),
        QuestionEntity(
            id = 202L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "द्विविंदु वर्गीकरण (Colon Classification - CC) किस प्रकार की वर्गीकरण पद्धति है?",
            optionA = "शुद्ध परिगणनात्मक (Purely Enumerative)",
            optionB = "लगभग परिगणनात्मक (Almost Enumerative)",
            optionC = "मुक्त पक्षात्मक (Freely Faceted)",
            optionD = "अपरिवर्तनीय पक्षात्मक (Rigidly Faceted)",
            correctOption = 3,
            explanationHindi = "डॉ. रंगनाथन की CC का 7वां संस्करण (1987) मुक्त पक्षात्मक (Freely Faceted) वर्गीकरण पद्धति का उत्कृष्ट उदाहरण है।",
            keyHighlight = "उप-विषय: पुस्तकालय वर्गीकरण के सिद्धांत"
        ),
        QuestionEntity(
            id = 203L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "UDC (Universal Decimal Classification) के विकास का श्रेय किन्हें जाता है?",
            optionA = "मेलविल डेवी व सी. ए. कटर",
            optionB = "पॉल ऑटलेट एवं हेनरी ला फोंटेन",
            optionC = "डॉ. रंगनाथन एवं जे. डी. ब्राउन",
            optionD = "एच. ई. ब्लिस एवं जे. सी. एम. हैनसन",
            correctOption = 2,
            explanationHindi = "UDC को 1895 में स्थापित International Institute of Bibliography (IIB) के अंतर्गत बेल्जियम के दो विद्वानों - पॉल ऑटलेट और नोबेल विजेता हेनरी ला फोंटेन ने विकसित किया था (प्रथम संस्करण 1905)।",
            keyHighlight = "उप-विषय: पुस्तकालय वर्गीकरण के सिद्धांत"
        ),
        QuestionEntity(
            id = 204L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "वर्गीकरण में 'अंकन' (Notation) के संबंध में DDC में किस प्रकार के अंकन का प्रयोग होता है?",
            optionA = "मिश्रित अंकन (Mixed Notation)",
            optionB = "शुद्ध अंकन (Pure Notation)",
            optionC = "वर्णमाला आधारित अंकन",
            optionD = "संकेतात्मक अंकन",
            correctOption = 2,
            explanationHindi = "DDC में केवल इंडो-अरबी अंकों (0, 1, 2, 3, 4, 5, 6, 7, 8, 9) का प्रयोग होता है, अतः यह शुद्ध अंकन (Pure Notation) है।",
            keyHighlight = "उप-विषय: पुस्तकालय वर्गीकरण के सिद्धांत"
        ),
        QuestionEntity(
            id = 205L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "Subject Classification (SC - 1906) का निर्माण किस पुस्तकालयाध्यक्ष द्वारा किया गया था?",
            optionA = "जे. डी. ब्राउन (J. D. Brown)",
            optionB = "सी. ए. कटर",
            optionC = "मेलविल डेवी",
            optionD = "एच. ई. ब्लिस",
            correctOption = 1,
            explanationHindi = "जेम्स डफ ब्राउन (J. D. Brown) ने 1906 में ब्रिटेन में 'सब्जेक्ट क्लासिफिकेशन' (SC) का निर्माण किया था जिसमें 'One-Place Theory' का सिद्धांत दिया गया था।",
            keyHighlight = "उप-विषय: पुस्तकालय वर्गीकरण के सिद्धांत"
        )
    )

    fun getUnit2Subtopic2Notes(): String = """
# 📖 उपविषय 02: द्विविंदु वर्गीकरण (CC) एवं मेलविल डेवी (DDC)
**परीक्षा दृष्टिकोण (BLET 2026 विशेष)**

---
### 1. कोलन वर्गीकरण (Colon Classification - CC):
* **प्रणेता:** डॉ. एस. आर. रंगनाथन (1933 में प्रथम संस्करण - मद्रास लाइब्रेरी एसोसिएशन)।
* **संस्करण:** कुल 7 संस्करण (1933, 1939, 1950, 1952, 1957, 1960, 1987)।
* **6वां पुनर्मुद्रित संस्करण (1963):** भारत में सर्वाधिक प्रयुक्त एवं प्रतियोगी परीक्षाओं में पूछा जाने वाला संस्करण।
* **PMEST का सिद्धांत (मौलिक श्रेणियां):**
  1. **[P] Personality (व्यक्तित्व):** सबसे कठिन पहचाने जाने योग्य। योजक चिह्न (Connecting symbol) = अल्पविराम (,)
  2. **[M] Matter (पदार्थ):** वस्तु की भौतिक सामग्री/गुण। योजक चिह्न = अर्धविराम (;)
  3. **[E] Energy (ऊर्जा):** क्रिया, समस्या, हल। योजक चिह्न = कोलन (:)
  4. **[S] Space (स्थान):** भौगोलिक सीमा, देश, राज्य। योजक चिह्न = बिंदु (.)
  5. **[T] Time (काल):** समय, शताब्दी, वर्ष। योजक चिह्न = एकल उद्धरण चिह्न (') [6वें संस्करण में बिंदु (.) था, 1963 में ' हुआ]।

### 2. डेवी डेसिमल वर्गीकरण (DDC):
* **प्रणेता:** मेलविल डेवी (1851-1931), फादर ऑफ अमेरिकन लाइब्रेरियनशिप।
* **मुख्य 10 वर्ग (10 Main Classes):**
  * 000 - कंप्यूटर विज्ञान, सूचना एवं सामान्य कार्य (Generalities)
  * 100 - दर्शनशास्त्र एवं मनोविज्ञान (Philosophy & Psychology)
  * 200 - धर्म (Religion)
  * 300 - सामाजिक विज्ञान (Social Sciences)
  * 400 - भाषा (Language)
  * 500 - प्राकृतिक विज्ञान व गणित (Pure Sciences)
  * 600 - प्रौद्योगिकी एवं व्यावहारिक विज्ञान (Technology/Applied Sciences)
  * 700 - कला एवं मनोरंजन (The Arts)
  * 800 - साहित्य (Literature)
  * 900 - इतिहास, भूगोल एवं जीवनी (History & Geography)
* **नवीनतम संस्करण:** 23वां संस्करण (2011 में 4 खंडों में प्रकाशित), वर्तमान में WebDewey के रूप में ऑनलाइन उपलब्ध।
    """.trimIndent()

    fun getUnit2Subtopic2Questions(): List<QuestionEntity> = listOf(
        QuestionEntity(
            id = 206L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "डॉ. रंगनाथन के अनुसार PMEST में 'ऊर्जा' (Energy - [E]) पक्ष का योजक चिह्न (Connecting Symbol) क्या है?",
            optionA = "अर्धविराम (;)",
            optionB = "कोलन (:)",
            optionC = "बिंदु (.)",
            optionD = "अल्पविराम (,)",
            correctOption = 2,
            explanationHindi = "PMEST में योजक चिह्न: Personality = अल्पविराम (,), Matter = अर्धविराम (;), Energy = कोलन (:), Space = बिंदु (.), Time = उल्टा उद्धरण चिह्न (')।",
            keyHighlight = "उप-विषय: द्विविंदु वर्गीकरण एवं DDC"
        ),
        QuestionEntity(
            id = 207L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "DDC के अनुसार 300 मुख्य वर्ग किसको प्रदर्शित करता है?",
            optionA = "दर्शनशास्त्र एवं मनोविज्ञान",
            optionB = "धर्म",
            optionC = "सामाजिक विज्ञान (Social Sciences)",
            optionD = "भाषा विज्ञान",
            correctOption = 3,
            explanationHindi = "DDC में 100 = दर्शनशास्त्र, 200 = धर्म, 300 = सामाजिक विज्ञान (Social Sciences), 400 = भाषा, 500 = प्राकृतिक विज्ञान।",
            keyHighlight = "उप-विषय: द्विविंदु वर्गीकरण एवं DDC"
        ),
        QuestionEntity(
            id = 208L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "DDC का 23वां संस्करण किस वर्ष 4 खंडों में प्रकाशित हुआ था?",
            optionA = "2003",
            optionB = "2008",
            optionC = "2011",
            optionD = "2015",
            correctOption = 3,
            explanationHindi = "DDC का 23वां संस्करण मई 2011 में जोआन एस. मिशेल (Joan S. Mitchell) के संपादन में 4 खंडों में प्रकाशित हुआ था।",
            keyHighlight = "उप-विषय: द्विविंदु वर्गीकरण एवं DDC"
        ),
        QuestionEntity(
            id = 209L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "कोलन वर्गीकरण (CC) में 'काल' (Time - [T]) पक्ष के लिए 6वें संस्करण के पुनर्मुद्रण (1963) में कौन सा योजक चिह्न निर्धारित किया गया?",
            optionA = "बिंदु (.)",
            optionB = "एकल उद्धरण चिह्न (') [Single inverted comma]",
            optionC = "योजक चिह्न (-)",
            optionD = "तीर चिह्न (->)",
            correctOption = 2,
            explanationHindi = "1963 में डॉ. रंगनाथन ने Space और Time के भेद को स्पष्ट करने हेतु Time के लिए बिंदु (.) के स्थान पर एकल उद्धरण चिह्न (') का प्रावधान किया।",
            keyHighlight = "उप-विषय: द्विविंदु वर्गीकरण एवं DDC"
        ),
        QuestionEntity(
            id = 210L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "DDC में 'फिनिक्स सारणी' (Phoenix Schedule) का अर्थ क्या होता है?",
            optionA = "एक अप्रचलित सारणी",
            optionB = "पूर्णतया नवीन रूप से पुनर्संशोधित सारणी (Completely revamped schedule)",
            optionC = "भौगोलिक सारणी",
            optionD = "भाषा सारणी",
            correctOption = 2,
            explanationHindi = "DDC में फिनिक्स सारणी वह सारणी होती है जिसमें किसी वर्ग को पुराने क्रम को पूरी तरह त्याग कर नए सिरे से पुनर्गठित किया जाता है (जैसे 16वें संस्करण में 540 रसायन विज्ञान)।",
            keyHighlight = "उप-विषय: द्विविंदु वर्गीकरण एवं DDC"
        )
    )

    fun getUnit2Subtopic3Notes(): String = """
# 📖 उपविषय 03: पुस्तकालय सूचीकरण (CCC एवं AACR-2)
**परीक्षा दृष्टिकोण (BLET 2026 विशेष)**

---
### 1. पुस्तकालय सूची (Library Catalogue):
* **अर्थ:** पुस्तकालय में उपलब्ध पाठ्य सामग्रियों की एक व्यवस्थित सूची, जो पाठकों को लेखक, शीर्षक या विषय के माध्यम से पुस्तक तक पहुँचने में सक्षम बनाती है।
* **सी. ए. कटर (1876):** "Rules for a Dictionary Catalogue" (RDC) में सूची के उद्देश्यों का प्रथम वैज्ञानिक निरूपण किया।

### 2. वर्गीकृत सूची संहिता (Classified Catalogue Code - CCC):
* **प्रणेता:** डॉ. एस. आर. रंगनाथन (1934 में प्रथम संस्करण)।
* **संस्करण:** कुल 5 संस्करण (1934, 1945, 1951, 1958, 1964 - ए. नीलगमेघन के सहयोग से)।
* **भाग:** वर्गीकृत सूची में दो मुख्य भाग होते हैं:
  1. **वर्गीकृत भाग (Classified Part):** क्रमांक प्रविष्टियां (Call Number Entries) - यह मुख्य भाग है।
  2. **वर्णानुक्रमिक भाग (Alphabetical Part):** लेखक, शीर्षक, विषय (Class Index Entries, Book Index Entries, Cross Reference Index Entries)।
* **CCC मुख्य प्रविष्टि के 6 अनुच्छेद (6 Sections of Main Entry):**
  1. अग्रिम अनुच्छेद (Leading Section) - कॉल नंबर (पेंसिल से)
  2. शीर्षक अनुच्छेद (Heading Section) - लेखक का नाम
  3. आख्या अनुच्छेद (Title Section) - पुस्तक का शीर्षक, संस्करण, सहकारक
  4. टिप्पणी अनुच्छेद (Note Section) - श्रृंखला आदि
  5. परिग्रहण क्रमांक (Accession Number) - सबसे नीचे बाईं ओर
  6. संज्ञापन (Tracing) - कार्ड के पिछले भाग पर

### 3. एंग्लो-अमेरिकन कैटलॉगिंग रूल्स (AACR-2):
* **इतिहास:** 1908 (Catalog Rules), 1949 (ALA Rules), 1967 (AACR-1), 1978 (AACR-2 - माइकल गोरमैन व पॉल विंकलर द्वारा)।
* **AACR-2R:** 1988 में पुनरीक्षित संस्करण आया।
* **मानक कार्ड का आकार:** 5 x 3 इंच (12.5 x 7.5 सेमी)।
    """.trimIndent()

    fun getUnit2Subtopic3Questions(): List<QuestionEntity> = listOf(
        QuestionEntity(
            id = 211L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "वर्गीकृत सूची संहिता (CCC) के प्रणेता डॉ. रंगनाथन द्वारा इसका प्रथम संस्करण किस वर्ष प्रकाशित किया गया?",
            optionA = "1928",
            optionB = "1933",
            optionC = "1934",
            optionD = "1945",
            correctOption = 3,
            explanationHindi = "डॉ. एस. आर. रंगनाथन ने 1934 में मद्रास लाइब्रेरी एसोसिएशन द्वारा Classified Catalogue Code (CCC) का प्रथम संस्करण प्रकाशित किया था।",
            keyHighlight = "उप-विषय: पुस्तकालय सूचीकरण"
        ),
        QuestionEntity(
            id = 212L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "मानक पुस्तकालय सूची पत्रक (Catalogue Card) का अंतरराष्ट्रीय माप क्या होता है?",
            optionA = "12.5 x 7.5 सेमी (5 x 3 इंच)",
            optionB = "15 x 10 सेमी (6 x 4 इंच)",
            optionC = "10 x 5 सेमी (4 x 2 इंच)",
            optionD = "14 x 8 सेमी",
            correctOption = 1,
            explanationHindi = "पुस्तकालय सूची पत्रक का मानक अंतरराष्ट्रीय आकार 12.5 सेमी चौड़ाई और 7.5 सेमी ऊंचाई (5 x 3 इंच) होता है।",
            keyHighlight = "उप-विषय: पुस्तकालय सूचीकरण"
        ),
        QuestionEntity(
            id = 213L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "CCC की मुख्य प्रविष्टि (Main Entry) में परिग्रहण क्रमांक (Accession Number) कहाँ लिखा जाता है?",
            optionA = "अग्रिम अनुच्छेद में",
            optionB = "शीर्षक अनुच्छेद में",
            optionC = "कार्ड के सबसे निचले भाग में बाईं ओर",
            optionD = "कार्ड के पिछले भाग में",
            correctOption = 3,
            explanationHindi = "CCC में Accession Number को कार्ड के सबसे निचले अनुच्छेद में बाईं ओर पहली ऊर्ध्वाधर रेखा से लिखा जाता है।",
            keyHighlight = "उप-विषय: पुस्तकालय सूचीकरण"
        ),
        QuestionEntity(
            id = 214L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "AACR-2 (1978) के संयुक्त संपादक कौन थे?",
            optionA = "माइकल गोरमैन एवं पॉल विंकलर",
            optionB = "मेलविल डेवी एवं सी. ए. कटर",
            optionC = "डॉ. रंगनाथन एवं नीलगमेघन",
            optionD = "हेनरी ला फोंटेन एवं पॉल ऑटलेट",
            correctOption = 1,
            explanationHindi = "AACR-2 का संपादन 1978 में माइकल गोरमैन (Michael Gorman) और पॉल विंकलर (Paul W. Winkler) द्वारा किया गया था।",
            keyHighlight = "उप-विषय: पुस्तकालय सूचीकरण"
        ),
        QuestionEntity(
            id = 215L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "पुस्तकालय सूची के 'Rules for a Dictionary Catalogue' (1876) का प्रतिपादन किसने किया था?",
            optionA = "सी. ए. कटर (C. A. Cutter)",
            optionB = "एंथोनी पानिजी",
            optionC = "मेलविल डेवी",
            optionD = "डब्ल्यू. सी. बी. सेयर्स",
            correctOption = 1,
            explanationHindi = "चार्ल्स एमी कटर (C. A. Cutter) ने 1876 में Rules for a Dictionary Catalogue का प्रकाशन किया, जिसे सूचीकरण का मील का पत्थर माना जाता है।",
            keyHighlight = "उप-विषय: पुस्तकालय सूचीकरण"
        )
    )

    fun getUnit2Subtopic4Notes(): String = """
# 📖 उपविषय 04: सूची प्रविष्टियों के प्रकार एवं OPAC
**परीक्षा दृष्टिकोण (BLET 2026 विशेष)**

---
### 1. प्रविष्टियों के प्रकार (Types of Entries):
* **मुख्य प्रविष्टि (Main Entry):** पुस्तक के संबंध में संपूर्ण एवं मूल ग्रंथपरक विवरण देने वाली प्राथमिक प्रविष्टि।
* **इतर/सहायक प्रविष्टियां (Added Entries):** मुख्य प्रविष्टि के अतिरिक्त अन्य शीर्षकों (सह-लेखक, संपादक, अनुवादक, ग्रंथमाला, विषय आदि) के अधीन तैयार की गई प्रविष्टियां।
* **विषय प्रविष्टि (Subject Entry):** पुस्तक के विषय के आधार पर तैयार की गई प्रविष्टि (Class Index Entry)।
* **निर्देश प्रविष्टि (Cross Reference Entry - CRE):** पाठक को एक शीर्षक से दूसरे शीर्षक की ओर निर्देशित करने वाली प्रविष्टि (See / See Also)।

### 2. OPAC (Online Public Access Catalogue):
* **अर्थ:** पुस्तकालय के कंप्यूटर डेटाबेस में संग्रहित डिजिटल सूची जिसे पाठक स्वयं कंप्यूटर टर्मिनल या वेब ब्राउज़र के माध्यम से खोज सकते हैं।
* **विशेषताएं:**
  * बूलियन ऑपरेटर्स (AND, OR, NOT) द्वारा खोज।
  * वाइल्डकार्ड (*, ?) सर्च की सुविधा।
  * वास्तविक समय में पुस्तक की उपलब्धता (Available / Checked out) की जानकारी।
* **Web-OPAC:** इंटरनेट के माध्यम से विश्व में कहीं से भी किसी भी समय पुस्तकालय सूची को खोजना।
    """.trimIndent()

    fun getUnit2Subtopic4Questions(): List<QuestionEntity> = listOf(
        QuestionEntity(
            id = 216L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "OPAC का पूर्ण रूप क्या है?",
            optionA = "Online Public Access Catalogue",
            optionB = "Offline Public Access Card",
            optionC = "Open Public Article Collection",
            optionD = "Online Private Access Catalogue",
            correctOption = 1,
            explanationHindi = "OPAC का पूर्ण रूप 'Online Public Access Catalogue' (ऑनलाइन पब्लिक एक्सेस कैटलॉग) है।",
            keyHighlight = "उप-विषय: सूची प्रविष्टियां एवं OPAC"
        ),
        QuestionEntity(
            id = 217L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "पुस्तकालय सूचीकरण में 'Tracing' (संज्ञापन) का मुख्य उद्देश्य क्या होता है?",
            optionA = "पुस्तक की कीमत दर्ज करना",
            optionB = "तैयार की गई सभी सहायक प्रविष्टियों (Added Entries) का रिकॉर्ड रखना",
            optionC = "पाठक का पता लिखना",
            optionD = "प्रकाशक की जानकारी देना",
            correctOption = 2,
            explanationHindi = "Tracing मुख्य प्रविष्टि के पीछे लिखी जाती है ताकि यह ज्ञात रहे कि इस पुस्तक के लिए कौन-कौन सी इतर/सहायक प्रविष्टियां बनाई गई हैं, जिससे पुस्तक हटने पर वे भी हटाई जा सकें।",
            keyHighlight = "उप-विषय: सूची प्रविष्टियां एवं OPAC"
        ),
        QuestionEntity(
            id = 218L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "OPAC में बहु-शब्द खोज हेतु प्रयुक्त 'बूलियन ऑपरेटर्स' (Boolean Operators) कौन से हैं?",
            optionA = "IN, AT, ON",
            optionB = "AND, OR, NOT",
            optionC = "PLUS, MINUS, EQUAL",
            optionD = "YES, NO, MAYBE",
            correctOption = 2,
            explanationHindi = "जॉर्ज बूले द्वारा प्रतिपादित बूलियन लॉजिक में तीन प्रमुख ऑपरेटर्स AND, OR, NOT हैं, जिनका उपयोग सूचना पुनर्प्राप्ति में होता है।",
            keyHighlight = "उप-विषय: सूची प्रविष्टियां एवं OPAC"
        ),
        QuestionEntity(
            id = 219L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "CCC में 'Class Index Entry' (CIE) का निर्माण किस विधि द्वारा किया जाता है?",
            optionA = "श्रृंखला प्रक्रिया (Chain Procedure)",
            optionB = "सीयर्स लिस्ट द्वारा",
            optionC = "कटर सूची द्वारा",
            optionD = "शब्दकोश विधि द्वारा",
            correctOption = 1,
            explanationHindi = "डॉ. रंगनाथन ने 1938 में विषय शीर्षक व्युत्पन्न करने हेतु 'श्रृंखला प्रक्रिया' (Chain Procedure) का आविष्कार किया, जिससे CIE तैयार की जाती है।",
            keyHighlight = "उप-विषय: सूची प्रविष्टियां एवं OPAC"
        ),
        QuestionEntity(
            id = 220L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "Web-OPAC की सबसे प्रमुख विशेषता क्या है?",
            optionA = "यह केवल प्रिंट रूप में उपलब्ध होता है",
            optionB = "इंटरनेट के माध्यम से 24x7 दूरस्थ स्थानों से भी कैटलॉग खोज की जा सकती है",
            optionC = "इसमें पुस्तकों की केवल कीमत दिखती है",
            optionD = "यह केवल पुस्तकालय कर्मचारियों के लिए होता है",
            correctOption = 2,
            explanationHindi = "Web-OPAC किसी भी वेब ब्राउज़र और इंटरनेट कनेक्शन के माध्यम से दुनिया के किसी भी कोने से चौबीसों घंटे खोजने की सुविधा देता है।",
            keyHighlight = "उप-विषय: सूची प्रविष्टियां एवं OPAC"
        )
    )

    fun getUnit2Subtopic5Notes(): String = """
# 📖 उपविषय 05: मेटाडेटा मानक (MARC 21, Dublin Core) एवं विषय अनुक्रमण
**परीक्षा दृष्टिकोण (BLET 2026 विशेष)**

---
### 1. मेटाडेटा (Metadata):
* **परिभाषा:** "डेटा अबाउट डेटा" (Data about Data) अर्थात् किसी सूचना संसाधन का वर्णन करने वाला संरचित डेटा।

### 2. MARC 21 (Machine Readable Cataloging):
* **इतिहास:** हेनरीटा अवराम (Henrietta Avram) द्वारा 1966 में लाइब्रेरी ऑफ कांग्रेस में MARC विकसित किया गया। 1999 में USMARC व CAN/MARC मिलकर MARC 21 बना।
* **प्रमुख MARC 21 टैग्स (महत्वपूर्ण परीक्षा प्रश्न):**
  * **020:** ISBN (International Standard Book Number)
  * **022:** ISSN (International Standard Serial Number)
  * **082:** DDC क्लासिफिकेशन नंबर
  * **100:** मुख्य प्रविष्टि - व्यक्तिगत लेखक (Personal Author)
  * **245:** आख्या कथन (Title and Statement of Responsibility)
  * **250:** संस्करण कथन (Edition Statement)
  * **260 / 264:** प्रकाशन विवरण (Publication, Distribution - Place, Publisher, Year)
  * **300:** भौतिक विवरण (Physical Description - Pages, Size)
  * **650:** विषय अतिरिक्त प्रविष्टि (Subject Added Entry)

### 3. डबलिन कोर मेटाडेटा मानक (Dublin Core - DC):
* **उत्पत्ति:** 1995 में डबलिन, ओहायो (USA) में OCLC कार्यशाला में निर्मित।
* **तत्व (Elements):** कुल 15 मुख्य कोर तत्व होते हैं:
  Title, Creator, Subject, Description, Publisher, Contributor, Date, Type, Format, Identifier, Source, Language, Relation, Coverage, Rights।

### 4. विषय शीर्षक सूचियां (Subject Headings):
* **SLSH (Sears List of Subject Headings):** 1923 में मिनी अर्ल सीयर्स द्वारा (छोटे व मध्यम पुस्तकालयों हेतु)।
* **LCSH (Library of Congress Subject Headings):** बड़े शोध पुस्तकालयों हेतु।
    """.trimIndent()

    fun getUnit2Subtopic5Questions(): List<QuestionEntity> = listOf(
        QuestionEntity(
            id = 221L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "MARC 21 प्रारूप में टैग '245' (Tag 245) किसको निर्दिष्ट करता है?",
            optionA = "व्यक्तिगत लेखक (Personal Author)",
            optionB = "आख्या कथन एवं उत्तरदायित्व (Title Statement)",
            optionC = "प्रकाशन विवरण",
            optionD = "ISBN नंबर",
            correctOption = 2,
            explanationHindi = "MARC 21 में टैग 245 पुस्तक के शीर्षक (Title) और जिम्मेदारी कथन (Statement of Responsibility) के लिए आरक्षित है।",
            keyHighlight = "उप-विषय: मेटाडेटा मानक एवं विषय शीर्षक"
        ),
        QuestionEntity(
            id = 222L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "डबलिन कोर मेटाडेटा तत्व सेट (Dublin Core Metadata Initiative - DCMI) में कुल कितने मौलिक तत्व (Elements) हैं?",
            optionA = "10",
            optionB = "12",
            optionC = "15",
            optionD = "21",
            correctOption = 3,
            explanationHindi = "डबलिन कोर में कुल 15 कोर तत्व होते हैं (Title, Creator, Subject, Description, Publisher, Contributor, Date, Type, Format, Identifier, Source, Language, Relation, Coverage, Rights)।",
            keyHighlight = "उप-विषय: मेटाडेटा मानक एवं विषय शीर्षक"
        ),
        QuestionEntity(
            id = 223L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "मशीन पठनीय सूचीकरण (MARC) का विकास किस संस्था में हेनरीटा अवराम द्वारा किया गया?",
            optionA = "ब्रिटिश लाइब्रेरी (London)",
            optionB = "लाइब्रेरी ऑफ कांग्रेस (Washington DC)",
            optionC = "नेशनल लाइब्रेरी ऑफ इंडिया",
            optionD = "यूनेस्को पेरिस",
            correctOption = 2,
            explanationHindi = "हेनरीटा अवराम ने 1960 के दशक के मध्य में Library of Congress (USA) में कंप्यूटर आधारित सूचीकरण हेतु MARC प्रणाली का विकास किया।",
            keyHighlight = "उप-विषय: मेटाडेटा मानक एवं विषय शीर्षक"
        ),
        QuestionEntity(
            id = 224L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "सीयर्स लिस्ट ऑफ सब्जेक्ट हेडिंग्स (SLSH) का प्रथम संस्करण 1923 में किसके द्वारा तैयार किया गया?",
            optionA = "मिनी अर्ल सीयर्स (Minnie Earl Sears)",
            optionB = "मेलविल डेवी",
            optionC = "चार्ल्स एमी कटर",
            optionD = "एस. आर. रंगनाथन",
            correctOption = 1,
            explanationHindi = "मिनी अर्ल सीयर्स ने 1923 में छोटे और मध्यम आकार के पुस्तकालयों की जरूरतों को पूरा करने के लिए Sears List of Subject Headings तैयार की थी।",
            keyHighlight = "उप-विषय: मेटाडेटा मानक एवं विषय शीर्षक"
        ),
        QuestionEntity(
            id = 225L,
            category = DefaultQuestions.UNIT_2,
            questionHindi = "MARC 21 में पुस्तक के ISBN (International Standard Book Number) के लिए कौन सा टैग प्रयुक्त होता है?",
            optionA = "Tag 020",
            optionB = "Tag 022",
            optionC = "Tag 100",
            optionD = "Tag 260",
            correctOption = 1,
            explanationHindi = "MARC 21 में Tag 020 ISBN के लिए और Tag 022 ISSN (पत्रिकाओं हेतु) के लिए प्रयुक्त होता है।",
            keyHighlight = "उप-विषय: मेटाडेटा मानक एवं विषय शीर्षक"
        )
    )

    // =========================================================================
    // UNIT 3: पुस्तकालय प्रबंधन एवं विभाग (Library Management & Sections)
    // =========================================================================

    fun getUnit3Subtopic1Notes(): String = """
# 📖 उपविषय 01: पुस्तकालय प्रबंधन: POSDCORB, टेलर व फेयोल के सिद्धांत
**परीक्षा दृष्टिकोण (BLET 2026 विशेष)**

---
### 1. प्रबंधन (Management) की अवधारणा:
* **परिभाषा:** उपलब्ध मानव एवं भौतिक संसाधनों का अधिकतम उपयोग करते हुए पूर्व-निर्धारित लक्ष्यों को प्राप्त करने की कला व विज्ञान ही प्रबंधन है।

### 2. POSDCORB का सिद्धांत:
* **प्रणेता:** लूथर गुलिक (Luther Gulick) एवं एल. उर्विक (L. Urwick) ने 1937 में लोक प्रशासन एवं प्रबंधन हेतु दिया।
* **संक्षिप्त रूप का विस्तार:**
  * **P** - Planning (नियोजन/योजना बनाना)
  * **O** - Organizing (संगठन करना)
  * **S** - Staffing (कर्मचारी प्रबंधन/नियुक्ति)
  * **D** - Directing (निर्देशन देना)
  * **CO** - Coordinating (समन्वय स्थापित करना)
  * **R** - Reporting (प्रतिवेदन/रिपोर्टिंग)
  * **B** - Budgeting (बजट निर्माण)

### 3. हेनरी फेयोल (Henri Fayol) के 14 सिद्धांत (फादर ऑफ क्लासिकल मैनेजमेंट):
* कार्य विभाजन (Division of Work), अधिकार एवं उत्तरदायित्व (Authority & Responsibility), अनुशासन (Discipline), आदेश की एकता (Unity of Command), निर्देश की एकता (Unity of Direction), व्यक्तिगत हित के ऊपर सामान्य हित, पारिश्रमिक, केंद्रीकरण, सोपान श्रृंखला (Scalar Chain), व्यवस्था (Order), समता (Equity), कर्मचारियों के कार्यकाल की स्थिरता, पहल क्षमता (Initiative), सहयोग की भावना (Esprit de Corps)।

### 4. वैज्ञानिक प्रबंधन (Scientific Management):
* **प्रणेता:** एफ. डब्ल्यू. टेलर (F. W. Taylor) - फादर ऑफ साइंटिफिक मैनेजमेंट।
* **सिद्धांत:** समय अध्ययन (Time Study), गति अध्ययन (Motion Study), थकान अध्ययन (Fatigue Study), कार्य का मानकीकरण।
    """.trimIndent()

    fun getUnit3Subtopic1Questions(): List<QuestionEntity> = listOf(
        QuestionEntity(
            id = 301L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "प्रबंधन के प्रसिद्ध सूत्र 'POSDCORB' का प्रतिपादन किसके द्वारा किया गया?",
            optionA = "लूथर गुलिक एवं एल. उर्विक",
            optionB = "हेनरी फेयोल",
            optionC = "एफ. डब्ल्यू. टेलर",
            optionD = "पीटर ड्रकर",
            correctOption = 1,
            explanationHindi = "लूथर गुलिक और लिंडल उर्विक ने 1937 में प्रबंधन के कार्यों को समाहित करते हुए 'POSDCORB' परिवर्णी शब्द दिया था।",
            keyHighlight = "उप-विषय: पुस्तकालय प्रबंधन के सिद्धांत"
        ),
        QuestionEntity(
            id = 302L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "हेनरी फेयोल ने प्रबंधन के कितने मूलभूत सिद्धांतों का प्रतिपादन किया था?",
            optionA = "5",
            optionB = "10",
            optionC = "12",
            optionD = "14",
            correctOption = 4,
            explanationHindi = "हेनरी फेयोल ने अपनी पुस्तक 'General and Industrial Management' (1916) में प्रबंधन के 14 सिद्धांत दिए थे।",
            keyHighlight = "उप-विषय: पुस्तकालय प्रबंधन के सिद्धांत"
        ),
        QuestionEntity(
            id = 303L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "'वैज्ञानिक प्रबंधन का पिता' (Father of Scientific Management) किसे कहा जाता है?",
            optionA = "एफ. डब्ल्यू. टेलर (F. W. Taylor)",
            optionB = "मैक्स वेबर",
            optionC = "एल्टन मेयो",
            optionD = "डॉ. रंगनाथन",
            correctOption = 1,
            explanationHindi = "फ्रेडरिक विंसलो टेलर (F. W. Taylor) को वैज्ञानिक प्रबंधन का जनक माना जाता है, जिन्होंने समय एवं गति अध्ययन की शुरुआत की।",
            keyHighlight = "उप-विषय: पुस्तकालय प्रबंधन के सिद्धांत"
        ),
        QuestionEntity(
            id = 304L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "प्रबंधन में 'MBO' (Management by Objectives) की अवधारणा 1954 में किसके द्वारा दी गई?",
            optionA = "पीटर एफ. ड्रकर (Peter Drucker)",
            optionB = "लूथर गुलिक",
            optionC = "हेनरी फेयोल",
            optionD = "डगलस मैकग्रेगर",
            correctOption = 1,
            explanationHindi = "पीटर ड्रकर ने 1954 में अपनी पुस्तक 'The Practice of Management' में 'उद्देश्यों द्वारा प्रबंधन' (MBO) का सिद्धांत प्रतिपादित किया था।",
            keyHighlight = "उप-विषय: पुस्तकालय प्रबंधन के सिद्धांत"
        ),
        QuestionEntity(
            id = 305L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "POSDCORB में अक्षर 'CO' किस शब्द को प्रदर्शित करता है?",
            optionA = "Communication (संचार)",
            optionB = "Coordinating (समन्वय)",
            optionC = "Cooperation (सहयोग)",
            optionD = "Controlling (नियंत्रण)",
            correctOption = 2,
            explanationHindi = "POSDCORB में 'CO' का अर्थ Coordinating (विभिन्न विभागों एवं कर्मचारियों के मध्य तालमेल व समन्वय स्थापित करना) होता है।",
            keyHighlight = "उप-विषय: पुस्तकालय प्रबंधन के सिद्धांत"
        )
    )

    fun getUnit3Subtopic2Notes(): String = """
# 📖 उपविषय 02: अर्जन अनुभाग एवं पुस्तक चयन सिद्धांत
**परीक्षा दृष्टिकोण (BLET 2026 विशेष)**

---
### 1. अर्जन अनुभाग (Acquisition Section):
* पुस्तकालय में प्रलेखों (पुस्तकों, पत्रिकाओं आदि) का चयन, आदेश, प्राप्ति, सत्यापन एवं परिग्रहण का कार्य इसी अनुभाग द्वारा किया जाता है।

### 2. पुस्तक चयन के प्रसिद्ध सिद्धांत (Book Selection Principles):
1. **मेलविल डेवी (1876):** "न्यूनतम लागत पर अधिकतम पाठकों के लिए सर्वोत्तम पठन सामग्री" (The best reading for the largest number at the least cost)।
2. **फ्रांसिस ड्रूरी (F. K. W. Drury - 1930):** "उचित पाठक को उचित समय पर उचित पुस्तक प्रदान करना" (To provide the right book to the right reader at the right time)।
3. **डॉ. एस. आर. रंगनाथन (1952 - Library Book Selection):**
   * प्रथम तीन सूत्रों (पुस्तकें उपयोगार्थ हैं, प्रत्येक पाठक को उसकी पुस्तक, प्रत्येक पुस्तक को उसका पाठक) पर आधारित।
4. **एल. आर. मैककॉल्विन (L. R. McColvin - 1925):** "मांग एवं पूर्ति का सिद्धांत" (Theory of Demand and Supply)।

### 3. परिग्रहण पंजिका (Accession Register):
* पुस्तकालय की स्थायी एवं कानूनी संपत्ति पंजिका।
* **मानक आकार:** 16 x 13 इंच।
* **स्तंभ (Columns):** सामान्यतः 14 या 15 कॉलम होते हैं (दिनांक, परिग्रहण संख्या, लेखक, आख्या, संस्करण, प्रकाशन स्थान व प्रकाशक, वर्ष, पृष्ठ संख्या, आकार, जिल्द, स्रोत/विक्रेता, बिल संख्या व दिनांक, मूल्य, वर्गीकरण संख्या, टिप्पणी)।
    """.trimIndent()

    fun getUnit3Subtopic2Questions(): List<QuestionEntity> = listOf(
        QuestionEntity(
            id = 306L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "'न्यूनतम लागत पर अधिकतम पाठकों के लिए सर्वोत्तम पठन सामग्री' पुस्तक चयन का यह सिद्धांत किसने दिया?",
            optionA = "मेलविल डेवी",
            optionB = "एफ. के. डब्ल्यू. ड्रूरी",
            optionC = "एस. आर. रंगनाथन",
            optionD = "एल. आर. मैककॉल्विन",
            correctOption = 1,
            explanationHindi = "मेलविल डेवी ने 1876 में अमेरिकन लाइब्रेरी एसोसिएशन के आदर्श वाक्य के रूप में यह प्रसिद्ध पुस्तक चयन सिद्धांत प्रतिपादित किया था।",
            keyHighlight = "उप-विषय: पुस्तक चयन एवं अर्जन"
        ),
        QuestionEntity(
            id = 307L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "'The right book to the right reader at the right time' यह सिद्धांत किसके द्वारा दिया गया?",
            optionA = "एफ. के. डब्ल्यू. ड्रूरी (F. K. W. Drury)",
            optionB = "मेलविल डेवी",
            optionC = "मैककॉल्विन",
            optionD = "रंगनाथन",
            correctOption = 1,
            explanationHindi = "फ्रांसिस ड्रूरी ने 1930 में अपनी पुस्तक 'Book Selection' में यह प्रसिद्ध सिद्धांत दिया था।",
            keyHighlight = "उप-विषय: पुस्तक चयन एवं अर्जन"
        ),
        QuestionEntity(
            id = 308L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "पुस्तकालय की मानक परिग्रहण पंजिका (Accession Register) का सामान्य आकार कितना होता है?",
            optionA = "16 x 13 इंच",
            optionB = "12 x 8 इंच",
            optionC = "10 x 6 इंच",
            optionD = "20 x 15 इंच",
            correctOption = 1,
            explanationHindi = "भारतीय पुस्तकालय संघ एवं मानक अनुसार परिग्रहण पंजिका का मानक आकार 16 x 13 इंच (14 से 15 कॉलम युक्त) होता है।",
            keyHighlight = "उप-विषय: पुस्तक चयन एवं अर्जन"
        ),
        QuestionEntity(
            id = 309L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "पुस्तक चयन में 'मांग एवं पूर्ति का सिद्धांत' (Theory of Demand and Supply) 1925 में किसने प्रतिपादित किया?",
            optionA = "एल. आर. मैककॉल्विन (L. R. McColvin)",
            optionB = "ड्रूरी",
            optionC = "डेवी",
            optionD = "रंगनाथन",
            correctOption = 1,
            explanationHindi = "लियोनेल रॉय मैककॉल्विन ने 1925 में अपनी पुस्तक 'The Theory of Book Selection for Public Libraries' में मांग और पूर्ति का सिद्धांत दिया।",
            keyHighlight = "उप-विषय: पुस्तक चयन एवं अर्जन"
        ),
        QuestionEntity(
            id = 310L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "पुस्तकालय में किसी पुस्तक को दी जाने वाली अद्वितीय पहचान संख्या (Unique Serial Number) क्या कहलाती है?",
            optionA = "कॉल नंबर (Call Number)",
            optionB = "परिग्रहण क्रमांक (Accession Number)",
            optionC = "क्लास नंबर (Class Number)",
            optionD = "बुक नंबर (Book Number)",
            correctOption = 2,
            explanationHindi = "परिग्रहण क्रमांक (Accession Number) प्रत्येक भौतिक पुस्तक की विशिष्ट पहचान संख्या होती है जो परिग्रहण पंजिका में दर्ज क्रम से मिलती है।",
            keyHighlight = "उप-विषय: पुस्तक चयन एवं अर्जन"
        )
    )

    fun getUnit3Subtopic3Notes(): String = """
# 📖 उपविषय 03: तकनीकी एवं परिसंचरण अनुभाग (ब्राउन व नेवार्क प्रणाली)
**परीक्षा दृष्टिकोण (BLET 2026 विशेष)**

---
### 1. परिसंचरण अनुभाग (Circulation Section):
* पाठकों को पुस्तकें निर्गमित (Issue) एवं वापस जमा (Return) करने का कार्य।

### 2. प्रमुख चार्जिंग-डिस्चार्जिंग प्रणालियां (Lending Systems):
1. **ब्राउन प्रणाली (Browne Charging System):**
   * **आविष्कारक:** नीना ई. ब्राउन (Nina E. Browne) - 1895 में बोस्टन (USA)।
   * **विशेषता:** रीडर टिकट (जेब के आकार का पाकेट) में बुक कार्ड डाला जाता है। कोई हस्ताक्षर या तिथि मोहर रीडर कार्ड पर नहीं लगाई जाती।
   * अत्यंत तेज एवं सरल व्यवस्था।
2. **नेवार्क प्रणाली (Newark Charging System):**
   * **आविष्कारक:** जॉन कॉटन डाना (John Cotton Dana) - 1900 में नेवार्क पब्लिक लाइब्रेरी (New Jersey, USA)।
   * **विशेषता:** पाठक का कार्ड पाठक के पास रहता है, कार्ड पर तिथि मोहर एवं पाठक संख्या दर्ज की जाती है। अधिक स्थायी रिकॉर्ड।
3. **रंगनाथन की टोकन पद्धति:** भीड़-भाड़ वाले पुस्तकालयों में प्रयुक्त।
4. **बारकोड एवं RFID आधारित स्वचालित प्रणाली:** आधुनिक डिजिटल पुस्तकालयों में बिना मानवीय हस्तक्षेप के सेल्फ-चेकआउट कियोस्क द्वारा पुस्तक निर्गम व वापसी।
    """.trimIndent()

    fun getUnit3Subtopic3Questions(): List<QuestionEntity> = listOf(
        QuestionEntity(
            id = 311L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "ब्राउन चार्जिंग प्रणाली (Browne Charging System) का आविष्कार 1895 में किसके द्वारा किया गया था?",
            optionA = "नीना ई. ब्राउन (Nina E. Browne)",
            optionB = "जे. डी. ब्राउन",
            optionC = "जॉन कॉटन डाना",
            optionD = "मेलविल डेवी",
            correctOption = 1,
            explanationHindi = "नीना ई. ब्राउन ने 1895 में अमेरिकन लाइब्रेरी एसोसिएशन के पब्लिशिंग बोर्ड की लाइब्रेरियन रहते हुए ब्राउन प्रणाली विकसित की थी।",
            keyHighlight = "उप-विषय: परिसंचरण प्रणालियां"
        ),
        QuestionEntity(
            id = 312L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "नेवार्क चार्जिंग सिस्टम (1900) का जनक किसे माना जाता है?",
            optionA = "जॉन कॉटन डाना (John Cotton Dana)",
            optionB = "नीना ई. ब्राउन",
            optionC = "चार्ल्स कटर",
            optionD = "डॉ. रंगनाथन",
            correctOption = 1,
            explanationHindi = "जॉन कॉटन डाना ने 1900 में नेवार्क पब्लिक लाइब्रेरी (यूएसए) में नेवार्क चार्जिंग सिस्टम का आविष्कार किया था।",
            keyHighlight = "उप-विषय: परिसंचरण प्रणालियां"
        ),
        QuestionEntity(
            id = 313L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "ब्राउन प्रणाली में पाठक के टिकट का स्वरूप कैसा होता है?",
            optionA = "एक साधारण पत्रक",
            optionB = "एक पाकेट या जेब (Pocket-shaped ticket)",
            optionC = "एक प्लास्टिक कार्ड",
            optionD = "एक टोकन",
            correctOption = 2,
            explanationHindi = "ब्राउन प्रणाली में पाठक का टिकट जेब के आकार (Pocket) का होता है जिसमें पुस्तक का बुक कार्ड फंसाकर चार्जिंग ट्रे में तिथि अनुसार रखा जाता है।",
            keyHighlight = "उप-विषय: परिसंचरण प्रणालियां"
        ),
        QuestionEntity(
            id = 314L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "पुस्तकालय में विलंब शुल्क (Overdue Charge) वसूलने का प्राथमिक उद्देश्य क्या होता है?",
            optionA = "पुस्तकालय की आय बढ़ाना",
            optionB = "पाठकों को समय पर पुस्तक लौटाने हेतु प्रेरित करना ताकि अन्य पाठक उपयोग कर सकें",
            optionC = "पाठक को दंडित करना",
            optionD = "नई पुस्तकें खरीदना",
            correctOption = 2,
            explanationHindi = "विलंब शुल्क का उद्देश्य राजस्व अर्जन नहीं बल्कि अनुशासन बनाए रखना और पुस्तकों का अधिकतम उपयोग सुनिश्चित करना (प्रथम व द्वितीय सूत्र) है।",
            keyHighlight = "उप-विषय: परिसंचरण प्रणालियां"
        ),
        QuestionEntity(
            id = 315L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "कॉल नंबर (Call Number) किन तीन तत्वों का संयोजन होता है?",
            optionA = "Class Number + Book Number + Collection Number",
            optionB = "Accession Number + Class Number + Price",
            optionC = "Author + Title + Year",
            optionD = "ISBN + ISSN + Barcode",
            correctOption = 1,
            explanationHindi = "डॉ. रंगनाथन के अनुसार Call Number = Class Number (वर्गांक) + Book Number (ग्रंथांक) + Collection Number (संग्रहांक)।",
            keyHighlight = "उप-विषय: परिसंचरण प्रणालियां"
        )
    )

    fun getUnit3Subtopic4Notes(): String = """
# 📖 उपविषय 04: पुस्तकालय बजट निर्माण (ZBB, PPBS एवं वित्तीय प्रबंधन)
**परीक्षा दृष्टिकोण (BLET 2026 विशेष)**

---
### 1. पुस्तकालय बजट (Library Budget):
* आगामी वित्तीय वर्ष के लिए पुस्तकालय के संभावित आय एवं व्यय का वित्तीय खाका।

### 2. बजट निर्माण की प्रमुख पद्धतियां:
1. **शून्य आधारित बजट (Zero-Based Budgeting - ZBB):**
   * **जनक:** पीटर ए. पायर (Peter A. Phyrr) - 1970 में टेक्सास इंस्ट्रूमेंट्स में।
   * **विशेषता:** पिछले वर्ष के बजट को आधार न मानकर शून्य से शुरुआत की जाती है। प्रत्येक व्यय का औचित्य (Justification) सिद्ध करना अनिवार्य होता है।
2. **कार्यक्रम एवं निष्पादन बजट (PPBS - Planning Programming Budgeting System):**
   * यूएस रक्षा विभाग द्वारा 1960 के दशक में विकसित (रैंड कॉर्पोरेशन)।
3. **मद-वार बजट (Line-Item / Incremental Budgeting):**
   * सबसे पुरानी व सरल पद्धति। पिछले वर्ष के बजट में एक निश्चित प्रतिशत जोड़ दिया जाता है।
4. **सूत्र बजट (Formula Budgeting):**
   * यूजीसी या सरकारी सूत्रों (प्रति छात्र या प्रति शिक्षक आवंटन) पर आधारित।

### 3. वित्तीय मानक (Financial Norms):
* **डॉ. रंगनाथन समिति (UGC 1957):** प्रति छात्र ₹15 एवं प्रति शिक्षक ₹200 पुस्तकालय अनुदान।
* **कोठारी आयोग (1964-66):** शैक्षणिक संस्था के कुल बजट का 6.5% से 10% पुस्तकालय पर व्यय किया जाना चाहिए।
    """.trimIndent()

    fun getUnit3Subtopic4Questions(): List<QuestionEntity> = listOf(
        QuestionEntity(
            id = 316L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "शून्य आधारित बजट (Zero-Based Budgeting - ZBB) का जनक किसे माना जाता है?",
            optionA = "पीटर ए. पायर (Peter A. Phyrr)",
            optionB = "लूथर गुलिक",
            optionC = "रंगनाथन",
            optionD = "हेनरी फेयोल",
            correctOption = 1,
            explanationHindi = "पीटर पायर ने 1970 में टेक्सास इंस्ट्रूमेंट्स कंपनी में ZBB का आविष्कार किया, जिसे बाद में अमेरिकी राष्ट्रपति जिमी कार्टर ने सरकारी क्षेत्र में लागू किया।",
            keyHighlight = "उप-विषय: पुस्तकालय बजट निर्माण"
        ),
        QuestionEntity(
            id = 317L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "कोठारी शिक्षा आयोग (1964-66) ने विश्वविद्यालय/कॉलेज के कुल बजट का कितना प्रतिशत पुस्तकालय हेतु अनुशंसित किया?",
            optionA = "1% से 2%",
            optionB = "3% से 5%",
            optionC = "6.5% से 10%",
            optionD = "15% से 20%",
            correctOption = 3,
            explanationHindi = "डॉ. डी. एस. कोठारी की अध्यक्षता वाले आयोग ने विश्वविद्यालय के कुल बजट का 6.5% से 10% तक पुस्तकालय विकास हेतु आवंटित करने की सिफारिश की थी।",
            keyHighlight = "उप-विषय: पुस्तकालय बजट निर्माण"
        ),
        QuestionEntity(
            id = 318L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "PPBS (Planning Programming Budgeting System) किस प्रकार की बजट पद्धति है?",
            optionA = "ऐतिहासिक बजट",
            optionB = "नियोजन एवं कार्यक्रम निष्पादन आधारित बजट",
            optionC = "घाटे का बजट",
            optionD = "मद-वार बजट",
            correctOption = 2,
            explanationHindi = "PPBS दीर्घकालिक लक्ष्यों, कार्यक्रमों एवं परिणामों का मूल्यांकन करके बजट आवंटन करने वाली आधुनिक वैज्ञानिक प्रणाली है।",
            keyHighlight = "उप-विषय: पुस्तकालय बजट निर्माण"
        ),
        QuestionEntity(
            id = 319L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "सार्वजनिक पुस्तकालयों की आय का सबसे प्रमुख कानूनी साधन क्या है?",
            optionA = "पुस्तकालय उपकर (Library Cess)",
            optionB = "निजी दान",
            optionC = "पुस्तकों की बिक्री",
            optionD = "जुर्माना",
            correctOption = 1,
            explanationHindi = "सार्वजनिक पुस्तकालय अधिनियमों के तहत संपत्ति कर या भू-राजस्व पर लगाया जाने वाला 'पुस्तकालय उपकर' (Cess) आय का प्रमुख साधन होता है।",
            keyHighlight = "उप-विषय: पुस्तकालय बजट निर्माण"
        ),
        QuestionEntity(
            id = 320L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "ZBB बजट पद्धति की सबसे प्रमुख विशेषता क्या है?",
            optionA = "इसमें पिछले वर्ष के खर्चों को सीधा 10% बढ़ा दिया जाता है",
            optionB = "प्रत्येक नए वित्तीय वर्ष में हर मद के खर्च का नए सिरे से औचित्य सिद्ध करना होता है",
            optionC = "इसमें बजट शून्य हो जाता है",
            optionD = "यह केवल पुस्तकों के लिए होता है",
            correctOption = 2,
            explanationHindi = "ZBB में पिछला इतिहास शून्य माना जाता है और प्रत्येक गतिविधि एवं व्यय के लिए स्क्रैच से तार्किक औचित्य प्रस्तुत करना अनिवार्य होता है।",
            keyHighlight = "उप-विषय: पुस्तकालय बजट निर्माण"
        )
    )

    fun getUnit3Subtopic5Notes(): String = """
# 📖 उपविषय 05: भंडार सत्यापन, वीडिंग आउट एवं संरक्षण
**परीक्षा दृष्टिकोण (BLET 2026 विशेष)**

---
### 1. भंडार सत्यापन (Stock Verification):
* पुस्तकालय में उपलब्ध प्रलेखों की वास्तविक भौतिक उपस्थिति का आधिकारिक अभिलेखों से मिलान करना।
* **पद्धतियां:**
  1. शेल्फ सूची (Shelf List) पद्धति - सबसे तीव्र एवं वैज्ञानिक पद्धति।
  2. परिग्रहण पंजिका पद्धति - समय साध्य (पंजिका खराब होने का डर)।
  3. संख्यात्मक पत्रक (Numerical Slip) पद्धति।
  4. बारकोड / आरएफआईडी स्कैनर द्वारा स्वतः सत्यापन - आधुनिकतम।

### 2. सामान्य वित्तीय नियम (GFR 2017) के अनुसार छूट:
* प्रति 1000 निर्गमित या परामर्श की गई पुस्तकों पर 5 पुस्तकों की सामान्य हानि/क्षति स्वीकार्य है और इसे लापरवाही नहीं माना जाता।

### 3. वीडिंग आउट (Weeding Out):
* अप्रचलित, जीर्ण-शीर्ण एवं अनुपयोगी पुस्तकों को संग्रह से हटाना (रंगनाथन के पंचम सूत्र - 'पुस्तकालय एक वर्धनशील संस्था है' के तहत अनिवार्य)।

### 4. प्रलेख संरक्षण (Preservation):
* जैविक कारक (कीट, दीमक, फफूंद), भौतिक कारक (धूप, नमी, तापमान), रासायनिक कारक (अम्लीय कागज)।
* डी-एसिडिफिकेशन (Deacidification), लैमिनेशन (बैरोज लैमिनेशन पद्धति)।
    """.trimIndent()

    fun getUnit3Subtopic5Questions(): List<QuestionEntity> = listOf(
        QuestionEntity(
            id = 321L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "पुस्तकालय में भंडार सत्यापन (Stock Verification) की सबसे श्रेष्ठ एवं वैज्ञानिक पद्धति कौन सी है?",
            optionA = "शेल्फ सूची पद्धति (Shelf List Method)",
            optionB = "परिग्रहण पंजिका पद्धति",
            optionC = "वार्षिक डायरी पद्धति",
            optionD = "अंधाधुंध गिनती",
            correctOption = 1,
            explanationHindi = "शेल्फ लिस्ट कार्ड्स का क्रम ठीक शेल्फ पर रखी पुस्तकों के समान होता है, अतः शेल्फ लिस्ट पद्धति सबसे तेज और त्रुटिरहित होती है।",
            keyHighlight = "उप-विषय: भंडार सत्यापन एवं संरक्षण"
        ),
        QuestionEntity(
            id = 322L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "भारत सरकार के GFR (General Financial Rules) के अनुसार प्रति 1000 निर्गमित पुस्तकों पर कितनी पुस्तकों की हानि सामान्य स्वीकार्य है?",
            optionA = "1 पुस्तक",
            optionB = "5 पुस्तकें",
            optionC = "10 पुस्तकें",
            optionD = "50 पुस्तकें",
            correctOption = 2,
            explanationHindi = "GFR 2017 नियम 215 के तहत 1000 प्रयुक्त/निर्गमित पुस्तकों पर 5 पुस्तकों का नुकसान सामान्य माना जाता है जिसे बट्टे खाते (Write-off) डाला जा सकता है।",
            keyHighlight = "उप-विषय: भंडार सत्यापन एवं संरक्षण"
        ),
        QuestionEntity(
            id = 323L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "जीर्ण-शीर्ण, अनुपयोगी एवं पाठ्यक्रम से बाहर हो चुकी पुस्तकों को संग्रह से अलग करना क्या कहलाता है?",
            optionA = "बाइंडिंग",
            optionB = "वीडिंग आउट (Weeding Out / प्रलेख छंटाई)",
            optionC = "वर्गीकरण",
            optionD = "परिग्रहण",
            correctOption = 2,
            explanationHindi = "पुस्तकालय में नए प्रलेखों के स्थान निर्माण एवं उपयोगी संग्रह बनाए रखने हेतु अनुपयोगी सामग्री को हटाना 'वीडिंग आउट' कहलाता है।",
            keyHighlight = "उप-विषय: भंडार सत्यापन एवं संरक्षण"
        ),
        QuestionEntity(
            id = 324L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "पुस्तकालय में पुस्तकों के संरक्षण हेतु बैरोज लैमिनेशन (Barrow's Lamination Method) में किन रसायनों का प्रयोग होता है?",
            optionA = "सेल्युलोज एसीटेट एवं टिश्यू पेपर",
            optionB = "पॉलीथीन व गोंद",
            optionC = "मोम व वार्निश",
            optionD = "सल्फर व फॉस्फोरस",
            correctOption = 1,
            explanationHindi = "विलियम जे. बैरो ने 1930 के दशक में दुर्लभ दस्तावेजों को संरक्षित करने के लिए सेल्युलोज एसीटेट फिल्म और जापानी टिश्यू पेपर लैमिनेशन विकसित किया था।",
            keyHighlight = "उप-विषय: भंडार सत्यापन एवं संरक्षण"
        ),
        QuestionEntity(
            id = 325L,
            category = DefaultQuestions.UNIT_3,
            questionHindi = "पुस्तकालय की अलमारियों पर पुस्तकों के दीमक व कीटों से बचाव हेतु सामान्यतः क्या रखा जाता है?",
            optionA = "नेफ़थलीन की गोलियां (Naphthalene Balls)",
            optionB = "गीला कपड़ा",
            optionC = "लकड़ी का बुरादा",
            optionD = "नमक",
            correctOption = 1,
            explanationHindi = "नेफ़थलीन बॉल्स कीट-रोधी (Insect repellent) होती हैं जो सिल्वरफिश और कीड़ों से पुस्तकों की सुरक्षा करती हैं।",
            keyHighlight = "उप-विषय: भंडार सत्यापन एवं संरक्षण"
        )
    )

    // =========================================================================
    // UNIT 4: सूचना स्रोत एवं सूचना सेवाएं (Information Sources & Services)
    // =========================================================================

    fun getUnit4Subtopic1Notes(): String = """
# 📖 उपविषय 01: सूचना स्रोत: प्राथमिक, द्वितीयक एवं तृतीयक स्रोत
**परीक्षा दृष्टिकोण (BLET 2026 विशेष)**

---
### 1. सूचना स्रोतों का वर्गीकरण:
* **सी. डब्ल्यू. हैनसन (C. W. Hanson - 1971):** दो वर्गों में विभाजित किया -
  1. प्राथमिक स्रोत (Primary Sources)
  2. द्वितीयक स्रोत (Secondary Sources)
* **डेनिस ग्रोगन (Denis Grogan - 1982):** तीन वर्गों में विभाजित किया -
  1. प्राथमिक स्रोत (Primary Sources)
  2. द्वितीयक स्रोत (Secondary Sources)
  3. तृतीयक स्रोत (Tertiary Sources)

### 2. स्रोतों का विस्तृत विवरण:
1. **प्राथमिक स्रोत (Primary Sources):**
   * मूल शोध एवं प्रथम बार प्रकाशित जानकारी।
   * **उदाहरण:** शोध पत्रिकाएं (Periodicals/Journals), शोध प्रबंध (Theses/Dissertations), एकस्व/पेटेंट (Patents), मानक (Standards), सम्मेलन की कार्यवाहियां (Conference Proceedings), शोध प्रतिवेदन (Research Reports)।
2. **द्वितीयक स्रोत (Secondary Sources):**
   * प्राथमिक स्रोतों को संकलित एवं विश्लेषित कर तैयार की गई सामग्री।
   * **उदाहरण:** पाठ्यपुस्तकें (Textbooks), विश्वकोश (Encyclopedias), शब्दकोश (Dictionaries), अनुक्रमणिका पत्रिकाएं (Indexing Periodicals), सार पत्रिकाएं (Abstracting Periodicals), संदर्भ ग्रंथ।
3. **तृतीयक स्रोत (Tertiary Sources):**
   * प्राथमिक एवं द्वितीयक स्रोतों तक पहुँचने का मार्ग दिखाने वाली मार्गदर्शिकाएं।
   * **उदाहरण:** ग्रंथसूचियों की ग्रंथसूची (Bibliography of Bibliographies), निर्देशिकाएं (Directories), गाइड टू रेफरेंस बुक्स (जैसे विंचेल/शीही गाइड)।
    """.trimIndent()

    fun getUnit4Subtopic1Questions(): List<QuestionEntity> = listOf(
        QuestionEntity(
            id = 401L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "सूचना स्रोतों को 'प्राथमिक, द्वितीयक एवं तृतीयक' तीन श्रेणियों में किसने वर्गीकृत किया था?",
            optionA = "डेनिस ग्रोगन (Denis Grogan)",
            optionB = "सी. डब्ल्यू. हैनसन",
            optionC = "एस. आर. रंगनाथन",
            optionD = "मेलविल डेवी",
            correctOption = 1,
            explanationHindi = "डेनिस ग्रोगन ने अपनी पुस्तक 'Science and Technology: An Introduction to the Literature' में स्रोतों को तीन स्तरों (Primary, Secondary, Tertiary) में बांटा था।",
            keyHighlight = "उप-विषय: सूचना स्रोतों का वर्गीकरण"
        ),
        QuestionEntity(
            id = 402L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "निम्नलिखित में से कौन सा एक 'प्राथमिक सूचना स्रोत' (Primary Source) का उदाहरण है?",
            optionA = "विश्वकोश (Encyclopedia)",
            optionB = "शोध प्रबंध (Thesis / Dissertation)",
            optionC = "पाठ्यपुस्तक (Textbook)",
            optionD = "निर्देशिका (Directory)",
            correctOption = 2,
            explanationHindi = "शोध प्रबंध (Thesis) शोधार्थी द्वारा किया गया मौलिक कार्य होता है, जो प्राथमिक स्रोत है; जबकि विश्वकोश व पाठ्यपुस्तक द्वितीयक स्रोत हैं।",
            keyHighlight = "उप-विषय: सूचना स्रोतों का वर्गीकरण"
        ),
        QuestionEntity(
            id = 403L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "'ग्रंथसूचियों की ग्रंथसूची' (Bibliography of Bibliographies) किस श्रेणी का सूचना स्रोत है?",
            optionA = "प्राथमिक स्रोत",
            optionB = "द्वितीयक स्रोत",
            optionC = "तृतीयक स्रोत (Tertiary Source)",
            optionD = "मौखिक स्रोत",
            correctOption = 3,
            explanationHindi = "ग्रंथसूचियों की ग्रंथसूची द्वितीयक स्रोतों की ओर मार्गदर्शन करती है, अतः यह तृतीयक (Tertiary) सूचना स्रोत है।",
            keyHighlight = "उप-विषय: सूचना स्रोतों का वर्गीकरण"
        ),
        QuestionEntity(
            id = 404L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "सी. डब्ल्यू. हैनसन (C. W. Hanson) ने 1971 में सूचना स्रोतों को कितने भागों में विभाजित किया था?",
            optionA = "केवल दो (प्राथमिक एवं द्वितीयक)",
            optionB = "तीन",
            optionC = "चार",
            optionD = "पाँच",
            correctOption = 1,
            explanationHindi = "हैनसन ने केवल दो श्रेणियों में बांटा था: प्राथमिक (Primary) और द्वितीयक (Secondary)।",
            keyHighlight = "उप-विषय: सूचना स्रोतों का वर्गीकरण"
        ),
        QuestionEntity(
            id = 405L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "एकस्व (Patent) किस प्रकार की सूचना प्रदान करता है?",
            optionA = "नवीन तकनीकी आविष्कार एवं बौद्धिक संपदा अधिकार",
            optionB = "ऐतिहासिक आंकड़े",
            optionC = "भाषा के व्याकरण नियम",
            optionD = "भौगोलिक मानचित्र",
            correctOption = 1,
            explanationHindi = "पेटेंट सरकार द्वारा किसी आविष्कारक को उसके नए तकनीकी आविष्कार के उपयोग का विशिष्ट अधिकार एवं विवरण प्रदान करने वाला प्राथमिक प्रलेख है।",
            keyHighlight = "उप-विषय: सूचना स्रोतों का वर्गीकरण"
        )
    )

    fun getUnit4Subtopic2Notes(): String = """
# 📖 उपविषय 02: संदर्भ सेवा: तैयार संदर्भ एवं दीर्घकालीन संदर्भ सेवा
**परीक्षा दृष्टिकोण (BLET 2026 विशेष)**

---
### 1. संदर्भ सेवा (Reference Service):
* **डॉ. रंगनाथन के अनुसार परिभाषा:** "पाठक और उसके प्रलेख के बीच व्यक्तिगत संपर्क स्थापित करना ही संदर्भ सेवा है।"
* यह पुस्तकालय की व्यक्तिगत सहायता (Personal Assistance) सेवा है।

### 2. संदर्भ सेवा के प्रकार (रंगनाथन के अनुसार):
1. **तैयार संदर्भ सेवा (Ready Reference Service):**
   * तथ्यपरक प्रश्नों का उत्तर तुरंत या बहुत कम समय (कुछ सेकंड से लेकर आधे घंटे के भीतर) में देना।
   * **साधन:** शब्दकोश, विश्वकोश, वर्षिकी (Yearbook), पंचांग (Almanac), निर्देशिकाएं आदि।
   * उदा: "भारत का राष्ट्रपति कौन है?", "पटना किस नदी के तट पर है?"
2. **दीर्घकालीन संदर्भ सेवा (Long Range Reference Service):**
   * जटिल, गहन अनुसंधान एवं विषयगत प्रश्नों के लिए प्रदान की जाने वाली सेवा जिसमें घंटों, दिनों या हफ्तों का समय लग सकता है।
   * **साधन:** शोध पत्रिकाएं, ग्रंथसूचियां, रिपोर्ट, अन्य पुस्तकालयों से अंतर-पुस्तकालय ऋण।
   * उदा: "पुस्तकालय स्वचालन पर 2020-2025 के मध्य हुए शोधों का साहित्य संकलन।"

### 3. जेम्स आई. वायर (James I. Wyer) के सिद्धांत (1930):
* अनुदार सिद्धांत (Conservative Theory - न्यूनतम सहायता)
* उदार सिद्धांत (Liberal Theory - अधिकतम सहायता)
* मध्यम सिद्धांत (Moderate Theory - संतुलित सहायता)
    """.trimIndent()

    fun getUnit4Subtopic2Questions(): List<QuestionEntity> = listOf(
        QuestionEntity(
            id = 406L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "'पाठक और उसके प्रलेख के बीच व्यक्तिगत संपर्क स्थापित करना ही संदर्भ सेवा है' यह कथन किसका है?",
            optionA = "डॉ. एस. आर. रंगनाथन",
            optionB = "मेलविल डेवी",
            optionC = "सैमुअल रोथस्टीन",
            optionD = "जेम्स आई. वायर",
            correctOption = 1,
            explanationHindi = "डॉ. रंगनाथन ने संदर्भ सेवा को पुस्तकालय का हृदय बताते हुए पाठक और प्रलेख के मध्य व्यक्तिगत सेतु के रूप में परिभाषित किया था।",
            keyHighlight = "उप-विषय: संदर्भ सेवा"
        ),
        QuestionEntity(
            id = 407L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "तथ्यपरक प्रश्नों का उत्तर शब्दकोश या वर्षिकी देखकर तुरंत देना किस प्रकार की संदर्भ सेवा है?",
            optionA = "दीर्घकालीन संदर्भ सेवा",
            optionB = "तैयार संदर्भ सेवा (Ready Reference Service)",
            optionC = "एसडीआई सेवा",
            optionD = "अनुवाद सेवा",
            correctOption = 2,
            explanationHindi = "कम समय में संदर्भ ग्रंथों के माध्यम से सीधे तथ्यात्मक प्रश्नों का उत्तर देना 'तैयार संदर्भ सेवा' कहलाता है।",
            keyHighlight = "उप-विषय: संदर्भ सेवा"
        ),
        QuestionEntity(
            id = 408L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "जेम्स आई. वायर (James I. Wyer) ने 1930 में संदर्भ सेवा के कितने सिद्धांतों का प्रतिपादन किया था?",
            optionA = "तीन (Conservative, Moderate, Liberal)",
            optionB = "चार",
            optionC = "पाँच",
            optionD = "दो",
            correctOption = 1,
            explanationHindi = "वायर ने संदर्भ सेवा के तीन दृष्टिकोण बताए: अनुदार (न्यूनतम), उदार (अधिकतम) एवं मध्यमवादी सिद्धांत।",
            keyHighlight = "उप-विषय: संदर्भ सेवा"
        ),
        QuestionEntity(
            id = 409L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "विश्व में संदर्भ सेवा की औपचारिक शुरुआत का श्रेय 1876 में किसे जाता है?",
            optionA = "सैमुअल स्वीट ग्रीन (Samuel Swett Green)",
            optionB = "मेलविल डेवी",
            optionC = "सी. ए. कटर",
            optionD = "डॉ. रंगनाथन",
            correctOption = 1,
            explanationHindi = "सैमुअल ग्रीन ने 1876 में Worcester Public Library में ALA सम्मेलन में 'Personal Relations Between Librarians and Readers' पेपर प्रस्तुत कर संदर्भ सेवा की नींव रखी।",
            keyHighlight = "उप-विषय: संदर्भ सेवा"
        ),
        QuestionEntity(
            id = 410L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "दीर्घकालीन संदर्भ सेवा (Long Range Reference Service) मुख्यतः किन पाठकों के लिए उपयोगी होती है?",
            optionA = "सामान्य आगंतुक",
            optionB = "शोधार्थी, वैज्ञानिक एवं विशेषज्ञ (Researchers & Scholars)",
            optionC = "प्राथमिक विद्यालय के बच्चे",
            optionD = "केवल अखबार पढ़ने वाले पाठक",
            correctOption = 2,
            explanationHindi = "गहन अनुसंधान, साहित्य खोज और विशेष तकनीकी प्रश्नों के समाधान हेतु शोधकर्ताओं को दीर्घकालीन सेवा प्रदान की जाती है।",
            keyHighlight = "उप-विषय: संदर्भ सेवा"
        )
    )

    fun getUnit4Subtopic3Notes(): String = """
# 📖 उपविषय 03: सामयिक चेतना सेवा (CAS) एवं चयनित सूचना प्रसार (SDI)
**परीक्षा दृष्टिकोण (BLET 2026 विशेष)**

---
### 1. सामयिक चेतना सेवा (Current Awareness Service - CAS):
* **उद्देश्य:** उपयोगकर्ताओं को उनके विषय क्षेत्र में हो रहे नवीनतम विकास, नवीन शोध पत्रों एवं प्रलेखों से निरंतर अवगत कराना।
* **स्वरूप:** सामूहिक सेवा (Group-oriented/General service)।
* **माध्यम:**
  * नवीनतम आगमन सूची (List of Recent Additions)
  * विषयवस्तु पृष्ठ बुलेटिन (Contents by Journal / Current Contents)
  * समाचार पत्र कतरन सेवा (Newspaper Clipping Service)।

### 2. चयनित सूचना प्रसार (Selective Dissemination of Information - SDI):
* **जनक:** हैंस पीटर लुहन (H. P. Luhn) - 1958 में IBM में विकसित।
* **स्वरूप:** व्यक्तिगत सेवा (Personalized/User-specific Alerting Service)।
* **SDI के 6 प्रमुख घटक/चरण (Components/Steps):**
  1. उपयोगकर्ता प्रोफाइल (User Profile): पाठक की शोध रुचि के कीवर्ड्स का समूह।
  2. प्रलेख प्रोफाइल (Document Profile): नवीन प्राप्त प्रलेखों के कीवर्ड्स का समूह।
  3. मिलान तंत्र (Matching Mechanism): कंप्यूटर द्वारा दोनों प्रोफाइल्स का मिलान।
  4. अधिसूचना (Notification): मिलान होने पर उपयोगकर्ता को सूचना भेजना।
  5. प्रतिपुष्टि (Feedback Loop): उपयोगकर्ता से प्रतिक्रिया प्राप्त करना।
  6. प्रोफाइल संशोधन (Profile Modification): प्रतिक्रिया के आधार पर प्रोफाइल को अपडेट करना।
    """.trimIndent()

    fun getUnit4Subtopic3Questions(): List<QuestionEntity> = listOf(
        QuestionEntity(
            id = 411L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "चयनित सूचना प्रसार (SDI) प्रणाली का आविष्कार 1958 में किसके द्वारा किया गया था?",
            optionA = "एच. पी. लुहन (H. P. Luhn)",
            optionB = "एस. आर. रंगनाथन",
            optionC = "डेनिस ग्रोगन",
            optionD = "डेरेक ऑस्टिन",
            correctOption = 1,
            explanationHindi = "आईबीएम (IBM) के वैज्ञानिक हैंस पीटर लुहन (H. P. Luhn) ने 1958 में कंप्यूटर आधारित SDI प्रणाली की अवधारणा प्रस्तुत की थी।",
            keyHighlight = "उप-विषय: CAS एवं SDI सेवाएं"
        ),
        QuestionEntity(
            id = 412L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "CAS और SDI में सबसे मूलभूत अंतर क्या है?",
            optionA = "CAS सामान्य/सामूहिक सेवा है, जबकि SDI व्यक्तिगत/विशिष्ट उपयोगकर्ता केंद्रित सेवा है",
            optionB = "CAS महंगी होती है, SDI मुफ्त होती है",
            optionC = "CAS पुरानी सेवा है, SDI बंद हो चुकी है",
            optionD = "दोनों में कोई अंतर नहीं है",
            correctOption = 1,
            explanationHindi = "CAS पूरे समुदाय के लिए नवीनतम सामग्री की सामान्य सूचना है, जबकि SDI किसी विशिष्ट शोधार्थी की व्यक्तिगत रुचि (User Profile) के अनुसार फ़िल्टर की गई सूचना है।",
            keyHighlight = "उप-विषय: CAS एवं SDI सेवाएं"
        ),
        QuestionEntity(
            id = 413L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "SDI प्रणाली में 'Feedback' (प्रतिपुष्टि) का मुख्य कार्य क्या होता है?",
            optionA = "लाइब्रेरियन का वेतन निर्धारित करना",
            optionB = "यह जानना कि भेजी गई सूचना प्रासंगिक थी या नहीं, तथा आवश्यकता पड़ने पर यूजर प्रोफाइल में सुधार करना",
            optionC = "पुस्तकालय की सदस्यता समाप्त करना",
            optionD = "पुस्तकों का मूल्य आंकना",
            correctOption = 2,
            explanationHindi = "फीडबैक लूप SDI का सर्वाधिक महत्वपूर्ण अंग है जो सिस्टम को गतिशील बनाए रखता है और प्रोफाइल को और सटीक बनाता है।",
            keyHighlight = "उप-विषय: CAS एवं SDI सेवाएं"
        ),
        QuestionEntity(
            id = 414L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "समाचार पत्रों की कतरन सेवा (Newspaper Clipping Service) किस प्रकार की सेवा का उदाहरण है?",
            optionA = "सामयिक चेतना सेवा (CAS)",
            optionB = "दीर्घकालीन संदर्भ सेवा",
            optionC = "अंतर-पुस्तकालय ऋण",
            optionD = "डेटा माइग्रेशन",
            correctOption = 1,
            explanationHindi = "अखबारों से समसामयिक समाचारों को काटकर संबंधित विभाग या शोधार्थियों को भेजना CAS का लोकप्रिय रूप है।",
            keyHighlight = "उप-विषय: CAS एवं SDI सेवाएं"
        ),
        QuestionEntity(
            id = 415L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "SDI में प्रलेखों के विवरण को कंप्यूटर में किस रूप में तैयार किया जाता है?",
            optionA = "User Profile",
            optionB = "Document Profile",
            optionC = "Author Catalogue",
            optionD = "Subject Index",
            correctOption = 2,
            explanationHindi = "आने वाले नए शोध पत्रों एवं पुस्तकों के प्रमुख कीवर्ड्स और अमूर्त को 'Document Profile' कहा जाता है।",
            keyHighlight = "उप-विषय: CAS एवं SDI सेवाएं"
        )
    )

    fun getUnit4Subtopic4Notes(): String = """
# 📖 उपविषय 04: अनुक्रमण एवं सारकरण सेवाएं (KWIC, PRECIS, POPSI)
**परीक्षा दृष्टिकोण (BLET 2026 विशेष)**

---
### 1. विषय अनुक्रमण पद्धतियां (Subject Indexing Systems):
1. **श्रृंखला अनुक्रमण (Chain Indexing):**
   * **प्रणेता:** डॉ. एस. आर. रंगनाथन (1938 - Theory of Library Catalogue)।
   * वर्गीकरण अंक से विषय शीर्षक व्युत्पन्न करने की अर्द्ध-यांत्रिक विधि।
2. **KWIC (Key Word In Context):**
   * **प्रणेता:** एच. पी. लुहन (1958 - IBM)।
   * प्राकृतिक भाषा शीर्षक आधारित कंप्यूटर अनुक्रमण। शीर्षक के मुख्य शब्दों को केंद्र में रखकर अनुक्रमित किया जाता है।
   * इसके अन्य रूप: KWOC (Key Word Out of Context), KWAC (Key Word Augmented in Context)।
3. **PRECIS (Preserved Context Indexing System):**
   * **प्रणेता:** डेरेक ऑस्टिन (Derek Austin) - 1974 में ब्रिटिश नेशनल बिब्लियोग्राफी (BNB) हेतु विकसित।
   * इसमें संदर्भ को संरक्षित रखने हेतु 'भूमिका संसूचक' (Role Operators 0 से 9) का प्रयोग होता है।
4. **POPSI (Postulate-based Permuted Subject Indexing):**
   * **प्रणेता:** प्रो. गणेश भट्टाचार्य (Ganesh Bhattacharyya) - 1979 में DRTC बैंगलोर में विकसित।
   * रंगनाथन के PMEST के सामान्य सिद्धांतों और उपसूत्रों पर आधारित।
    """.trimIndent()

    fun getUnit4Subtopic4Questions(): List<QuestionEntity> = listOf(
        QuestionEntity(
            id = 416L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "PRECIS (Preserved Context Indexing System) का विकास 1974 में किसके द्वारा किया गया?",
            optionA = "डेरेक ऑस्टिन (Derek Austin)",
            optionB = "एच. पी. लुहन",
            optionC = "गणेश भट्टाचार्य",
            optionD = "एस. आर. रंगनाथन",
            correctOption = 1,
            explanationHindi = "डेरेक ऑस्टिन ने ब्रिटिश नेशनल बिब्लियोग्राफी (BNB) में विषय अनुक्रमण हेतु PRECIS का विकास किया था।",
            keyHighlight = "उप-विषय: अनुक्रमण पद्धतियां"
        ),
        QuestionEntity(
            id = 417L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "KWIC (Key Word In Context) अनुक्रमण पद्धति का आविष्कार किसने किया?",
            optionA = "एच. पी. लुहन (H. P. Luhn)",
            optionB = "डेरेक ऑस्टिन",
            optionC = "रंगनाथन",
            optionD = "यूजीन गारफील्ड",
            correctOption = 1,
            explanationHindi = "एच. पी. लुहन ने 1958 में आख्या के मुख्य शब्दों के संदर्भ सहित अनुक्रमण हेतु KWIC का आविष्कार किया था।",
            keyHighlight = "उप-विषय: अनुक्रमण पद्धतियां"
        ),
        QuestionEntity(
            id = 418L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "POPSI (Postulate-based Permuted Subject Indexing) का विकास DRTC में किसके द्वारा किया गया?",
            optionA = "गणेश भट्टाचार्य (G. Bhattacharyya)",
            optionB = "ए. नीलगमेघन",
            optionC = "पी. एन. कौला",
            optionD = "बी. एस. केसवन",
            correctOption = 1,
            explanationHindi = "गणेश भट्टाचार्य ने 1979 में DRTC (Documentation Research and Training Centre, Bangalore) में POPSI का विकास किया।",
            keyHighlight = "उप-विषय: अनुक्रमण पद्धतियां"
        ),
        QuestionEntity(
            id = 419L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "साइंस साइटेशन इंडेक्स (SCI - Science Citation Index) 1964 में किसके द्वारा शुरू किया गया?",
            optionA = "यूजीन गारफील्ड (Eugene Garfield - ISI)",
            optionB = "एच. पी. लुहन",
            optionC = "वन्नेवर बुश",
            optionD = "मेलविल डेवी",
            correctOption = 1,
            explanationHindi = "डॉ. यूजीन गारफील्ड ने Institute for Scientific Information (ISI, Philadelphia) के माध्यम से 1964 में SCI उद्धरण अनुक्रमण की शुरुआत की।",
            keyHighlight = "उप-विषय: अनुक्रमण पद्धतियां"
        ),
        QuestionEntity(
            id = 420L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "श्रृंखला अनुक्रमण (Chain Indexing) में 'मिथ्या कड़ी' (False Link) का क्या अर्थ होता है?",
            optionA = "ऐसी कड़ी जिसका कोई विषय सूचक नाम नहीं होता या जो योजक चिह्न का प्रतिनिधित्व करती है",
            optionB = "गलत लिंक",
            optionC = "अंतिम लिंक",
            optionD = "लेखक का नाम",
            correctOption = 1,
            explanationHindi = "श्रृंखला प्रक्रिया में योजक चिह्नों (Connecting symbols) या गैर-अर्थपूर्ण प्रतीकों से बनी कड़ी को 'False Link' कहा जाता है जिससे शीर्षक नहीं बनता।",
            keyHighlight = "उप-विषय: अनुक्रमण पद्धतियां"
        )
    )

    fun getUnit4Subtopic5Notes(): String = """
# 📖 उपविषय 05: राष्ट्रीय एवं अंतरराष्ट्रीय सूचना प्रणालियां व नेटवर्क
**परीक्षा दृष्टिकोण (BLET 2026 विशेष)**

---
### 1. भारत के प्रमुख पुस्तकालय नेटवर्क:
* **INFLIBNET (Information and Library Network):**
  * **स्थापना:** मार्च 1991 (यूजीसी द्वारा), 1996 में स्वायत्त अंतर-विश्वविद्यालय केंद्र।
  * **मुख्यालय:** इन्फोसिटी, गांधीनगर (गुजरात)।
  * **प्रमुख परियोजनाएं:** शोधगंगा (Shodhganga - भारतीय शोध प्रबंधों का डिजिटल रिपॉजिटरी), शोधगंगोत्री (Shodhgangotri - शोध प्रस्ताव), ई-शोधसिंधु (e-ShodhSindhu - कंसोर्टियम), शोधचक्र, विद्यामित्र, SOUL सॉफ्टवेयर।
* **DELNET (Developing Library Network):**
  * **स्थापना:** 1988 (दिल्ली में NISSAT के समर्थन से), वर्तमान में दक्षिण एशिया का सबसे बड़ा रिसोर्स शेयरिंग नेटवर्क।
  * **मुख्यालय:** जेएनयू परिसर, नई दिल्ली।
  * **प्रमुख कार्य:** यूनियन कैटलॉग, अंतर-पुस्तकालय ऋण (ILL) एवं प्रलेख प्रदाय सेवा (DDS)।

### 2. अंतरराष्ट्रीय सूचना प्रणालियां:
* **INIS (International Nuclear Information System):** 1970 में विएना (ऑस्ट्रिया) में IAEA द्वारा।
* **AGRIS (International Information System for the Agricultural Sciences and Technology):** 1974 में रोम में FAO द्वारा।
* **MEDLARS / PubMed:** 1964 में नेशनल लाइब्रेरी ऑफ मेडिसिन (NLM, USA) द्वारा।
* **UNESCO:** UNISIST (1971) एवं PGI (General Information Programme - 1976)।
    """.trimIndent()

    fun getUnit4Subtopic5Questions(): List<QuestionEntity> = listOf(
        QuestionEntity(
            id = 421L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "INFLIBNET (Information and Library Network Centre) का मुख्यालय कहाँ स्थित है?",
            optionA = "गांधीनगर (गुजरात)",
            optionB = "नई दिल्ली",
            optionC = "कोलकाता",
            optionD = "बेंगलुरु",
            correctOption = 1,
            explanationHindi = "INFLIBNET केंद्र का स्थायी मुख्यालय इन्फोसिटी, गांधीनगर (गुजरात) में स्थित है।",
            keyHighlight = "उप-विषय: सूचना प्रणालियां व नेटवर्क"
        ),
        QuestionEntity(
            id = 422L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "भारतीय इलेक्ट्रॉनिक शोध प्रबंधों (ETDs) का राष्ट्रीय डिजिटल रिपॉजिटरी कौन सा है?",
            optionA = "शोधगंगा (Shodhganga)",
            optionB = "शोधगंगोत्री",
            optionC = "एनडीएलआई",
            optionD = "ई-पीजी पाठशाला",
            correctOption = 1,
            explanationHindi = "शोधगंगा (Shodhganga) INFLIBNET द्वारा संचालित भारत का राष्ट्रीय ओपन एक्सेस शोध प्रबंध रिपॉजिटरी है।",
            keyHighlight = "उप-विषय: सूचना प्रणालियां व नेटवर्क"
        ),
        QuestionEntity(
            id = 423L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "DELNET (Developing Library Network) की स्थापना किस वर्ष हुई थी?",
            optionA = "1988",
            optionB = "1991",
            optionC = "1995",
            optionD = "2000",
            correctOption = 1,
            explanationHindi = "DELNET की शुरुआत जनवरी 1988 में NISSAT के सहयोग से दिल्ली लाइब्रेरी नेटवर्क के रूप में हुई थी (बाद में डेवलपिंग लाइब्रेरी नेटवर्क बना)।",
            keyHighlight = "उप-विषय: सूचना प्रणालियां व नेटवर्क"
        ),
        QuestionEntity(
            id = 424L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "AGRIS अंतरराष्ट्रीय कृषि सूचना प्रणाली किसके द्वारा संचालित की जाती है?",
            optionA = "FAO (Food and Agriculture Organization - Rome)",
            optionB = "UNESCO",
            optionC = "WHO",
            optionD = "IAEA",
            correctOption = 1,
            explanationHindi = "AGRIS की शुरुआत 1974-75 में संयुक्त राष्ट्र के खाद्य एवं कृषि संगठन (FAO, Rome) द्वारा की गई थी।",
            keyHighlight = "उप-विषय: सूचना प्रणालियां व नेटवर्क"
        ),
        QuestionEntity(
            id = 425L,
            category = DefaultQuestions.UNIT_4,
            questionHindi = "INIS (International Nuclear Information System) का मुख्यालय कहाँ स्थित है?",
            optionA = "विएना (ऑस्ट्रिया) - IAEA",
            optionB = "जिनेवा (स्विट्जरलैंड)",
            optionC = "पेरिस (फ्रांस)",
            optionD = "न्यूयॉर्क (यूएसए)",
            correctOption = 1,
            explanationHindi = "INIS की स्थापना 1970 में International Atomic Energy Agency (IAEA, Vienna) द्वारा परमाणु विज्ञान की सूचना हेतु की गई थी।",
            keyHighlight = "उप-विषय: सूचना प्रणालियां व नेटवर्क"
        )
    )

    // =========================================================================
    // UNIT 5: सामान्य कंप्यूटर / बेसिक कंप्यूटर (Basic Computer / ICT for Library)
    // =========================================================================

    fun getUnit5Subtopic1Notes(): String = """
# 📖 उपविषय 01: कंप्यूटर की मूलभूत अवधारणा: हार्डवेयर, सॉफ्टवेयर व पीढ़ियां
**परीक्षा दृष्टिकोण (BLET 2026 विशेष)**

---
### 1. कंप्यूटर की पीढ़ियां (Generations of Computers):
* **प्रथम पीढ़ी (1940-1956):** वैक्यूम ट्यूब (Vacuum Tubes), मशीनी भाषा, चुंबकीय ड्रम। उदा: ENIAC, EDVAC, UNIVAC-I।
* **द्वितीय पीढ़ी (1956-1963):** ट्रांजिस्टर (Transistors - शॉकले, बारडीन, ब्रैटन द्वारा 1947), असेंबली भाषा व प्रारंभिक उच्चस्तरीय भाषा (FORTRAN, COBOL)।
* **तृतीय पीढ़ी (1964-1971):** एकीकृत परिपथ (Integrated Circuits - IC, जैक किल्बी द्वारा), ऑपरेटिंग सिस्टम का प्रारंभ।
* **चतुर्थ पीढ़ी (1971-वर्तमान):** माइक्रोप्रोसेसर (VLSI - Very Large Scale Integration, Intel 4004), पर्सनल कंप्यूटर (PC)।
* **पंचम पीढ़ी (वर्तमान एवं भविष्य):** कृत्रिम बुद्धिमत्ता (Artificial Intelligence - AI, ULSI), क्वांटम कंप्यूटिंग।

### 2. कंप्यूटर मेमोरी (Memory Hierarchy):
* **प्राथमिक मेमोरी (Primary Memory):**
  * **RAM (Random Access Memory):** अस्थिर (Volatile) - कंप्यूटर बंद होने पर डेटा नष्ट हो जाता है।
  * **ROM (Read Only Memory):** गैर-अस्थिर (Non-volatile) - BIOS/फर्मवेयर संग्रहित।
* **द्वितीयक मेमोरी (Secondary Storage):** HDD, SSD, पेन ड्राइव, ऑप्टिकल डिस्क (CD, DVD, Blu-ray)।
* **मेमोरी माप इकाइयां:**
  * 1 Byte = 8 Bits
  * 1 KB = 1024 Bytes
  * 1 MB = 1024 KB
  * 1 GB = 1024 MB
  * 1 TB = 1024 GB
    """.trimIndent()

    fun getUnit5Subtopic1Questions(): List<QuestionEntity> = listOf(
        QuestionEntity(
            id = 501L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "प्रथम पीढ़ी के कंप्यूटरों में मुख्य इलेक्ट्रॉनिक घटक के रूप में किसका प्रयोग किया गया था?",
            optionA = "वैक्यूम ट्यूब (निर्वात नली)",
            optionB = "ट्रांजिस्टर",
            optionC = "इंटीग्रेटेड सर्किट (IC)",
            optionD = "माइक्रोप्रोसेसर",
            correctOption = 1,
            explanationHindi = "प्रथम पीढ़ी (1940-1956) के कंप्यूटरों में स्विचिंग डिवाइस के रूप में वैक्यूम ट्यूब का प्रयोग होता था।",
            keyHighlight = "उप-विषय: कंप्यूटर की मूलभूत अवधारणा"
        ),
        QuestionEntity(
            id = 502L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "कंप्यूटर की कौन सी मेमोरी 'अस्थिर' (Volatile) प्रकृति की होती है, जिसमें बिजली जाते ही डेटा नष्ट हो जाता है?",
            optionA = "ROM",
            optionB = "RAM (Random Access Memory)",
            optionC = "हार्ड डिस्क",
            optionD = "पेन ड्राइव",
            correctOption = 2,
            explanationHindi = "RAM एक अस्थायी (Volatile) मेमोरी है जो चालू प्रोग्राम्स का डेटा रखती है; पावर कट होते ही इसमें रखा डेटा मिट जाता है।",
            keyHighlight = "उप-विषय: कंप्यूटर की मूलभूत अवधारणा"
        ),
        QuestionEntity(
            id = 503L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "1 गीगाबाइट (1 GB) में कितने मेगाबाइट (MB) होते हैं?",
            optionA = "1000 MB",
            optionB = "1024 MB",
            optionC = "1048 MB",
            optionD = "512 MB",
            correctOption = 2,
            explanationHindi = "कंप्यूटर बाइनरी सिस्टम (2^10) पर कार्य करता है, अतः 1 GB = 1024 MB होता है।",
            keyHighlight = "उप-विषय: कंप्यूटर की मूलभूत अवधारणा"
        ),
        QuestionEntity(
            id = 504L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "इंटीग्रेटेड सर्किट (IC चिप) का आविष्कार किसके द्वारा किया गया था?",
            optionA = "जैक किल्बी (Jack Kilby) एवं रॉबर्ट नॉयस",
            optionB = "चार्ल्स बैबेज",
            optionC = "एलन ट्यूरिंग",
            optionD = "बिल गेट्स",
            correctOption = 1,
            explanationHindi = "जैक किल्बी (Texas Instruments) ने 1958 में पहली IC विकसित की, जिसके लिए उन्हें भौतिकी का नोबेल पुरस्कार मिला।",
            keyHighlight = "उप-विषय: कंप्यूटर की मूलभूत अवधारणा"
        ),
        QuestionEntity(
            id = 505L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "विश्व का प्रथम सामान्य उद्देश्य इलेक्ट्रॉनिक डिजिटल कंप्यूटर कौन सा था?",
            optionA = "ENIAC (Electronic Numerical Integrator and Computer)",
            optionB = "UNIVAC",
            optionC = "IBM PC",
            optionD = "एप्पल II",
            correctOption = 1,
            explanationHindi = "ENIAC को 1946 में जे. प्रेस्पर एकर्ट और जॉन मौचली ने पेंसिल्वेनिया विश्वविद्यालय में बनाया था।",
            keyHighlight = "उप-विषय: कंप्यूटर की मूलभूत अवधारणा"
        )
    )

    fun getUnit5Subtopic2Notes(): String = """
# 📖 उपविषय 02: पुस्तकालय स्वचालन: आवश्यकता, योजना एवं प्रमुख घटक
**परीक्षा दृष्टिकोण (BLET 2026 विशेष)**

---
### 1. पुस्तकालय स्वचालन (Library Automation):
* पुस्तकालय की विभिन्न नियमित प्रक्रियाओं (अर्जन, सूचीकरण, परिसंचरण, धारावाहिक नियंत्रण, बजट) को कंप्यूटर एवं आईसीटी की सहायता से स्वतः संचालित करना।

### 2. स्वचालन के मुख्य मॉड्यूल्स (Integrated Library System - ILS Modules):
1. **अर्जन मॉड्यूल (Acquisition Module):** पुस्तक चयन, डुप्लीकेशन चेक, विक्रेता को आदेश पत्र जारी करना, बजट ट्रैकिंग।
2. **सूचीकरण मॉड्यूल (Cataloguing Module):** MARC 21 या CCC/AACR-2 आधारित ग्रंथपरक डेटा प्रविष्टि, बारकोड जनरेशन।
3. **परिसंचरण मॉड्यूल (Circulation Module):** बारकोड स्कैनर द्वारा पुस्तक इशू, रिटर्न, रिन्यू, रिजर्वेशन, ओवरड्यू गणना।
4. **धारावाहिक नियंत्रण (Serials Control Module):** पत्रिकाओं की सदस्यता, प्राप्ति (Kardex का कंप्यूटरीकरण)।
5. **OPAC (Online Public Access Catalogue):** पाठकों हेतु ऑनलाइन खोज।
6. **प्रशासन/रिपोर्ट मॉड्यूल (Administration/Reports):** उपयोगकर्ता प्रबंधन, सांख्यिकी।
    """.trimIndent()

    fun getUnit5Subtopic2Questions(): List<QuestionEntity> = listOf(
        QuestionEntity(
            id = 506L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "पुस्तकालय स्वचालन (Library Automation) शब्द का प्रयोग सर्वप्रथम किसने किया था?",
            optionA = "डी. एस. हार्डर (D. S. Harder)",
            optionB = "मेलविल डेवी",
            optionC = "रंगनाथन",
            optionD = "एच. पी. लुहन",
            correctOption = 1,
            explanationHindi = "ऑटोमेशन शब्द का प्रयोग 1936 में फोर्ड मोटर कंपनी के डी. एस. हार्डर द्वारा ऑटोमेशन इंजीनियरिंग के संदर्भ में किया गया था।",
            keyHighlight = "उप-विषय: पुस्तकालय स्वचालन"
        ),
        QuestionEntity(
            id = 507L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "एकीकृत पुस्तकालय प्रबंधन प्रणाली (ILS) में पत्रिकाओं (Serials) की प्राप्ति दर्ज करने हेतु कंप्यूटर में किस पारंपरिक व्यवस्था का डिजिटल रूप प्रयुक्त होता है?",
            optionA = "कारडेक्स प्रणाली (Kardex System)",
            optionB = "ब्राउन सिस्टम",
            optionC = "शेल्फ लिस्ट",
            optionD = "एक्सेशन रजिस्टर",
            correctOption = 1,
            explanationHindi = "रेमिंगटन रैंड द्वारा विकसित कारडेक्स (Kardex) धारावाहिक नियंत्रण का मानक साधन है जिसे सॉफ्टवेयर में डिजिटाइज़ किया गया है।",
            keyHighlight = "उप-विषय: पुस्तकालय स्वचालन"
        ),
        QuestionEntity(
            id = 508L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "पुस्तकालय स्वचालन में परिसंचरण (Circulation) को तीव्र एवं त्रुटिरहित बनाने हेतु प्रयुक्त सबसे लोकप्रिय तकनीक कौन सी है?",
            optionA = "बारकोड एवं RFID तकनीक",
            optionB = "केवल हाथ से लिखा रजिस्टर",
            optionC = "टाइपराइटर",
            optionD = "माइक्रोफिल्म",
            correctOption = 1,
            explanationHindi = "बारकोड तथा आरएफआईडी तकनीक से पुस्तकों और पाठक कार्ड को स्कैन करके कुछ ही सेकंडों में चेक-इन और चेक-आउट पूरा हो जाता है।",
            keyHighlight = "उप-विषय: पुस्तकालय स्वचालन"
        ),
        QuestionEntity(
            id = 509L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "पुस्तकालय स्वचालन का सबसे बड़ा लाभ पाठकों के दृष्टिकोण से क्या है?",
            optionA = "समय की बचत (चतुर्थ सूत्र) एवं तुरंत 24x7 कैटलॉग खोज",
            optionB = "पुस्तकालय का बंद रहना",
            optionC = "पुस्तकों की संख्या कम होना",
            optionD = "फीस में वृद्धि",
            correctOption = 1,
            explanationHindi = "स्वचालन से पाठक को घर बैठे कैटलॉग खोजने (Web-OPAC) और पुस्तकों को तुरंत प्राप्त करने की सुविधा मिलती है।",
            keyHighlight = "उप-विषय: पुस्तकालय स्वचालन"
        ),
        QuestionEntity(
            id = 510L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "कंप्यूटर आधारित पुस्तकालय सॉफ्टवेयर में 'Z39.50' प्रोटोकॉल का मुख्य उपयोग क्या होता है?",
            optionA = "विभिन्न पुस्तकालयों के कैटलॉग से रिकॉर्ड्स को खोजना एवं सीधे डाउनलोड करना (Copy Cataloguing)",
            optionB = "कंप्यूटर का तापमान नियंत्रित करना",
            optionC = "प्रिंटर चालू करना",
            optionD = "सॉफ्टवेयर की कीमत चुकाना",
            correctOption = 1,
            explanationHindi = "Z39.50 एक अंतरराष्ट्रीय क्लाइंट-सर्वर प्रोटोकॉल है जो एक पुस्तकालय को दूसरे पुस्तकालय के डेटाबेस से ग्रंथपरक डेटा खोजने व आयात करने की अनुमति देता है।",
            keyHighlight = "उप-विषय: पुस्तकालय स्वचालन"
        )
    )

    fun getUnit5Subtopic3Notes(): String = """
# 📖 उपविषय 03: ओपन सोर्स लाइब्रेरी सॉफ्टवेयर: कोहा (Koha), सोउल (SOUL), DSpace
**परीक्षा दृष्टिकोण (BLET 2026 विशेष)**

---
### 1. कोहा (Koha):
* **इतिहास:** विश्व का प्रथम ओपन-सोर्स इंटीग्रेटेड लाइब्रेरी सिस्टम (ILS)।
* **निर्माण:** जनवरी 2000 में होरोव्हेनुआ लाइब्रेरी ट्रस्ट (Horowhenua Library Trust, New Zealand) के लिए कातिपो कम्युनिकेशंस (Katipo Communications) द्वारा जारी।
* **अर्थ:** माओरी (Maori) भाषा में 'Koha' का अर्थ 'उपहार' (Gift / Contribution) होता है।
* **तकनीक:** लिनक्स, पर्ल (Perl), मारियाडीबी/MySQL, अपाचे, वेब-आधारित इंटरफेस।
* **मानक:** MARC 21, UNIMARC, Z39.50, SIP2।

### 2. सोउल (SOUL - Software for University Libraries):
* **निर्माता:** INFLIBNET केंद्र, गांधीनगर (यूजीसी)।
* **संस्करण:**
  * SOUL 1.0 (2000 में)
  * SOUL 2.0 (2009 में)
  * SOUL 3.0 (फरवरी 2021 में नवीनतम वेब-आधारित संस्करण जारी)।
* **डेटाबेस:** MS SQL Server।

### 3. डिजिटल लाइब्रेरी सॉफ्टवेयर:
* **DSpace:** 2002 में MIT Libraries एवं HP Labs द्वारा विकसित। ओपन-सोर्स संस्थागत रिपॉजिटरी (Institutional Repository) सॉफ्टवेयर।
* **GSDL (Greenstone Digital Library):** न्यूजीलैंड की वाइकाटो यूनिवर्सिटी द्वारा यूनेस्को के सहयोग से विकसित।
* **EPrints:** साउथेम्प्टन विश्वविद्यालय (UK) द्वारा।
    """.trimIndent()

    fun getUnit5Subtopic3Questions(): List<QuestionEntity> = listOf(
        QuestionEntity(
            id = 511L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "विश्व का प्रथम ओपन सोर्स इंटीग्रेटेड लाइब्रेरी सिस्टम (Koha) किस देश में विकसित हुआ था?",
            optionA = "न्यूजीलैंड (New Zealand)",
            optionB = "संयुक्त राज्य अमेरिका (USA)",
            optionC = "भारत",
            optionD = "यूनाइटेड किंगडम (UK)",
            correctOption = 1,
            explanationHindi = "कोहा (Koha) को 1999-2000 में न्यूजीलैंड में कातिपो कम्युनिकेशंस द्वारा होरोव्हेनुआ लाइब्रेरी के लिए बनाया गया था।",
            keyHighlight = "उप-विषय: ओपन सोर्स लाइब्रेरी सॉफ्टवेयर"
        ),
        QuestionEntity(
            id = 512L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "SOUL (Software for University Libraries) सॉफ्टवेयर का विकास किस भारतीय संस्था द्वारा किया गया है?",
            optionA = "INFLIBNET केंद्र (गांधीनगर)",
            optionB = "DRTC (बेंगलुरु)",
            optionC = "NISCAIR (नई दिल्ली)",
            optionD = "आईआईटी खड़गपुर",
            correctOption = 1,
            explanationHindi = "SOUL सॉफ्टवेयर का निर्माण और निरंतर विकास INFLIBNET केंद्र (गांधीनगर) द्वारा विशेष रूप से कॉलेज और विश्वविद्यालयों के लिए किया गया है।",
            keyHighlight = "उप-विषय: ओपन सोर्स लाइब्रेरी सॉफ्टवेयर"
        ),
        QuestionEntity(
            id = 513L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "संस्थागत रिपॉजिटरी (Institutional Repository) निर्माण हेतु सर्वाधिक लोकप्रिय ओपन सोर्स सॉफ्टवेयर कौन सा है?",
            optionA = "DSpace",
            optionB = "MS Excel",
            optionC = "Photoshop",
            optionD = "VLC Player",
            correctOption = 1,
            explanationHindi = "DSpace (MIT व HP लैब्स द्वारा 2002 में निर्मित) शोध प्रबंधों, ई-बुक्स और शोध पत्रों के डिजिटल संग्रह हेतु विश्व का सबसे लोकप्रिय ओपन सोर्स सॉफ्टवेयर है।",
            keyHighlight = "उप-विषय: ओपन सोर्स लाइब्रेरी सॉफ्टवेयर"
        ),
        QuestionEntity(
            id = 514L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "माओरी (Maori) भाषा में 'Koha' शब्द का क्या अर्थ होता है?",
            optionA = "उपहार (Gift / Present)",
            optionB = "कंप्यूटर",
            optionC = "पुस्तकालय",
            optionD = "वर्गीकरण",
            correctOption = 1,
            explanationHindi = "न्यूजीलैंड की स्थानीय माओरी भाषा में Koha का अर्थ 'भेंट' या 'उपहार' (Gift/Contribution) होता है।",
            keyHighlight = "उप-विषय: ओपन सोर्स लाइब्रेरी सॉफ्टवेयर"
        ),
        QuestionEntity(
            id = 515L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "SOUL सॉफ्टवेयर का नवीनतम 3.0 संस्करण किस वर्ष लॉन्च किया गया?",
            optionA = "2021",
            optionB = "2015",
            optionC = "2009",
            optionD = "2000",
            correctOption = 1,
            explanationHindi = "SOUL 3.0 का विमोचन फरवरी 2021 में किया गया, जो पूरी तरह से वेब-सक्षम एवं यूनिकोड अनुपालक है।",
            keyHighlight = "उप-विषय: ओपन सोर्स लाइब्रेरी सॉफ्टवेयर"
        )
    )

    fun getUnit5Subtopic4Notes(): String = """
# 📖 उपविषय 04: बारकोड एवं आरएफआईडी (RFID) तकनीक पुस्तकालय में
**परीक्षा दृष्टिकोण (BLET 2026 विशेष)**

---
### 1. बारकोड तकनीक (Barcode Technology):
* विभिन्न मोटाई की समानांतर काली रेखाओं एवं सफेद अंतरालों में डेटा को सांकेतिक (Encrypted) रूप में प्रदर्शित करना।
* **पुस्तकालय में उपयोग:** प्रत्येक पुस्तक पर परिग्रहण संख्या का बारकोड स्टीकर और पाठक पहचान पत्र पर बारकोड।
* **सीमा:** लाइन-ऑफ-साइट (Line of sight) आवश्यक - स्कैनर को सीधे बारकोड के सामने रखना पड़ता है।

### 2. आरएफआईडी तकनीक (Radio Frequency Identification - RFID):
* रेडियो तरंगों के माध्यम से डेटा का स्वतः एवं वायरलेस प्रसारण।
* **घटक (Key Components):**
  1. RFID टैग (RFID Tag / Transponder): सिलिकॉन चिप और एंटीना से युक्त स्टीकर जो पुस्तक के पिछले कवर पर चिपकाया जाता है।
  2. RFID रीडर / एंटीना (Reader/Antenna): रेडियो सिग्नलों को पढ़ना व लिखना।
  3. सेल्फ-चेक इन/चेक आउट कियोस्क (Self-Kiosk): पाठक बिना स्टाफ की सहायता के स्वयं पुस्तक ले और लौटा सकते हैं।
  4. सुरक्षा गेट (Security Gates / EAS - Electronic Article Surveillance): अनधिकृत पुस्तक ले जाने पर सायरन/अलार्म बजाना।
  5. हैंडहेल्ड स्कैनर (Handheld Wand Reader): शेल्फ पर घूमते हुए मिनटों में भंडार सत्यापन (Stock Verification) पूरा करना।
* **विशेषताएं:** लाइन-ऑफ-साइट की आवश्यकता नहीं; एक साथ कई टैग्स को पढ़ा जा सकता है।
    """.trimIndent()

    fun getUnit5Subtopic4Questions(): List<QuestionEntity> = listOf(
        QuestionEntity(
            id = 516L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "RFID का पूर्ण रूप क्या है?",
            optionA = "Radio Frequency Identification",
            optionB = "Remote Frequency Indicator Device",
            optionC = "Rapid File Indexing Directory",
            optionD = "Radar Frequency Information Desk",
            correctOption = 1,
            explanationHindi = "RFID का पूर्ण रूप 'Radio Frequency Identification' (रेडियो आवृत्ति पहचान) है।",
            keyHighlight = "उप-विषय: बारकोड एवं RFID तकनीक"
        ),
        QuestionEntity(
            id = 517L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "बारकोड की तुलना में RFID तकनीक का सबसे बड़ा तकनीकी लाभ क्या है?",
            optionA = "इसमें लाइन-ऑफ-साइट की आवश्यकता नहीं होती तथा एक साथ अनेक पुस्तकों को बिना छुए पढ़ा जा सकता है",
            optionB = "यह बिना बिजली के चलता है",
            optionC = "यह केवल कागजी पुस्तकों पर लगता है",
            optionD = "यह मुफ्त मिलता है",
            correctOption = 1,
            explanationHindi = "RFID रेडियो तरंगों पर कार्य करता है, अतः बैग में बंद पुस्तकों को भी सुरक्षा गेट या रीडर द्वारा बिना सीधे देखे पढ़ा जा सकता है।",
            keyHighlight = "उप-विषय: बारकोड एवं RFID तकनीक"
        ),
        QuestionEntity(
            id = 518L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "पुस्तकालय से अनाधिकृत रूप से बिना निर्गम कराए पुस्तक बाहर ले जाने पर अलार्म बजाने वाला RFID घटक कौन सा है?",
            optionA = "EAS सिक्योरिटी गेट्स (Electronic Article Surveillance)",
            optionB = "OPAC टर्मिनल",
            optionC = "प्रिंटर",
            optionD = "बारकोड स्टिकर",
            correctOption = 1,
            explanationHindi = "पुस्तकालय के निकास द्वार पर लगे EAS सुरक्षा गेट सक्रिय (Active) टैग वाली पुस्तक के गुजरने पर तुरंत अलार्म बजा देते हैं।",
            keyHighlight = "उप-विषय: बारकोड एवं RFID तकनीक"
        ),
        QuestionEntity(
            id = 519L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "पुस्तकालय में पुस्तकों के त्वरित भंडार सत्यापन (Rapid Shelf Verification) हेतु कौन सा उपकरण प्रयुक्त होता है?",
            optionA = "हैंडहेल्ड आरएफआईडी रीडर (Handheld RFID Wand)",
            optionB = "माउस",
            optionC = "कीबोर्ड",
            optionD = "प्रोजेक्टर",
            correctOption = 1,
            explanationHindi = "हैंडहेल्ड वैंड रीडर को अलमारी के सामने फिराते ही रेडियो तरंगों द्वारा सभी पुस्तकों के टैग सेकंडों में स्कैन हो जाते हैं।",
            keyHighlight = "उप-विषय: बारकोड एवं RFID तकनीक"
        ),
        QuestionEntity(
            id = 520L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "बारकोड पढ़ने हेतु किस प्रकार के उपकरण का प्रयोग होता है?",
            optionA = "ऑप्टिकल स्कैनर / लेजर बारकोड रीडर",
            optionB = "रेडियो एंटीना",
            optionC = "माइक्रोफोन",
            optionD = "वेबकैम",
            correctOption = 1,
            explanationHindi = "बारकोड लेजर बीम या ऑप्टिकल सेंसर द्वारा परावर्तित प्रकाश की तीव्रता से बारकोड लाइनों को डिकोड करता है।",
            keyHighlight = "उप-विषय: बारकोड एवं RFID तकनीक"
        )
    )

    fun getUnit5Subtopic5Notes(): String = """
# 📖 उपविषय 05: डिजिटल लाइब्रेरी, इंटरनेट एवं ई-संसाधन (NDLI, शोधगंगा)
**परीक्षा दृष्टिकोण (BLET 2026 विशेष)**

---
### 1. डिजिटल लाइब्रेरी (Digital Library):
* ऐसे पुस्तकालय जहां सूचना संसाधन डिजिटल स्वरूप (ई-बुक्स, ई-जर्नल्स, मल्टीमीडिया) में संग्रहित, अनुक्रमित एवं इंटरनेट या नेटवर्क के माध्यम से सुलभ होते हैं।

### 2. भारत के प्रमुख डिजिटल शिक्षा संसाधन:
1. **NDLI (National Digital Library of India - राष्ट्रीय डिजिटल पुस्तकालय):**
   * **नोडल एजेंसी:** आईआईटी खड़गपुर (शिक्षा मंत्रालय, भारत सरकार द्वारा वित्तपोषित)।
   * **विशेषता:** एकल खिड़की (Single Window) एकीकृत खोज सुविधा युक्त राष्ट्रीय डिजिटल ज्ञान भंडार।
2. **शोधगंगा (Shodhganga):**
   * INFLIBNET द्वारा संचालित भारतीय विश्वविद्यालयों के पीएचडी शोध प्रबंधों का खुला भंडार (Open Repository of Theses)।
3. **ई-शोधसिंधु (e-ShodhSindhu):**
   * उच्च शिक्षण संस्थानों को सहयोगात्मक रूप से ई-पत्रिकाओं एवं डेटाबेसों तक सस्ती पहुँच प्रदान करने वाला कंसोर्टियम।
4. **स्वयं (SWAYAM) एवं स्वयं प्रभा (SWAYAM PRABHA):**
   * MOOCs (Massive Open Online Courses) एवं 24x7 डीटीएच शैक्षिक टीवी चैनल।

### 3. इंटरनेट एवं सर्च इंजन:
* **वेब प्रोटोकॉल:** HTTP/HTTPS, FTP, TCP/IP, SMTP।
* **सर्च इंजन:** Google (1998 - लैरी पेज व सर्गेई ब्रिन), Yahoo, Bing, DuckDuckGo।
    """.trimIndent()

    fun getUnit5Subtopic5Questions(): List<QuestionEntity> = listOf(
        QuestionEntity(
            id = 521L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "भारत का राष्ट्रीय डिजिटल पुस्तकालय (NDLI - National Digital Library of India) किस संस्थान द्वारा विकसित एवं संचालित किया जा रहा है?",
            optionA = "आईआईटी खड़गपुर (IIT Kharagpur)",
            optionB = "आईआईटी दिल्ली",
            optionC = "जेएनयू नई दिल्ली",
            optionD = "आईआईएससी बेंगलुरु",
            correctOption = 1,
            explanationHindi = "शिक्षा मंत्रालय के तहत नेशनल डिजिटल लाइब्रेरी ऑफ इंडिया (NDLI) प्रोजेक्ट का निष्पादन आईआईटी खड़गपुर द्वारा किया गया है।",
            keyHighlight = "उप-विषय: डिजिटल लाइब्रेरी एवं ई-संसाधन"
        ),
        QuestionEntity(
            id = 522L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "इंटरनेट पर वेब पेजों को सुरक्षित रूप से स्थानांतरित करने हेतु कौन सा प्रोटोकॉल प्रयुक्त होता है?",
            optionA = "HTTPS (Hypertext Transfer Protocol Secure)",
            optionB = "FTP",
            optionC = "SMTP",
            optionD = "POP3",
            correctOption = 1,
            explanationHindi = "HTTPS एसएसएल/टीएलएस एन्क्रिप्शन के साथ वेब सामग्री सुरक्षित रूप से ब्राउज़र तक पहुँचाता है।",
            keyHighlight = "उप-विषय: डिजिटल लाइब्रेरी एवं ई-संसाधन"
        ),
        QuestionEntity(
            id = 523L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "ई-पत्रिकाओं (e-Journals) के संदर्भ में 'DOAJ' का पूर्ण रूप क्या है?",
            optionA = "Directory of Open Access Journals",
            optionB = "Digital Online Article Journal",
            optionC = "Directory of Academic Journals",
            optionD = "Database of Online Access Journals",
            correctOption = 1,
            explanationHindi = "DOAJ (Directory of Open Access Journals) उच्च गुणवत्ता वाली पीयर-रिव्यूड ओपन एक्सेस पत्रिकाओं की अंतरराष्ट्रीय निर्देशिका है।",
            keyHighlight = "उप-विषय: डिजिटल लाइब्रेरी एवं ई-संसाधन"
        ),
        QuestionEntity(
            id = 524L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "डिजिटल प्रलेखों के स्थायी एवं विशिष्ट ऑनलाइन पहचान हेतु प्रयुक्त कोड 'DOI' का पूर्ण रूप क्या है?",
            optionA = "Digital Object Identifier",
            optionB = "Direct Online Information",
            optionC = "Document Online Index",
            optionD = "Digital Original Identity",
            correctOption = 1,
            explanationHindi = "DOI का पूर्ण रूप 'Digital Object Identifier' (डिजिटल ऑब्जेक्ट आइडेंटिफ़ायर) है, जो शोध पत्रों का स्थायी वेब लिंक प्रदान करता है।",
            keyHighlight = "उप-विषय: डिजिटल लाइब्रेरी एवं ई-संसाधन"
        ),
        QuestionEntity(
            id = 525L,
            category = DefaultQuestions.UNIT_5,
            questionHindi = "भारत सरकार द्वारा संचालित 'SWAYAM' पोर्टल का मुख्य उद्देश्य क्या है?",
            optionA = "निःशुल्क ऑनलाइन ओपन कोर्सेज (MOOCs) उपलब्ध कराना",
            optionB = "ऑनलाइन टिकट बुक करना",
            optionC = "नौकरी का आवेदन करना",
            optionD = "मौसम की जानकारी देना",
            correctOption = 1,
            explanationHindi = "SWAYAM भारत सरकार का स्वदेशी MOOCs प्लेटफॉर्म है जो स्कूल से लेकर स्नातकोत्तर स्तर तक के निःशुल्क ऑनलाइन कोर्स प्रदान करता है।",
            keyHighlight = "उप-विषय: डिजिटल लाइब्रेरी एवं ई-संसाधन"
        )
    )

    fun getAllUnitsAdditionalQuestions(): List<QuestionEntity> =
        getUnit2Subtopic1Questions() +
        getUnit2Subtopic2Questions() +
        getUnit2Subtopic3Questions() +
        getUnit2Subtopic4Questions() +
        getUnit2Subtopic5Questions() +
        getUnit3Subtopic1Questions() +
        getUnit3Subtopic2Questions() +
        getUnit3Subtopic3Questions() +
        getUnit3Subtopic4Questions() +
        getUnit3Subtopic5Questions() +
        getUnit4Subtopic1Questions() +
        getUnit4Subtopic2Questions() +
        getUnit4Subtopic3Questions() +
        getUnit4Subtopic4Questions() +
        getUnit4Subtopic5Questions() +
        getUnit5Subtopic1Questions() +
        getUnit5Subtopic2Questions() +
        getUnit5Subtopic3Questions() +
        getUnit5Subtopic4Questions() +
        getUnit5Subtopic5Questions()
}
