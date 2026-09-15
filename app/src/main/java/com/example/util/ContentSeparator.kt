package com.example.util

import com.example.data.model.QuestionEntity

data class ParsedQuestionItem(
    val questionHindi: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctOption: Int, // 1 = A, 2 = B, 3 = C, 4 = D
    val explanationHindi: String = "",
    val keyHighlight: String = ""
) {
    fun toQuestionEntity(category: String): QuestionEntity {
        return QuestionEntity(
            category = category,
            questionHindi = questionHindi.trim(),
            optionA = optionA.trim(),
            optionB = optionB.trim(),
            optionC = optionC.trim(),
            optionD = optionD.trim(),
            correctOption = correctOption.coerceIn(1, 4),
            explanationHindi = explanationHindi.trim().ifEmpty {
                val correctText = when (correctOption) {
                    1 -> optionA
                    2 -> optionB
                    3 -> optionC
                    4 -> optionD
                    else -> optionA
                }
                "सही उत्तर: $correctText"
            },
            keyHighlight = keyHighlight.trim(),
            isUserAdded = true
        )
    }
}

data class SeparationResult(
    val notesText: String,
    val questions: List<ParsedQuestionItem>,
    val rawTextLength: Int = 0
)

object ContentSeparator {

    private val libraryDistractors = listOf(
        "डॉ. एस. आर. रंगनाथन",
        "मेलविल डेवी (Melvil Dewey)",
        "सी. ए. कटर (C.A. Cutter)",
        "डब्ल्यू. सी. सेयर्स (W.C. Berwick Sayers)",
        "हेनरी ला फॉन्टेन",
        "पॉल ओटलेट",
        "कोलन क्लासिफिकेशन (CC)",
        "डेवी डेसिमल क्लासिफिकेशन (DDC)",
        "यूनिवर्सल डेसिमल क्लासिफिकेशन (UDC)",
        "पुस्तकालय विज्ञान के 5 सूत्र",
        "डिलीवरी ऑफ बुक्स एक्ट 1954",
        "राजा राममोहन राय लाइब्रेरी फाउंडेशन (RRRLF)",
        "इन्फ्लिबनेट (INFLIBNET)",
        "ओपेक (OPAC) प्रणाली",
        "खुदा बख्श ओरिएंटल पब्लिक लाइब्रेरी, पटना",
        "राष्ट्रीय पुस्तकालय कोलकाता"
    )

    /**
     * Extracts YouTube Video ID from any standard YouTube URL
     */
    fun extractYouTubeVideoId(url: String): String? {
        val trimmed = url.trim()
        if (trimmed.isEmpty()) return null

        val patterns = listOf(
            Regex("(?:https?://)?(?:www\\.)?youtube\\.com/watch\\?(?:.*&)?v=([a-zA-Z0-9_-]{11})"),
            Regex("(?:https?://)?(?:www\\.)?youtu\\.be/([a-zA-Z0-9_-]{11})"),
            Regex("(?:https?://)?(?:www\\.)?youtube\\.com/shorts/([a-zA-Z0-9_-]{11})"),
            Regex("(?:https?://)?(?:www\\.)?youtube\\.com/embed/([a-zA-Z0-9_-]{11})"),
            Regex("(?:https?://)?(?:www\\.)?m\\.youtube\\.com/watch\\?(?:.*&)?v=([a-zA-Z0-9_-]{11})")
        )

        for (regex in patterns) {
            val match = regex.find(trimmed)
            if (match != null) {
                return match.groupValues.getOrNull(1)
            }
        }

        // If the user directly pasted an 11-char ID
        if (trimmed.length == 11 && trimmed.matches(Regex("^[a-zA-Z0-9_-]{11}$"))) {
            return trimmed
        }

        return null
    }

    fun getYouTubeThumbnailUrl(videoId: String): String {
        return "https://img.youtube.com/vi/$videoId/hqdefault.jpg"
    }

    /**
     * Intelligently separates raw input text (which may contain both lecture notes and Q&A)
     * into clean study notes and structured quiz questions.
     */
    fun separateNotesAndQuestions(rawContent: String): SeparationResult {
        if (rawContent.isBlank()) {
            return SeparationResult("", emptyList(), 0)
        }

        val cleanText = rawContent.replace("\r\n", "\n").trim()
        val lines = cleanText.lines()

        // 1. Check for explicit delimiters (e.g., [Notes] ... [Questions] or --- or क्विज़/प्रश्न)
        val questionsHeaderRegex = Regex(
            "(?i)^\\s*(?:---+|===+|###|##|#)?\\s*(?:\\[?(?:questions?|quiz|mcqs?|q&a|प्रश्नोत्तरी|महत्वपूर्ण प्रश्न|अभ्यास प्रश्न|बहुविकल्पीय प्रश्न|वस्तुनिष्ठ प्रश्न)\\]?)\\s*(?:---+|===+)?\\s*$",
            RegexOption.IGNORE_CASE
        )

        val notesHeaderRegex = Regex(
            "(?i)^\\s*(?:---+|===+|###|##|#)?\\s*(?:\\[?(?:notes?|theory|study material|lecture notes|अध्ययन सामग्री|नोट्स|मुख्य बिंदु|थ्योरी)\\]?)\\s*(?:---+|===+)?\\s*$",
            RegexOption.IGNORE_CASE
        )

        var questionHeaderIndex = -1
        for (i in lines.indices) {
            if (questionsHeaderRegex.containsMatchIn(lines[i])) {
                questionHeaderIndex = i
                break
            }
        }

        val notesLines = mutableListOf<String>()
        val questionLines = mutableListOf<String>()

        if (questionHeaderIndex != -1) {
            // Explicit header found
            for (i in 0 until questionHeaderIndex) {
                val line = lines[i]
                if (!notesHeaderRegex.containsMatchIn(line)) {
                    notesLines.add(line)
                }
            }
            for (i in (questionHeaderIndex + 1) until lines.size) {
                questionLines.add(lines[i])
            }
        } else {
            // Find first line that looks like a question start
            val firstQuestionIndex = lines.indexOfFirst { line ->
                isQuestionStartLine(line)
            }

            if (firstQuestionIndex != -1) {
                // Everything before first question is notes
                for (i in 0 until firstQuestionIndex) {
                    val line = lines[i]
                    if (!notesHeaderRegex.containsMatchIn(line)) {
                        notesLines.add(line)
                    }
                }
                for (i in firstQuestionIndex until lines.size) {
                    questionLines.add(lines[i])
                }
            } else {
                // No questions detected at all, or mixed
                notesLines.addAll(lines)
            }
        }

        val finalNotes = notesLines.joinToString("\n").trim()
        val parsedQuestions = parseQuestionsFromLines(questionLines)

        return SeparationResult(
            notesText = finalNotes,
            questions = parsedQuestions,
            rawTextLength = cleanText.length
        )
    }

    private fun isQuestionStartLine(line: String): Boolean {
        val trimmed = line.trim()
        val questionPatterns = listOf(
            Regex("^(?:Q|q)[0-9]+[\\.\\:\\)]\\s*.*"),
            Regex("^(?:Q|q)[\\.\\:]\\s*.*"),
            Regex("^(?:प्र|प्रश्न)[\\s\\.\\:0-9]+[\\.\\:\\)]?\\s*.*"),
            Regex("^[0-9]+[\\.\\)]\\s+.*\\?$"),
            Regex("^[0-9]+[\\.\\)]\\s+(?:किस|कौन|कब|कहाँ|क्या|निम्न|पुस्तकालय).*")
        )
        return questionPatterns.any { it.matches(trimmed) }
    }

    /**
     * Parses a list of lines representing questions, options, answers, and explanations.
     */
    fun parseQuestionsFromLines(lines: List<String>): List<ParsedQuestionItem> {
        val questions = mutableListOf<ParsedQuestionItem>()
        if (lines.isEmpty()) return questions

        var currentQuestionText = ""
        var optA = ""
        var optB = ""
        var optC = ""
        var optD = ""
        var correctOption = 1
        var explanation = ""
        var inOptions = false

        fun saveCurrentQuestionIfValid() {
            if (currentQuestionText.isNotBlank()) {
                // If user provided question & answer without 4 options, auto-generate options!
                val cleanQ = currentQuestionText.trim()
                var a = optA.trim()
                var b = optB.trim()
                var c = optC.trim()
                var d = optD.trim()

                if (a.isEmpty() && b.isEmpty()) {
                    // Only Q & Ans given!
                    val fallbackAnswer = explanation.ifBlank { "सही उत्तर" }
                    val distractors = libraryDistractors.shuffled().filter { it != fallbackAnswer }.take(3)
                    a = fallbackAnswer
                    b = distractors.getOrNull(0) ?: "विकल्प B"
                    c = distractors.getOrNull(1) ?: "विकल्प C"
                    d = distractors.getOrNull(2) ?: "विकल्प D"
                    correctOption = 1
                } else if (c.isEmpty() && d.isEmpty()) {
                    // Only 2 options provided (e.g. True/False or 2 choices)
                    if (c.isEmpty()) c = "उपरोक्त दोनों"
                    if (d.isEmpty()) d = "इनमें से कोई नहीं"
                }

                questions.add(
                    ParsedQuestionItem(
                        questionHindi = cleanQ,
                        optionA = a.ifBlank { "विकल्प A" },
                        optionB = b.ifBlank { "विकल्प B" },
                        optionC = c.ifBlank { "विकल्प C" },
                        optionD = d.ifBlank { "विकल्प D" },
                        correctOption = correctOption.coerceIn(1, 4),
                        explanationHindi = explanation.trim(),
                        keyHighlight = ""
                    )
                )

                // Reset
                currentQuestionText = ""
                optA = ""
                optB = ""
                optC = ""
                optD = ""
                correctOption = 1
                explanation = ""
                inOptions = false
            }
        }

        val optARegex = Regex("^(?:\\(?A\\)?[\\.\\:\\-\\s]|(?:1[\\.\\)\\:\\-]\\s)|(?:\\(?क\\)?[\\.\\:\\-\\s]))\\s*(.*)", RegexOption.IGNORE_CASE)
        val optBRegex = Regex("^(?:\\(?B\\)?[\\.\\:\\-\\s]|(?:2[\\.\\)\\:\\-]\\s)|(?:\\(?ख\\)?[\\.\\:\\-\\s]))\\s*(.*)", RegexOption.IGNORE_CASE)
        val optCRegex = Regex("^(?:\\(?C\\)?[\\.\\:\\-\\s]|(?:3[\\.\\)\\:\\-]\\s)|(?:\\(?ग\\)?[\\.\\:\\-\\s]))\\s*(.*)", RegexOption.IGNORE_CASE)
        val optDRegex = Regex("^(?:\\(?D\\)?[\\.\\:\\-\\s]|(?:4[\\.\\)\\:\\-]\\s)|(?:\\(?घ\\)?[\\.\\:\\-\\s]))\\s*(.*)", RegexOption.IGNORE_CASE)

        val answerRegex = Regex(
            "^(?:उत्तर|ans|answer|correct|सही उत्तर|उत्तर विकल्प)[\\s\\:\\-\\=]+(.*)",
            RegexOption.IGNORE_CASE
        )
        val expRegex = Regex(
            "^(?:व्याख्या|explanation|exp|विवरण|टिप्पणी)[\\s\\:\\-\\=]+(.*)",
            RegexOption.IGNORE_CASE
        )

        for (rawLine in lines) {
            val line = rawLine.trim()
            if (line.isEmpty()) continue

            val ansMatch = answerRegex.find(line)
            if (ansMatch != null) {
                val ansVal = ansMatch.groupValues[1].trim()
                correctOption = parseAnswerToOptionIndex(ansVal, optA, optB, optC, optD)
                continue
            }

            val expMatch = expRegex.find(line)
            if (expMatch != null) {
                val expVal = expMatch.groupValues[1].trim()
                explanation = if (explanation.isEmpty()) expVal else "$explanation $expVal"
                continue
            }

            val aMatch = optARegex.find(line)
            if (aMatch != null) {
                optA = aMatch.groupValues[1].trim()
                inOptions = true
                continue
            }

            val bMatch = optBRegex.find(line)
            if (bMatch != null) {
                optB = bMatch.groupValues[1].trim()
                inOptions = true
                continue
            }

            val cMatch = optCRegex.find(line)
            if (cMatch != null) {
                optC = cMatch.groupValues[1].trim()
                inOptions = true
                continue
            }

            val dMatch = optDRegex.find(line)
            if (dMatch != null) {
                optD = dMatch.groupValues[1].trim()
                inOptions = true
                continue
            }

            if (isQuestionStartLine(line)) {
                saveCurrentQuestionIfValid()
                // Strip leading question markers
                currentQuestionText = cleanQuestionPrefix(line)
                inOptions = false
            } else if (!inOptions && currentQuestionText.isNotEmpty()) {
                // Continuation of question text
                currentQuestionText += " $line"
            } else if (inOptions && explanation.isNotEmpty()) {
                // Continuation of explanation
                explanation += " $line"
            }
        }

        saveCurrentQuestionIfValid()
        return questions
    }

    private fun cleanQuestionPrefix(line: String): String {
        return line
            .replace(Regex("^(?:Q|q)[0-9]+[\\.\\:\\)]\\s*"), "")
            .replace(Regex("^(?:Q|q)[\\.\\:]\\s*"), "")
            .replace(Regex("^(?:प्र|प्रश्न)[\\s\\.\\:0-9]+[\\.\\:\\)]?\\s*"), "")
            .replace(Regex("^[0-9]+[\\.\\)]\\s+"), "")
            .trim()
    }

    private fun parseAnswerToOptionIndex(
        ansText: String,
        optA: String,
        optB: String,
        optC: String,
        optD: String
    ): Int {
        val clean = ansText.uppercase().trim().replace("(", "").replace(")", "").replace(".", "")

        return when {
            clean.startsWith("A") || clean.startsWith("1") || clean.startsWith("क") -> 1
            clean.startsWith("B") || clean.startsWith("2") || clean.startsWith("ख") -> 2
            clean.startsWith("C") || clean.startsWith("3") || clean.startsWith("ग") -> 3
            clean.startsWith("D") || clean.startsWith("4") || clean.startsWith("घ") -> 4
            // Match against actual option text if provided as answer
            optA.isNotBlank() && optA.contains(ansText, ignoreCase = true) -> 1
            optB.isNotBlank() && optB.contains(ansText, ignoreCase = true) -> 2
            optC.isNotBlank() && optC.contains(ansText, ignoreCase = true) -> 3
            optD.isNotBlank() && optD.contains(ansText, ignoreCase = true) -> 4
            else -> 1
        }
    }

    /**
     * Pre-populated sample lecture notes & quiz questions for testing and instant preview
     */
    fun getSampleContent(): String {
        return """
# इकाई 1: पुस्तकालय विज्ञान का आधार (Foundation of Library Science)
- डॉ. एस. आर. रंगनाथन को भारत में पुस्तकालय विज्ञान का जनक (Father of Library Science in India) माना जाता है।
- रंगनाथन जी ने 1928 में मीनाक्षी कॉलेज, अन्नामलाई नगर में पुस्तकालय विज्ञान के 5 सूत्रों (Five Laws of Library Science) का प्रतिपादन किया।
- यह पांच सूत्र 1931 में मद्रास लाइब्रेरी एसोसिएशन द्वारा पुस्तक रूप में प्रकाशित हुए।
- डिलीवरी ऑफ बुक्स (पब्लिक लाइब्रेरीज) एक्ट 1954 में पारित हुआ था। वर्ष 1956 में संशोधन कर इसमें समाचार पत्रों और पत्रिकाओं को भी सम्मिलित किया गया।
- डिलीवरी ऑफ बुक्स एक्ट के तहत प्रत्येक प्रकाशक को भारत के 4 राष्ट्रीय धरोहर पुस्तकालयों (नेशनल लाइब्रेरी कोलकाता, कोन्निमेरा चेन्नई, एशियाटिक सोसाइटी मुंबई, दिल्ली पब्लिक लाइब्रेरी) में पुस्तक की 1-1 प्रति 30 दिनों के भीतर निःशुल्क भेजनी होती है।

--- क्विज़ प्रश्न एवं उत्तर (Quiz Questions & Answers) ---

प्र. 1. पुस्तकालय विज्ञान के पांच सूत्रों (Five Laws of Library Science) का प्रतिपादन किसने किया था?
(A) डॉ. एस. आर. रंगनाथन (Dr. S.R. Ranganathan)
(B) मेलविल डेवी (Melvil Dewey)
(C) सी. ए. कटर (C.A. Cutter)
(D) डब्ल्यू. सी. सेयर्स (W.C. Berwick Sayers)
उत्तर: (A)
व्याख्या: डॉ. एस.आर. रंगनाथन ने 1928 में इन सूत्रों का प्रतिपादन किया तथा 1931 में यह पुस्तक के रूप में प्रकाशित हुई।

प्र. 2. भारत में 'डिलीवरी ऑफ बुक्स एक्ट' किस वर्ष संसद द्वारा पारित किया गया था?
(A) 1950
(B) 1954
(C) 1956
(D) 1962
उत्तर: (B)
व्याख्या: 1954 में यह अधिनियम पारित हुआ, तथा 1956 में इसमें संशोधन करके समाचार पत्रों को भी शामिल किया गया।

प्र. 3. पुस्तकालय विज्ञान का कौन-सा सूत्र कहता है कि "पुस्तकालय एक वर्धनशील संस्था है" (Library is a Growing Organism)?
(A) प्रथम सूत्र (First Law)
(B) तृतीय सूत्र (Third Law)
(C) पंचम सूत्र (Fifth Law)
(D) द्वितीय सूत्र (Second Law)
उत्तर: (C)
व्याख्या: पंचम सूत्र (Fifth Law) पुस्तकालय को एक जीवंत एवं वर्धनशील संस्था के रूप में परिभाषित करता है।
        """.trimIndent()
    }

    /**
     * Sample extra questions without notes for Unit 6 (Extra Questions)
     */
    fun getExtraQuestionsSample(): String {
        return """
प्र. 1. यूनेस्को (UNESCO) द्वारा पब्लिक लाइब्रेरी मेनिफेस्टो सर्वप्रथम किस वर्ष जारी किया गया था?
(A) 1949
(B) 1972
(C) 1994
(D) 1954
उत्तर: (A)
व्याख्या: यूनेस्को द्वारा प्रथम पब्लिक लाइब्रेरी मेनिफेस्टो 1949 में जारी किया गया था और 1972 तथा 1994 में इसे संशोधित किया गया।

प्र. 2. भारत में पुस्तकालय आंदोलन (Library Movement in India) के जनक के रूप में किसे जाना जाता है?
(A) सयाजीराव गायकवाड़ तृतीय
(B) डब्ल्यू. ए. बोर्डेन
(C) डॉ. एस. आर. रंगनाथन
(D) कुमार मुनींद्र देव राय
उत्तर: (A)
व्याख्या: बड़ौदा नरेश महाराजा सयाजीराव गायकवाड़ III ने 1910 में भारत में पुस्तकालय आंदोलन की शुरुआत की थी।

प्र. 3. राजा राममोहन राय लाइब्रेरी फाउंडेशन (RRRLF) का मुख्यालय कहाँ स्थित है?
(A) नई दिल्ली
(B) कोलकाता
(C) मुंबई
(D) चेन्नई
उत्तर: (B)
व्याख्या: RRRLF की स्थापना मई 1972 में कोलकाता में की गई थी, जो भारत में सार्वजनिक पुस्तकालयों के विकास हेतु सर्वोच्च निकाय है।
        """.trimIndent()
    }
}
