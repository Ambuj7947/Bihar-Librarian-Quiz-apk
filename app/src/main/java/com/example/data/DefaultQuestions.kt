package com.example.data

import com.example.data.model.QuestionEntity

object DefaultQuestions {

    const val CAT_FOUNDATIONS = "पुस्तकालय विज्ञान के आधार"
    const val CAT_CLASSIFICATION = "वर्गीकरण एवं सूचीकरण"
    const val CAT_REFERENCE = "संदर्भ एवं सूचना सेवाएं"
    const val CAT_MANAGEMENT = "प्रबंधन एवं कंप्यूटर स्वचालन"
    const val CAT_BIHAR_GK = "बिहार विशेष एवं पुस्तकालय धरोहर"
    const val CAT_TEACHING = "शिक्षण अभिक्षमता एवं सामान्य ज्ञान"

    val allCategories = listOf(
        CAT_FOUNDATIONS,
        CAT_CLASSIFICATION,
        CAT_REFERENCE,
        CAT_MANAGEMENT,
        CAT_BIHAR_GK,
        CAT_TEACHING
    )

    fun getInitialQuestions(): List<QuestionEntity> = listOf(
        // Category 1: पुस्तकालय विज्ञान के आधार (Foundations)
        QuestionEntity(
            category = CAT_FOUNDATIONS,
            questionHindi = "डॉ. एस. आर. रंगनाथन द्वारा पुस्तकालय विज्ञान के 'पाँच सूत्र' (Five Laws of Library Science) किस वर्ष प्रतिपादित किए गए थे?",
            optionA = "1928 में",
            optionB = "1931 में",
            optionC = "1933 में",
            optionD = "1948 में",
            correctOption = 1,
            explanationHindi = "डॉ. एस. आर. रंगनाथन ने पुस्तकालय विज्ञान के पाँच सूत्रों को सर्वप्रथम दिसंबर 1928 में मीनाक्षी कॉलेज, अन्नामलाई नगर में आयोजित एक सम्मेलन में प्रतिपादित किया था। बाद में 1931 में मद्रास लाइब्रेरी एसोसिएशन (MALA) द्वारा इसे 'Five Laws of Library Science' पुस्तक के रूप में प्रकाशित किया गया।",
            keyHighlight = "प्रतिपादन: 1928 (अन्नामलाई नगर) | पुस्तक प्रकाशन: 1931 (मद्रास लाइब्रेरी एसोसिएशन)"
        ),
        QuestionEntity(
            category = CAT_FOUNDATIONS,
            questionHindi = "\"पुस्तकालय एक वर्धनशील संस्था है\" (Library is a growing organism) पुस्तकालय विज्ञान का कौन-सा सूत्र है?",
            optionA = "द्वितीय सूत्र",
            optionB = "तृतीय सूत्र",
            optionC = "चतुर्थ सूत्र",
            optionD = "पंचम सूत्र",
            correctOption = 4,
            explanationHindi = "यह पुस्तकालय विज्ञान का पंचम सूत्र (5th Law) है। यह सूत्र जैविक विकास (Biological Growth) की तरह पुस्तकालय के सतत विकास को दर्शाता है। इसके अनुसार पाठकों, पुस्तकों और कर्मचारियों की संख्या सदैव बढ़ती रहती है, अतः पुस्तकालय भवन, फर्नीचर व तकनीकी विस्तार की पूर्व-योजना अनिवार्य है।",
            keyHighlight = "पंचम सूत्र: Library is a growing organism (सजीव वर्धनशील संस्था)"
        ),
        QuestionEntity(
            category = CAT_FOUNDATIONS,
            questionHindi = "'बिहार राज्य सार्वजनिक पुस्तकालय एवं सूचना केंद्र अधिनियम' किस वर्ष पारित किया गया था?",
            optionA = "वर्ष 2002 में",
            optionB = "वर्ष 2006 में",
            optionC = "वर्ष 2008 में",
            optionD = "वर्ष 2015 में",
            correctOption = 3,
            explanationHindi = "बिहार राज्य सार्वजनिक पुस्तकालय एवं सूचना केंद्र अधिनियम (Bihar State Public Libraries Act) वर्ष 2008 में बिहार विधानमंडल द्वारा पारित किया गया। बिहार यह अधिनियम पारित करने वाला भारत का 15वां राज्य बना।",
            keyHighlight = "बिहार पुस्तकालय अधिनियम: वर्ष 2008 (भारत का 15वां राज्य)"
        ),
        QuestionEntity(
            category = CAT_FOUNDATIONS,
            questionHindi = "भारत में 'पुस्तकालय आंदोलन का जनक' (Father of Library Movement in India) किन्हें कहा जाता है?",
            optionA = "डॉ. एस. आर. रंगनाथन",
            optionB = "महाराजा सयाजीराव गायकवाड़ तृतीय",
            optionC = "विलियम एलन बोर्डन",
            optionD = "बी. एस. केशवन",
            correctOption = 2,
            explanationHindi = "बड़ौदा रियासत के महाराजा सयाजीराव गायकवाड़ तृतीय को भारत में पुस्तकालय आंदोलन का जनक माना जाता है। उन्होंने 1910 में अमेरिका से विलियम एलन बोर्डन को बुलाकर बड़ौदा में देश की प्रथम सुनियोजित सार्वजनिक पुस्तकालय प्रणाली विकसित की थी। नोट: डॉ. रंगनाथन को 'पुस्तकालय विज्ञान' (Library Science) का जनक कहा जाता है।",
            keyHighlight = "आंदोलन के जनक: सयाजीराव गायकवाड़ तृतीय | पुस्तकालय विज्ञान के जनक: डॉ. रंगनाथन"
        ),
        QuestionEntity(
            category = CAT_FOUNDATIONS,
            questionHindi = "स्वतंत्र भारत के राष्ट्रीय पुस्तकालय (National Library of India, Kolkata) के प्रथम भारतीय पुस्तकालयाध्यक्ष कौन थे?",
            optionA = "बी. एस. केशवन",
            optionB = "डॉ. एस. आर. रंगनाथन",
            optionC = "खान बहादुर के. एम. असदुल्लाह",
            optionD = "हरिनारायण शर्मा",
            correctOption = 1,
            explanationHindi = "बेलारी शमन्ना केशवन (B. S. Kesavan) स्वतंत्र भारत के राष्ट्रीय पुस्तकालय, कोलकाता के प्रथम भारतीय लाइब्रेरियन थे (1948 में नियुक्त)। उन्हें 'इंडियन नेशनल बिब्लियोग्राफी' (INB) का जनक भी माना जाता है।",
            keyHighlight = "प्रथम भारतीय राष्ट्रीय लाइब्रेरियन: बी. एस. केशवन (1948)"
        ),
        QuestionEntity(
            category = CAT_FOUNDATIONS,
            questionHindi = "भारत में 'पुस्तकालय दिवस' (National Library Day) प्रतिवर्ष किस तिथि को मनाया जाता है?",
            optionA = "12 अगस्त",
            optionB = "14 नवंबर",
            optionC = "5 सितंबर",
            optionD = "23 अप्रैल",
            correctOption = 1,
            explanationHindi = "भारत में प्रतिवर्ष 12 अगस्त को डॉ. एस. आर. रंगनाथन की जयंती के उपलक्ष्य में 'राष्ट्रीय पुस्तकालय दिवस' (National Librarians' Day) मनाया जाता है। उनका जन्म 12 अगस्त 1892 (आधिकारिक दस्तावेजों में 9 अगस्त) को तमिलनाडु के शियाली में हुआ था।",
            keyHighlight = "12 अगस्त: राष्ट्रीय पुस्तकालयाध्यक्ष दिवस (डॉ. रंगनाथन की जयंती)"
        ),

        // Category 2: वर्गीकरण एवं सूचीकरण (Classification & Cataloguing)
        QuestionEntity(
            category = CAT_CLASSIFICATION,
            questionHindi = "ड्यूई डेसीमल क्लासिफिकेशन (DDC) का प्रथम संस्करण किस वर्ष प्रकाशित हुआ था?",
            optionA = "1872 में",
            optionB = "1876 में",
            optionC = "1885 में",
            optionD = "1893 में",
            correctOption = 2,
            explanationHindi = "मेलविल ड्यूई (Melvil Dewey) द्वारा विकसित DDC का प्रथम संस्करण 1876 में मात्र 44 पृष्ठों में गुमनाम (बिना लेखक के नाम) प्रकाशित हुआ था। यह विश्व की सबसे लोकप्रिय दशमलव वर्गीकरण पद्धति है।",
            keyHighlight = "DDC का प्रथम संस्करण: 1876 | पृष्ठ संख्या: 44 | रचयिता: मेलविल ड्यूई"
        ),
        QuestionEntity(
            category = CAT_CLASSIFICATION,
            questionHindi = "कोलन क्लासिफिकेशन (Colon Classification - CC) का विकास किसके द्वारा तथा किस वर्ष किया गया था?",
            optionA = "मेलविल ड्यूई द्वारा (1876)",
            optionB = "डॉ. एस. आर. रंगनाथन द्वारा (1933)",
            optionC = "सी. ए. कटर द्वारा (1891)",
            optionD = "हेनरी ब्लिस द्वारा (1935)",
            correctOption = 2,
            explanationHindi = "कोलन क्लासिफिकेशन (CC) का विकास डॉ. एस. आर. रंगनाथन द्वारा 1933 में मद्रास लाइब्रेरी एसोसिएशन द्वारा प्रकाशित किया गया था। यह विश्व की प्रथम पूर्णतः पक्षात्मक (Analytico-Synthetic) वर्गीकरण प्रणाली है।",
            keyHighlight = "CC: 1933 में डॉ. एस. आर. रंगनाथन द्वारा (पक्षात्मक पद्धति)"
        ),
        QuestionEntity(
            category = CAT_CLASSIFICATION,
            questionHindi = "डॉ. रंगनाथन के PMEST सूत्र में 'E' अक्षर किस मूलभूत श्रेणी (Fundamental Category) का प्रतिनिधित्व करता है?",
            optionA = "Energy (ऊर्जा/क्रिया)",
            optionB = "Entity (इकाई)",
            optionC = "Edition (संस्करण)",
            optionD = "Evaluation (मूल्यांकन)",
            correctOption = 1,
            explanationHindi = "डॉ. रंगनाथन के अनुसार ज्ञान जगत के पाँच मौलिक तत्व (PMEST) हैं: P = Personality (व्यक्तित्व), M = Matter (पदार्थ/द्रव्य), E = Energy (ऊर्जा/कार्य/क्रिया), S = Space (स्थान), T = Time (समय)। ऊर्जा (Energy) का योजक चिन्ह 'कोलन' (:) होता है।",
            keyHighlight = "P = Personality, M = Matter, E = Energy (:), S = Space (.), T = Time (')"
        ),
        QuestionEntity(
            category = CAT_CLASSIFICATION,
            questionHindi = "क्लासीफाइड कैटलॉग कोड (CCC) के प्रथम संस्करण की रचना किस वर्ष हुई थी?",
            optionA = "1931 में",
            optionB = "1934 में",
            optionC = "1937 में",
            optionD = "1951 में",
            correctOption = 2,
            explanationHindi = "Classified Catalogue Code (CCC) डॉ. एस. आर. रंगनाथन द्वारा 1934 में तैयार किया गया था। यह भारत का प्रथम सम्पूर्ण सूचीकरण कोड है। इसमें अनुक्रमणी भाग और वर्गीकृत भाग दोनों होते हैं।",
            keyHighlight = "CCC की रचना: 1934 | लेखक: डॉ. एस. आर. रंगनाथन"
        ),
        QuestionEntity(
            category = CAT_CLASSIFICATION,
            questionHindi = "मानक अंतरराष्ट्रीय पुस्तक संख्या (ISBN) में 1 जनवरी 2007 से कुल कितने अंक (Digits) होते हैं?",
            optionA = "10 अंक",
            optionB = "12 अंक",
            optionC = "13 अंक",
            optionD = "15 अंक",
            correctOption = 3,
            explanationHindi = "1 जनवरी 2007 से पूर्व ISBN 10 अंकों का होता था, जिसे बढ़ाकर 13 अंकों (13 Digits) का कर दिया गया। भारत में ISBN जारी करने वाली राष्ट्रीय संस्था 'राजा राममोहन राय राष्ट्रीय एजेंसी (RRRLF, नई दिल्ली)' है।",
            keyHighlight = "वर्तमान ISBN: 13 अंक | भारतीय एजेंसी: RRRLF"
        ),
        QuestionEntity(
            category = CAT_CLASSIFICATION,
            questionHindi = "सूचीकरण कार्ड (Catalogue Card) का मानक अंतरराष्ट्रीय आकार (Standard Size) क्या होता है?",
            optionA = "12.5 सेमी × 7.5 सेमी (5 × 3 इंच)",
            optionB = "15 सेमी × 10 सेमी",
            optionC = "10 सेमी × 5 सेमी",
            optionD = "12 सेमी × 8 सेमी",
            correctOption = 1,
            explanationHindi = "पुस्तकालय सूची कार्ड (Catalogue Card) का अंतरराष्ट्रीय मानक आकार 12.5 सेमी लंबाई × 7.5 सेमी चौड़ाई (अर्थात 5 × 3 इंच) होता है। इसके निचले मध्य भाग में एक छिद्र होता है जिससे छड़ में कार्ड पिरोए जाते हैं।",
            keyHighlight = "मानक आकार: 12.5 cm × 7.5 cm (5 × 3 inch)"
        ),

        // Category 3: संदर्भ एवं सूचना सेवाएं (Reference & Information Services)
        QuestionEntity(
            category = CAT_REFERENCE,
            questionHindi = "डॉ. रंगनाथन के अनुसार 'त्वरित संदर्भ सेवा' (Ready Reference Service) का उत्तर सामान्यतः कितने समय में दिया जाता है?",
            optionA = "तत्काल या 5 से 30 मिनट के भीतर",
            optionB = "1 से 2 दिन के भीतर",
            optionC = "कम से कम 1 सप्ताह में",
            optionD = "1 माह में",
            correctOption = 1,
            explanationHindi = "त्वरित संदर्भ सेवा (Ready Reference Service) का उत्तर संदर्भ ग्रंथों (विश्वकोश, शब्दकोश, निर्देशिका आदि) से तुरंत या 5 से 30 मिनट के अल्प समय में दिया जाता है। इसके विपरीत 'व्यापक संदर्भ सेवा' (Long Range Reference Service) में लंबा समय लगता है।",
            keyHighlight = "त्वरित संदर्भ सेवा: 5 से 30 मिनट | व्यापक संदर्भ सेवा: अधिक समय"
        ),
        QuestionEntity(
            category = CAT_REFERENCE,
            questionHindi = "पुस्तकालय विज्ञान में 'CAS' का पूर्ण रूप (Full Form) क्या है?",
            optionA = "Current Awareness Service",
            optionB = "Computerized Access System",
            optionC = "Central Academic Service",
            optionD = "Current Archive Standard",
            correctOption = 1,
            explanationHindi = "CAS का अर्थ 'Current Awareness Service' (सामयिक जागरूकता सेवा) है। यह सेवा शोधकर्ताओं और पाठकों को उनके अध्ययन क्षेत्र में होने वाले नवीनतम साहित्यों, पत्रिकाओं और आविष्कारों से अद्यतन रखने हेतु दी जाती है।",
            keyHighlight = "CAS = Current Awareness Service (सामयिक जागरूकता सेवा)"
        ),
        QuestionEntity(
            category = CAT_REFERENCE,
            questionHindi = "भारत में 'शोधगंगा' (Shodhganga) किसका डिजिटल रिपोजिटरी भंडार है?",
            optionA = "ई-बुक्स का",
            optionB = "भारतीय शोध प्रबंधों (Ph.D. Theses) का",
            optionC = "दैनिक समाचार पत्रों का",
            optionD = "पुस्तकालय नियमों का",
            correctOption = 2,
            explanationHindi = "'शोधगंगा' INFLIBNET केंद्र, गांधीनगर द्वारा प्रबंधित भारतीय विश्वविद्यालयों में स्वीकृत डॉक्टरेट शोध प्रबंधों (Ph.D. Theses & Dissertations) का एक ओपन-एक्सेस डिजिटल भंडार है।",
            keyHighlight = "शोधगंगा: भारतीय Ph.D. शोध प्रबंधों (Theses) का भंडार | संचालक: INFLIBNET"
        ),
        QuestionEntity(
            category = CAT_REFERENCE,
            questionHindi = "'INFLIBNET' (सूचना एवं पुस्तकालय नेटवर्क) केंद्र का मुख्यालय कहाँ स्थित है?",
            optionA = "नई दिल्ली",
            optionB = "गांधीनगर (गुजरात)",
            optionC = "बेंगलुरु (कर्नाटक)",
            optionD = "कोलकाता (पश्चिम बंगाल)",
            correctOption = 2,
            explanationHindi = "INFLIBNET (Information and Library Network Centre) विश्वविद्यालय अनुदान आयोग (UGC) का एक स्वायत्त अंतर-विश्वविद्यालय केंद्र है। इसका मुख्यालय इन्फोसिटी, गांधीनगर (गुजरात) में स्थित है।",
            keyHighlight = "INFLIBNET मुख्यालय: गांधीनगर, गुजरात | स्थापना: 1991 (1996 में स्वायत्त)"
        ),
        QuestionEntity(
            category = CAT_REFERENCE,
            questionHindi = "निम्नलिखित में से कौन-सा 'प्राथमिक सूचना स्रोत' (Primary Information Source) का उदाहरण है?",
            optionA = "विश्वकोश (Encyclopedia)",
            optionB = "शोध पत्रिका में प्रकाशित मूल शोध पत्र (Research Article)",
            optionC = "ग्रन्थसूची (Bibliography)",
            optionD = "पाठ्यपुस्तक (Textbook)",
            correctOption = 2,
            explanationHindi = "प्राथमिक स्रोत (Primary Sources) वे होते हैं जिनमें मौलिक विचार और नवीन शोध पहली बार प्रकाशित होते हैं (जैसे: शोध पत्र, पेटेंट, मानक, शोध प्रबंध)। पाठ्यपुस्तकें और विश्वकोश द्वितीयक या तृतीयक स्रोत की श्रेणी में आते हैं।",
            keyHighlight = "प्राथमिक स्रोत: मौलिक शोध पत्र, पेटेंट, थीसिस | द्वितीयक: विश्वकोश, पाठ्यपुस्तक"
        ),

        // Category 4: प्रबंधन एवं कंप्यूटर स्वचालन (Management & IT)
        QuestionEntity(
            category = CAT_MANAGEMENT,
            questionHindi = "प्रसिद्ध प्रबंधन सूत्र 'POSDCORB' किसके द्वारा प्रतिपादित किया गया था?",
            optionA = "हेनरी फेयोल",
            optionB = "लूथर गुलिक (Luther Gulick)",
            optionC = "एफ. डब्ल्यू. टेलर",
            optionD = "पीटर ड्रकर",
            correctOption = 2,
            explanationHindi = "लूथर गुलिक और एल. उर्विक ने 1937 में प्रबंधन के सात कार्यों को दर्शाने हेतु 'POSDCORB' सूत्र दिया: Planning (योजना), Organizing (संगठन), Staffing (कार्मिक), Directing (निर्देशन), Co-ordinating (समन्वय), Reporting (प्रतिवेदन), Budgeting (बजट)।",
            keyHighlight = "POSDCORB: लूथर गुलिक (1937) - प्रबंधन के 7 मौलिक कार्य"
        ),
        QuestionEntity(
            category = CAT_MANAGEMENT,
            questionHindi = "'कोहा' (Koha) पुस्तकालय जगत में क्या है?",
            optionA = "एक ओपन सोर्स एकीकृत पुस्तकालय प्रबंधन सॉफ्टवेयर (ILS)",
            optionB = "एक व्यावसायिक ऑपरेटिंग सिस्टम",
            optionC = "एक डिजिटल स्कैनर मशीन",
            optionD = "एक ऑनलाइन ई-कॉमर्स वेबसाइट",
            correctOption = 1,
            explanationHindi = "कोहा (Koha) विश्व का सर्वप्रथम ओपन सोर्स एकीकृत पुस्तकालय प्रणाली (Integrated Library System - ILS) सॉफ्टवेयर है। इसे वर्ष 1999 में न्यूजीलैंड में कटोपो कम्युनिकेशंस द्वारा विकसित किया गया तथा 2000 में जारी किया गया।",
            keyHighlight = "कोहा: विश्व का पहला ओपन सोर्स लाइब्रेरी सॉफ्टवेयर (ILS) | वर्ष: 1999-2000"
        ),
        QuestionEntity(
            category = CAT_MANAGEMENT,
            questionHindi = "पुस्तकालय परिसंचरण में 'ब्राउने निर्गम-आगम प्रणाली' (Browne Charging System) का विकास किसने किया था?",
            optionA = "नीना ई. ब्राउन (Nina E. Browne)",
            optionB = "जॉन कॉटन डाना",
            optionC = "मेलविल ड्यूई",
            optionD = "रंगनाथन",
            correctOption = 1,
            explanationHindi = "नीना ई. ब्राउन (बोस्टन लाइब्रेरी ब्यूरो) ने 1895 में 'ब्राउने चार्जिंग सिस्टम' विकसित किया। इसमें पुस्तक पॉकेट, पुस्तक कार्ड और पाठक टिकट (Reader's Ticket) का उपयोग करके पुस्तकों का लेन-देन रिकॉर्ड किया जाता है।",
            keyHighlight = "ब्राउने प्रणाली: नीना ई. ब्राउन (1895) - कार्ड व पॉकेट आधारित लेन-देन"
        ),
        QuestionEntity(
            category = CAT_MANAGEMENT,
            questionHindi = "पुस्तकालय बजट निर्माण में 'शून्य आधारित बजट' (Zero-Based Budgeting - ZBB) की अवधारणा किसने दी?",
            optionA = "पीटर पायर (Peter Phyrr)",
            optionB = "हेनरी फेयोल",
            optionC = "कार्ल मार्क्स",
            optionD = "एडम स्मिथ",
            correctOption = 1,
            explanationHindi = "शून्य आधारित बजट (ZBB) की अवधारणा वर्ष 1970 में पीटर पायर (Peter Phyrr) द्वारा टेक्सास इंस्ट्रूमेंट्स में दी गई। इसमें पिछले वर्ष के खर्चों को आधार न मानकर हर मद की आवश्यकता का नए सिरे से शून्य से औचित्य सिद्ध करना होता है।",
            keyHighlight = "Zero-Based Budget (ZBB): पीटर पायर (1970)"
        ),
        QuestionEntity(
            category = CAT_MANAGEMENT,
            questionHindi = "पुस्तकालयों में स्वचालित पुस्तक पहचान और सुरक्षा हेतु प्रयुक्त 'RFID' तकनीक का पूर्ण रूप क्या है?",
            optionA = "Radio Frequency Identification",
            optionB = "Rapid File Identifier",
            optionC = "Remote Format Information Device",
            optionD = "Random Frequency Indicator",
            correctOption = 1,
            explanationHindi = "RFID का अर्थ 'Radio Frequency Identification' है। इसमें माइक्रोचिप लगे टैग और रेडियो तरंगों द्वारा पुस्तकों की स्वचालित पहचान, इन्वेंट्री गणना, स्वयं-निर्गम (Self Checkout) और चोरी-निरोधक सुरक्षा गेट संचालित किए जाते हैं।",
            keyHighlight = "RFID = Radio Frequency Identification (रेडियो तरंगों से पुस्तक ट्रैकिंग)"
        ),

        // Category 5: बिहार विशेष एवं पुस्तकालय धरोहर (Bihar GK & Heritage)
        QuestionEntity(
            category = CAT_BIHAR_GK,
            questionHindi = "प्राचीन काल के विश्वविख्यात नालंदा विश्वविद्यालय के भव्य पुस्तकालय का क्या नाम था?",
            optionA = "धर्मगंज",
            optionB = "ज्ञानपीठ",
            optionC = "विद्यासागर",
            optionD = "भारती सदन",
            correctOption = 1,
            explanationHindi = "प्राचीन नालंदा विश्वविद्यालय के विशाल पुस्तकालय को 'धर्मगंज' (Dharmaganja - धर्म का पर्वत) कहा जाता था। इसमें तीन बहुमंजिला भव्य भवन थे जिनके नाम 'रत्नसागर', 'रत्नोदधि' और 'रत्नरंजक' थे।",
            keyHighlight = "नालंदा पुस्तकालय: धर्मगंज (तीन भवन: रत्नसागर, रत्नोदधि, रत्नरंजक)"
        ),
        QuestionEntity(
            category = CAT_BIHAR_GK,
            questionHindi = "पटना स्थित ऐतिहासिक 'खुदा बख्श ओरिएंटल पब्लिक लाइब्रेरी' की स्थापना किस वर्ष हुई थी?",
            optionA = "वर्ष 1875 में",
            optionB = "वर्ष 1891 में",
            optionC = "वर्ष 1912 में",
            optionD = "वर्ष 1947 में",
            correctOption = 2,
            explanationHindi = "खुदा बख्श ओरिएंटल पब्लिक लाइब्रेरी (पटना) की स्थापना मौलवी खुदा बख्श खान द्वारा 29 अक्टूबर 1891 को की गई थी। इसमें दुर्लभ अरबी, फारसी पांडुलिपियों का अमूल्य संग्रह है। 1969 में संसद के अधिनियम द्वारा इसे राष्ट्रीय महत्व का संस्थान घोषित किया गया।",
            keyHighlight = "स्थापना: 29 अक्टूबर 1891 | संस्थापक: मौलवी खुदा बख्श खान | स्थान: पटना"
        ),
        QuestionEntity(
            category = CAT_BIHAR_GK,
            questionHindi = "बिहार की राजधानी पटना में स्थित सुप्रसिद्ध 'सिन्हा लाइब्रेरी' का आधिकारिक नाम क्या है?",
            optionA = "श्रीमती राधिका सिन्हा संस्थान एवं सच्चिदानंद सिन्हा पुस्तकालय",
            optionB = "मौलाना अबुल कलाम आज़ाद पुस्तकालय",
            optionC = "पाटलिपुत्र सेंट्रल लाइब्रेरी",
            optionD = "बिहार विद्यापीठ वाचनालय",
            correctOption = 1,
            explanationHindi = "सिन्हा लाइब्रेरी की स्थापना संविधान सभा के प्रथम अध्यक्ष डॉ. सच्चिदानंद सिन्हा द्वारा अपनी दिवंगत पत्नी श्रीमती राधिका सिन्हा की स्मृति में 1924 में की गई थी। इसका आधिकारिक नाम 'श्रीमती राधिका सिन्हा संस्थान एवं सच्चिदानंद सिन्हा पुस्तकालय' है।",
            keyHighlight = "संस्थापक: डॉ. सच्चिदानंद सिन्हा (1924) | पटना स्थित महत्वपूर्ण शोध पुस्तकालय"
        ),
        QuestionEntity(
            category = CAT_BIHAR_GK,
            questionHindi = "बिहार का राजकीय वृक्ष और राजकीय पक्षी क्रमशः कौन-से हैं?",
            optionA = "पीपल और गौरैया (House Sparrow)",
            optionB = "बरगद और मोर",
            optionC = "आम और कोयल",
            optionD = "नीम और तोता",
            correctOption = 1,
            explanationHindi = "बिहार का राजकीय वृक्ष 'पीपल' (बोधिवृक्ष का प्रतीक), राजकीय पक्षी 'गौरैया', राजकीय पशु 'बैल' और राजकीय पुष्प 'गेंदा' (Marigold) है।",
            keyHighlight = "राजकीय वृक्ष: पीपल | राजकीय पक्षी: गौरैया"
        ),
        QuestionEntity(
            category = CAT_BIHAR_GK,
            questionHindi = "प्राचीन विक्रमशिला विश्वविद्यालय, जहाँ विशाल पुस्तकालय व बौद्ध शिक्षा केंद्र था, बिहार के किस वर्तमान जिले में स्थित था?",
            optionA = "भागलपुर",
            optionB = "नालंदा",
            optionC = "गया",
            optionD = "वैशाली",
            correctOption = 1,
            explanationHindi = "विक्रमशिला विश्वविद्यालय की स्थापना पाल वंश के प्रतापी राजा धर्मपाल ने 8वीं शताब्दी में की थी। इसके अवशेष बिहार के वर्तमान भागलपुर जिले के अंतीचक गाँव में स्थित हैं। यहाँ तंत्रयान बौद्ध धर्म व दर्शन का समृद्ध पुस्तकालय था।",
            keyHighlight = "विक्रमशिला: भागलपुर जिला (बिहार) | संस्थापक: पाल नरेश धर्मपाल"
        ),

        // Category 6: शिक्षण अभिक्षमता एवं सामान्य ज्ञान (Teaching Aptitude)
        QuestionEntity(
            category = CAT_TEACHING,
            questionHindi = "एक पुस्तकालयाध्यक्ष (Librarian) का पाठकों के प्रति सबसे महत्वपूर्ण दायित्व क्या होता है?",
            optionA = "पाठकों को सही समय पर सही सूचना व सामग्री विनम्रतापूर्वक उपलब्ध कराना",
            optionB = "पुस्तकों को अलमारी में बंद रखना ताकि वे खराब न हों",
            optionC = "पाठकों को केवल जुर्माना लगाना",
            optionD = "केवल परीक्षा के समय पुस्तकालय खोलना",
            correctOption = 1,
            explanationHindi = "रंगनाथन के प्रथम तीन सूत्रों (पुस्तकें उपयोग के लिए हैं, प्रत्येक पाठक को उसकी पुस्तक मिले, प्रत्येक पुस्तक को उसका पाठक मिले) के अनुसार पुस्तकालयाध्यक्ष का सर्वोपरि गुण सेवा-भाव, धैर्य और सही पाठक को सही समय पर उपयुक्त अध्ययन सामग्री उपलब्ध कराना है।",
            keyHighlight = "मुख्य ध्येय: सही पाठक को सही समय पर उपयुक्त अध्ययन सामग्री देना"
        ),
        QuestionEntity(
            category = CAT_TEACHING,
            questionHindi = "शिक्षा व पुस्तकालय प्रबंधन में 'क्रियात्मक अनुसंधान' (Action Research) का मुख्य उद्देश्य क्या होता है?",
            optionA = "कार्यस्थल या शिक्षण की तात्कालिक समस्याओं का व्यावहारिक समाधान खोजना",
            optionB = "प्राचीन इतिहास पर शोध ग्रंथ लिखना",
            optionC = "केवल अकादमिक उपाधि प्राप्त करना",
            optionD = "वित्तीय लाभ कमाना",
            correctOption = 1,
            explanationHindi = "क्रियात्मक अनुसंधान (Action Research) का उद्देश्य संस्था या विद्यालय की दैनिक कार्यप्रणाली में आने वाली व्यावहारिक समस्याओं की पहचान कर स्थानीय स्तर पर उनका त्वरित वैज्ञानिक समाधान निकालना होता है।",
            keyHighlight = "Action Research: तात्कालिक समस्याओं का वैज्ञानिक व व्यावहारिक समाधान"
        ),
        QuestionEntity(
            category = CAT_TEACHING,
            questionHindi = "पुस्तकालय में 'पाठक दीक्षा / प्रयोक्ता शिक्षा' (User Orientation / Education) का प्रमुख उद्देश्य क्या है?",
            optionA = "पाठकों को कैटलॉग, ओपेक (OPAC) और शेल्फ व्यवस्था समझने में स्वावलंबी बनाना",
            optionB = "पुस्तकालय के कर्मचारियों की संख्या कम करना",
            optionC = "पाठकों को पुस्तकालय आने से रोकना",
            optionD = "केवल नई पुस्तकों की बिक्री बढ़ाना",
            correctOption = 1,
            explanationHindi = "पाठक दीक्षा (User Education) नए पाठकों को पुस्तकालय की व्यवस्था, नियमों, संदर्भ स्रोतों और ऑनलाइन कैटलॉग (OPAC) के प्रयोग से परिचित कराती है ताकि वे बिना किसी कठिनाई के स्वतंत्र रूप से ज्ञान प्राप्त कर सकें।",
            keyHighlight = "User Education: पाठकों को पुस्तकालय संसाधनों के उपयोग में आत्मनिर्भर बनाना"
        )
    )
}
