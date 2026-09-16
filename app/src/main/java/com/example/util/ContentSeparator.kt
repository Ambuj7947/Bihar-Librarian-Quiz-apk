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
            Regex("(?:https?://)?(?:www\\.)?m\\.youtube\\.com/watch\\?(?:.*&)?v=([a-zA-Z0-9_-]{11})"),
            Regex("(?:https?://)?(?:www\\.)?youtube\\.com/live/([a-zA-Z0-9_-]{11})")
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

        // 1. Check for explicit delimiters (e.g., [Notes] ... [Questions] or --- or क्विज़/प्रश्न or 2. वस्तुनिष्ठ प्रश्नोत्तरी)
        val questionsHeaderRegex = Regex(
            "(?i)^\\s*(?:---+|===+|###|##|#|[0-9]+[\\.\\)])?\\s*(?:\\[?(?:questions?|quiz|mcqs?|q&a|प्रश्नोत्तरी|महत्वपूर्ण प्रश्न|अभ्यास प्रश्न|बहुविकल्पीय प्रश्न|वस्तुनिष्ठ प्रश्न|वस्तुनिष्ठ प्रश्नोत्तरी|multiple choice questions)[^\n]*\\]?)\\s*(?:---+|===+)?\\s*$",
            RegexOption.IGNORE_CASE
        )

        val notesHeaderRegex = Regex(
            "(?i)^\\s*(?:---+|===+|###|##|#|[0-9]+[\\.\\)])?\\s*(?:\\[?(?:notes?|theory|study material|lecture notes|अध्ययन सामग्री|नोट्स|मुख्य बिंदु|थ्योरी|मुख्य अवधारणाओं का विस्तृत सारांश|core concepts summary)[^\n]*\\]?)\\s*(?:---+|===+)?\\s*$",
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

        val cleanNotesLines = notesLines.filter { line ->
            val t = line.trim()
            t.isNotEmpty() &&
            !t.startsWith("इस वीडियो", ignoreCase = true) &&
            !t.startsWith("यह वीडियो", ignoreCase = true) &&
            !t.startsWith("YouTube video", ignoreCase = true) &&
            !t.contains("Term of Service", ignoreCase = true)
        }
        val finalNotes = cleanNotesLines.joinToString("\n").trim()
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

        val answerKeyHeaderRegex = Regex("(?i)^\\s*(?:उत्तर कुंजी|answer\\s*key|उत्तरमाला|उत्तर तालिका).*", RegexOption.IGNORE_CASE)
        val answerKeyIndex = lines.indexOfFirst { answerKeyHeaderRegex.containsMatchIn(it) }

        val rawQuestionLines = if (answerKeyIndex != -1) lines.subList(0, answerKeyIndex) else lines
        val rawAnswerKeyLines = if (answerKeyIndex != -1) lines.subList(answerKeyIndex + 1, lines.size) else emptyList()

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
                val cleanQ = currentQuestionText.trim()
                var a = optA.trim()
                var b = optB.trim()
                var c = optC.trim()
                var d = optD.trim()

                if (a.isEmpty() && b.isEmpty()) {
                    val fallbackAnswer = explanation.ifBlank { "सही उत्तर" }
                    val distractors = libraryDistractors.shuffled().filter { it != fallbackAnswer }.take(3)
                    a = fallbackAnswer
                    b = distractors.getOrNull(0) ?: "विकल्प B"
                    c = distractors.getOrNull(1) ?: "विकल्प C"
                    d = distractors.getOrNull(2) ?: "विकल्प D"
                    correctOption = 1
                } else if (c.isEmpty() && d.isEmpty()) {
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

        for (rawLine in rawQuestionLines) {
            val line = rawLine.trim()
            if (line.isEmpty()) continue

            // Strip leading bullet markers (*, -, •)
            val lineCleanBullet = line.replace(Regex("^[\\*\\-\\•\\–\\—]\\s*"), "").trim()

            val ansMatch = answerRegex.find(lineCleanBullet)
            if (ansMatch != null) {
                val ansVal = ansMatch.groupValues[1].trim()
                correctOption = parseAnswerToOptionIndex(ansVal, optA, optB, optC, optD)
                continue
            }

            val expMatch = expRegex.find(lineCleanBullet)
            if (expMatch != null) {
                val expVal = expMatch.groupValues[1].trim()
                explanation = if (explanation.isEmpty()) expVal else "$explanation $expVal"
                continue
            }

            val aMatch = optARegex.find(lineCleanBullet)
            if (aMatch != null) {
                optA = aMatch.groupValues[1].trim()
                inOptions = true
                continue
            }

            val bMatch = optBRegex.find(lineCleanBullet)
            if (bMatch != null) {
                optB = bMatch.groupValues[1].trim()
                inOptions = true
                continue
            }

            val cMatch = optCRegex.find(lineCleanBullet)
            if (cMatch != null) {
                optC = cMatch.groupValues[1].trim()
                inOptions = true
                continue
            }

            val dMatch = optDRegex.find(lineCleanBullet)
            if (dMatch != null) {
                optD = dMatch.groupValues[1].trim()
                inOptions = true
                continue
            }

            if (isQuestionStartLine(lineCleanBullet)) {
                saveCurrentQuestionIfValid()
                // Strip leading question markers
                currentQuestionText = cleanQuestionPrefix(lineCleanBullet)
                inOptions = false
            } else if (!inOptions) {
                // Continuation of question text or question text on line following question number
                if (currentQuestionText.isEmpty()) {
                    currentQuestionText = lineCleanBullet
                } else {
                    currentQuestionText += " $lineCleanBullet"
                }
            } else if (inOptions && explanation.isNotEmpty()) {
                // Continuation of explanation
                explanation += " $lineCleanBullet"
            }
        }

        saveCurrentQuestionIfValid()

        // Apply answer key if found at the end of text
        val answerKeyMap = parseAnswerKey(rawAnswerKeyLines)
        if (answerKeyMap.isNotEmpty()) {
            for (i in questions.indices) {
                val qNum = i + 1
                answerKeyMap[qNum]?.let { (opt, exp) ->
                    val existing = questions[i]
                    questions[i] = existing.copy(
                        correctOption = opt,
                        explanationHindi = exp.ifBlank { existing.explanationHindi }
                    )
                }
            }
        }

        return questions
    }

    /**
     * Parses an Answer Key section that lists question numbers, correct options, and explanations
     */
    private fun parseAnswerKey(lines: List<String>): Map<Int, Pair<Int, String>> {
        val result = mutableMapOf<Int, Pair<Int, String>>()
        if (lines.isEmpty()) return result

        val filteredLines = lines.map { it.trim() }.filter { line ->
            line.isNotEmpty() &&
            !line.startsWith("YouTube video views will be stored", ignoreCase = true) &&
            !line.startsWith("YouTube video", ignoreCase = true) &&
            !line.contains("Term of Service", ignoreCase = true) &&
            !line.startsWith("यह संपूर्ण विश्लेषण", ignoreCase = true) &&
            !line.startsWith("यह विश्लेषण", ignoreCase = true) &&
            !Regex("^(?:प्रश्न संख्या|सही उत्तर|सही विकल्प|संक्षिप्त व्याख्या|क्रमांक|उत्तर|व्याख्या|s\\.no|q\\.no|answer|explanation)$", RegexOption.IGNORE_CASE).matches(line)
        }

        var currentQNum: Int? = null
        var currentOpt: Int? = null
        var currentExp = ""

        fun saveEntry() {
            val q = currentQNum
            val opt = currentOpt
            if (q != null && opt != null) {
                result[q] = Pair(opt, currentExp.trim())
            }
            currentQNum = null
            currentOpt = null
            currentExp = ""
        }

        val singleLineRegex = Regex("^[\\#\\*\\-]?\\s*([0-9]+)[\\.\\:\\-\\t\\s]+\\(?([A-Da-d1-4क-घ])\\)?[\\.\\:\\-\\t\\s]*(.*)")

        for (line in filteredLines) {
            val singleMatch = singleLineRegex.find(line)
            if (singleMatch != null) {
                saveEntry()
                val qNum = singleMatch.groupValues[1].toIntOrNull() ?: continue
                val optChar = singleMatch.groupValues[2]
                val exp = singleMatch.groupValues[3].trim()
                val opt = parseAnswerToOptionIndex(optChar, "", "", "", "")
                result[qNum] = Pair(opt, exp)
                continue
            }

            val qNumOnlyMatch = Regex("^(?:प्रश्न\\s*)?([0-9]+)[\\.\\)]?$").find(line)
            if (qNumOnlyMatch != null) {
                saveEntry()
                currentQNum = qNumOnlyMatch.groupValues[1].toIntOrNull()
                continue
            }

            val optOnlyMatch = Regex("^\\(?([A-Da-d1-4क-घ])\\)?$").find(line)
            if (optOnlyMatch != null && currentQNum != null && currentOpt == null) {
                val optChar = optOnlyMatch.groupValues[1]
                currentOpt = parseAnswerToOptionIndex(optChar, "", "", "", "")
                continue
            }

            if (currentQNum != null && currentOpt != null) {
                currentExp = if (currentExp.isEmpty()) line else "$currentExp $line"
            }
        }
        saveEntry()
        return result
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
     * Format a QuestionEntity into clean quiz text
     */
    fun formatQuestionToQuizText(q: QuestionEntity, index: Int): String {
        val optLetter = when (q.correctOption) {
            1 -> "(A)"
            2 -> "(B)"
            3 -> "(C)"
            4 -> "(D)"
            else -> "(A)"
        }
        return "प्रश्न $index: ${q.questionHindi}\n(A) ${q.optionA}\n(B) ${q.optionB}\n(C) ${q.optionC}\n(D) ${q.optionD}\nउत्तर: $optLetter\nव्याख्या: ${q.explanationHindi}"
    }

    /**
     * Complete attached content for Unit 1 Subtopic 1: पुस्तकालय की बेसिक अवधारणा
     */
    fun getUnit1Subtopic1FullContent(): String {
        return """
इस वीडियो "Bihar Librarian LET 2026 | UNIT-1 Basic Concepts of Library | पुस्तकालय की मूलभूत अवधारणा" (डॉ. सैयद फहीम अली द्वारा प्रस्तुत) पर आधारित मुख्य अवधारणाओं का विस्तृत सारांश और बहुविकल्पीय प्रश्नोत्तरी (Quiz) नीचे दी गई है:

1. मुख्य अवधारणाओं का विस्तृत सारांश (Core Concepts Summary)
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

2. वस्तुनिष्ठ प्रश्नोत्तरी (Multiple Choice Questions)

प्रश्न 1
अंग्रेजी भाषा का 'Library' शब्द किस भाषा के मूल शब्द 'Liber' से व्युत्पन्न हुआ है?
* (A) ग्रीक (Greek)
* (B) लैटिन (Latin)
* (C) फ्रेंच (French)
* (D) जर्मन (German)

प्रश्न 2
'Liber' शब्द का मूल शाब्दिक अर्थ क्या होता है?
* (A) कागज का पन्ना
* (B) पेड़ की छाल (Inner Bark)
* (C) लोहे की पट्टिका
* (D) स्याही

प्रश्न 3
'Library' शब्द लैटिन के 'Liber' से किस फ्रेंच शब्द के माध्यम से अंग्रेजी में आया?
* (A) Liberte
* (B) Librairie
* (C) Libris
* (D) Libre

प्रश्न 4
हिंदी भाषा में 'पुस्तकालय' शब्द किन दो शब्दों के संधि-योग से बना है?
* (A) पुस्तक + लय
* (B) पुस्तक + आलय
* (C) पुस्तिका + लय
* (D) पुस्त + कालय

प्रश्न 5
डॉ. एस. आर. रंगनाथन के अनुसार पुस्तकालय की 'त्रिमूर्ति' (Trinity of Library) में कौन-से तीन घटक शामिल हैं?
* (A) पुस्तक, भवन और फर्नीचर
* (B) पाठक, पुस्तक और कर्मचारी (Librarian)
* (C) कंप्यूटर, इंटरनेट और सॉफ्टवेयर
* (D) बजट, प्रबंधन और पाठक

प्रश्न 6
भारत में "पुस्तकालय विज्ञान के जनक" (Father of Library Science in India) के रूप में किन्हें जाना जाता है?
* (A) डब्ल्यू. ए. बोर्डन
* (B) सी. ए. कटर
* (C) डॉ. एस. आर. रंगनाथन
* (D) बी. एस. केशवन

प्रश्न 7
विश्व स्तर पर प्रथम 'स्कूल ऑफ लाइब्रेरी इकोनॉमी' (1887) की स्थापना किसके द्वारा की गई थी?
* (A) मेलविल डेवी
* (B) चार्ल्स एमी कटर
* (C) हेनरी ला फोंटेन
* (D) पॉल ओटलेट

प्रश्न 8
पुस्तकालय के आधुनिक दृष्टिकोण के संबंध में कौन-सा कथन सर्वाधिक उपयुक्त है?
* (A) यह केवल दुर्लभ पुस्तकों का संग्रहालय है।
* (B) यह केवल धनवान वर्ग के लिए अध्ययन कक्ष है।
* (C) यह सूचना प्रसार और निरंतर स्वाध्याय का एक सक्रिय सामाजिक केंद्र है।
* (D) यह केवल परीक्षा की तैयारी के लिए बैठने का स्थान है।

प्रश्न 9
डॉ. एस. आर. रंगनाथन द्वारा प्रतिपादित 'पुस्तकालय विज्ञान के पाँच सूत्र' (Five Laws of Library Science) का मुख्य उद्देश्य क्या है?
* (A) पुस्तकों की बिक्री बढ़ाना
* (B) पुस्तकालय प्रबंधन और सेवाओं को अधिकतम पाठक-उन्मुख बनाना
* (C) केवल कर्मचारियों की संख्या सीमित करना
* (D) पुस्तकालय को बंद रखना

प्रश्न 10
विश्वविद्यालय और कॉलेज पुस्तकालय किस श्रेणी के अंतर्गत आते हैं?
* (A) सार्वजनिक पुस्तकालय (Public Library)
* (B) शैक्षणिक पुस्तकालय (Academic Library)
* (C) विशिष्ट पुस्तकालय (Special Library)
* (D) राष्ट्रीय पुस्तकालय (National Library)

उत्तर कुंजी (Answer Key)
प्रश्न संख्या	सही उत्तर	संक्षिप्त व्याख्या
1	(B)	'Library' शब्द लैटिन भाषा के मूल शब्द 'Liber' से निकला है।
2	(B)	प्राचीन काल में 'Liber' का अर्थ पेड़ की भीतरी छाल होता था, जिस पर लिखा जाता था।
3	(B)	लैटिन के 'Liber' से फ्रेंच शब्द 'Librairie' बना, जिससे अंग्रेजी शब्द 'Library' आया।
4	(B)	पुस्तक + आलय = पुस्तकालय (अर्थात पुस्तकों को सहेजने और पढ़ने का स्थान)।
5	(B)	पुस्तकालय के तीन अनिवार्य अंग पाठक, पुस्तक और कर्मचारी माने गए हैं।
6	(C)	भारत में पुस्तकालय आंदोलन और शिक्षा के प्रणेता डॉ. एस. आर. रंगनाथन हैं।
7	(A)	मेलविल डेवी ने 1887 में कोलंबिया कॉलेज में पुस्तकालय शिक्षा का पहला संस्थान स्थापित किया था।
8	(C)	आधुनिक पुस्तकालय केवल पुस्तकों का संग्रह न होकर सूचना व ज्ञान के प्रसार का सामाजिक केंद्र है।
9	(B)	पाँचों सूत्र पुस्तकालय सेवाओं, पाठकों के समय की बचत और अधिकतम उपयोग को सुनिश्चित करते हैं।
10	(B)	स्कूल, कॉलेज और विश्वविद्यालय पुस्तकालय Academic Libraries (शैक्षणिक पुस्तकालय) कहलाते हैं।
        """.trimIndent()
    }

    /**
     * Pre-populated Unit 1 Subtopic 2 (पुस्तकालय के प्रकार) content
     */
    fun getUnit1Subtopic2FullContent(): String {
        return """
1. मुख्य अवधारणाओं का विस्तृत सारांश (Core Concepts Summary)

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

2. वस्तुनिष्ठ प्रश्नोत्तरी (Multiple Choice Quiz)

प्रश्न 1
पुस्तकालयों को उनके कार्य और पाठक वर्ग के आधार पर सामान्यतः कितने मुख्य प्रकारों में वर्गीकृत किया जाता है?
* (A) दो
* (B) तीन
* (C) चार
* (D) छह

प्रश्न 2
निम्नलिखित में से किसे "जनता का विश्वविद्यालय" (People's University) की संज्ञा दी गई है?
* (A) राष्ट्रीय पुस्तकालय
* (B) शैक्षणिक पुस्तकालय
* (C) विशिष्ट पुस्तकालय
* (D) सार्वजनिक पुस्तकालय

प्रश्न 3
शैक्षणिक पुस्तकालय (Academic Library) के अंतर्गत निम्नलिखित में से कौन-सा शामिल नहीं है?
* (A) स्कूल पुस्तकालय
* (B) अनुसंधान संस्थान पुस्तकालय (Research Institute Library)
* (C) कॉलेज पुस्तकालय
* (D) विश्वविद्यालय पुस्तकालय

प्रश्न 4
"पुस्तकालय किसी भी विश्वविद्यालय का हृदय स्थल होता है" (Library is the heart of the university) — यह विचार मुख्यतः किस आयोग/सिद्धांत से जुड़ा है?
* (A) मुदालियर आयोग
* (B) राधाकृष्णन आयोग (विश्वविद्यालय शिक्षा आयोग)
* (C) हंटर आयोग
* (D) कोठारी आयोग

प्रश्न 5
यूनेस्को (UNESCO) द्वारा 'सार्वजनिक पुस्तकालय घोषणापत्र' (Public Library Manifesto) सर्वप्रथम किस वर्ष जारी किया गया था?
* (A) 1931
* (B) 1949
* (C) 1972
* (D) 1994

प्रश्न 6
किसी विशिष्ट विषय (जैसे—चिकित्सा, कृषि, अंतरिक्ष विज्ञान) या विशेष संस्थान के वैज्ञानिकों/शोधार्थियों को सेवा प्रदान करने वाले पुस्तकालय को क्या कहा जाता है?
* (A) सार्वजनिक पुस्तकालय
* (B) विशिष्ट पुस्तकालय (Special Library)
* (C) राष्ट्रीय पुस्तकालय
* (D) मोबाइल पुस्तकालय

प्रश्न 7
भारत का राष्ट्रीय पुस्तकालय (National Library of India) कहाँ स्थित है?
* (A) नई दिल्ली
* (B) मुंबई
* (C) कोलकाता
* (D) चेन्नई

प्रश्न 8
डिलीवरी ऑफ बुक्स एक्ट (Delivery of Books Act) भारत में किस वर्ष पारित किया गया था?
* (A) 1948
* (B) 1951
* (C) 1954
* (D) 1962

प्रश्न 9
सार्वजनिक पुस्तकालयों के संचालन एवं विकास हेतु सरकार द्वारा एकत्र किए जाने वाले कर को क्या कहा जाता है?
* (A) व्यावसायिक कर
* (B) पुस्तकालय उपकर (Library Cess)
* (C) संपत्ति अधिभार
* (D) मनोरंजन कर

प्रश्न 10
विशिष्ट पुस्तकालयों (Special Libraries) में सामान्यतः किस प्रकार की अध्ययन सामग्री का संग्रह सबसे अधिक महत्वपूर्ण माना जाता है?
* (A) प्राथमिक विद्यालयी पाठ्यपुस्तकें
* (B) दैनिक समाचार पत्र एवं पत्रिकाएँ
* (C) शोध पत्रिकाएँ, तकनीकी रिपोर्ट्स और पेटेंट्स
* (D) उपन्यास एवं कहानियों की पुस्तकें

उत्तर कुंजी (Answer Key)
प्रश्न संख्या
	सही विकल्प
	संक्षिप्त व्याख्या
	1
	(C)
	पुस्तकालयों को मुख्यतः 4 वर्गों—शैक्षणिक, सार्वजनिक, विशिष्ट और राष्ट्रीय पुस्तकालय में बांटा जाता है।
	2
	(D)
	सार्वजनिक पुस्तकालय बिना किसी भेद के सभी नागरिकों के निरंतर स्व-अध्ययन का केंद्र होता है, इसलिए इसे "People's University" कहते हैं।
	3
	(B)
	अनुसंधान संस्थान पुस्तकालय 'विशिष्ट पुस्तकालय' (Special Library) के अंतर्गत आता है, शैक्षणिक नहीं।
	4
	(B)
	डॉ. सर्वपल्ली राधाकृष्णन की अध्यक्षता वाले विश्वविद्यालय शिक्षा आयोग (1948-49) ने पुस्तकालय को विश्वविद्यालय का हृदय बताया था।
	5
	(B)
	यूनेस्को ने सार्वजनिक पुस्तकालयों के मार्गदर्शक सिद्धांतों हेतु पहला मैनिफेस्टो 1949 में जारी किया था।
	6
	(B)
	किसी खास विषय क्षेत्र या शोध संस्थान हेतु कार्य करने वाले पुस्तकालय Special Libraries कहलाते हैं।
	7
	(C)
	भारत का राष्ट्रीय पुस्तकालय कोलकाता के बेलवेडियर एस्टेट (Belvedere Estate) में स्थित है।
	8
	(C)
	डिलीवरी ऑफ बुक्स एक्ट 1954 में पारित हुआ (तथा 1956 में इसमें समाचार पत्रों को शामिल किया गया)।
	9
	(B)
	कई राज्यों के सार्वजनिक पुस्तकालय अधिनियमों में पुस्तकालयों के वित्तीय पोषण हेतु Library Cess (पुस्तकालय उपकर) का प्रावधान है।
	10
	(C)
	विशिष्ट पुस्तकालयों में नवीनतम अनुसंधान, जर्नल्स, तकनीकी रिपोर्ट्स और पेटेंट सबसे प्राथमिक सामग्री होते हैं।
        """.trimIndent()
    }

    /**
     * Pre-populated Unit 1 Subtopic 3 (सार्वजनिक लाइब्रेरी / Public Library) content
     */
    fun getUnit1Subtopic3FullContent(): String {
        return """
1. मुख्य अवधारणाओं का विस्तृत सारांश (Core Concepts Summary)

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

2. वस्तुनिष्ठ प्रश्नोत्तरी (Multiple Choice Quiz)

प्रश्न 1
सार्वजनिक पुस्तकालय (Public Library) की सबसे प्रमुख विशेषता क्या है?
* (A) केवल पंजीकृत विद्यार्थियों के लिए सीमित प्रवेश
* (B) जाति, धर्म, लिंग और सामाजिक स्थिति के भेदभाव के बिना सभी नागरिकों के लिए खुला होना
* (C) केवल सरकारी अधिकारियों के लिए दस्तावेज उपलब्ध कराना
* (D) पुस्तकों का व्यावसायिक विक्रय करना

प्रश्न 2
सार्वजनिक पुस्तकालय को अनौपचारिक शिक्षा का माध्यम होने के कारण किस संज्ञा से अभिहित किया जाता है?
* (A) राष्ट्रीय अभिलेखागार
* (B) विशिष्ट शोध केंद्र
* (C) जनता का विश्वविद्यालय (People's University)
* (D) शैक्षणिक संकुल

प्रश्न 3
यूनेस्को (UNESCO) ने सार्वजनिक पुस्तकालय घोषणापत्र (Public Library Manifesto) पहली बार किस वर्ष जारी किया था?
* (A) 1931
* (B) 1949
* (C) 1972
* (D) 1994

प्रश्न 4
स्वतंत्र भारत में सबसे पहला 'सार्वजनिक पुस्तकालय अधिनियम' (Public Library Act) किस राज्य में पारित किया गया था?
* (A) बिहार (1950)
* (B) मद्रास / तमिलनाडु (1948)
* (C) आंध्र प्रदेश (1960)
* (D) कर्नाटक (1965)

प्रश्न 5
बिहार राज्य में 'बिहार राज्य सार्वजनिक पुस्तकालय एवं सूचना केंद्र अधिनियम' किस वर्ष लागू/पारित हुआ?
* (A) 1989
* (B) 2002
* (C) 2008
* (D) 2015

प्रश्न 6
सार्वजनिक पुस्तकालयों के विकास एवं नियमित वित्तीय पोषण हेतु सरकार द्वारा लगाया जाने वाला विशेष कर क्या कहलाता है?
* (A) मनोरंजन कर
* (B) पुस्तकालय उपकर (Library Cess)
* (C) शिक्षा उपकर
* (D) सेवा कर

प्रश्न 7
राजा राममोहन राय लाइब्रेरी फाउंडेशन (RRRLF) की स्थापना किस वर्ष और कहाँ की गई थी?
* (A) 1972, कोलकाता
* (B) 1954, नई दिल्ली
* (C) 1948, मद्रास
* (D) 1985, मुंबई

प्रश्न 8
RRRLF (राजा राममोहन राय पुस्तकालय प्रतिष्ठान) भारत सरकार के किस मंत्रालय के अधीन एक स्वायत्त संस्था के रूप में कार्य करता है?
* (A) शिक्षा मंत्रालय (Ministry of Education)
* (B) संस्कृति मंत्रालय (Ministry of Culture)
* (C) सूचना एवं प्रसारण मंत्रालय
* (D) गृह मंत्रालय

प्रश्न 9
भारत में अब तक कुल कितने राज्यों में सार्वजनिक पुस्तकालय विधान (Public Library Acts) पारित किए जा चुके हैं?
* (A) 12 राज्यों में
* (B) 16 राज्यों में
* (C) 19 राज्यों में
* (D) 28 राज्यों में

प्रश्न 10
सार्वजनिक पुस्तकालय के सुचारू संचालन और समाज के सभी वर्गों तक उसकी पहुंच सुनिश्चित करने के लिए सबसे प्रभावी कानूनी उपाय क्या है?
* (A) निजी दानदाताओं पर पूर्ण निर्भरता
* (B) पुस्तकालय विधान (Library Legislation) का निर्माण एवं क्रियान्वयन
* (C) सदस्यता शुल्क में अत्यधिक वृद्धि
* (D) केवल बड़े शहरों तक सीमित रखना

उत्तर कुंजी (Answer Key)
प्रश्न संख्या
	सही विकल्प
	संक्षिप्त व्याख्या
	1
	(B)
	सार्वजनिक पुस्तकालय बिना किसी पूर्वाग्रह व भेदभाव के समाज के हर वर्ग के लिए निःशुल्क या सुलभ होता है।
	2
	(C)
	समाज के प्रत्येक आयु वर्ग को निरंतर स्वाध्याय का अवसर देने के कारण इसे "People's University" कहा जाता है।
	3
	(B)
	यूनेस्को ने सार्वजनिक पुस्तकालयों के अंतरराष्ट्रीय मार्गदर्शक सिद्धांतों हेतु पहला मैनिफेस्टो 1949 में जारी किया था।
	4
	(B)
	डॉ. एस. आर. रंगनाथन के मसौदे पर मद्रास पब्लिक लाइब्रेरी एक्ट (1948) स्वतंत्र भारत का पहला अधिनियम था।
	5
	(C)
	बिहार में पुस्तकालय अधिनियम वर्ष 2008 में अधिनियमित हुआ, जो राज्य में सार्वजनिक पुस्तकालय व्यवस्था की कानूनी रीढ़ है।
	6
	(B)
	अधिनियमित राज्यों में सार्वजनिक पुस्तकालयों की आय हेतु संपत्ति/गृह कर पर Library Cess (पुस्तकालय उपकर) लगाया जाता है।
	7
	(A)
	RRRLF की स्थापना मई 1972 में कोलकाता (पश्चिम बंगाल) में हुई थी।
	8
	(B)
	RRRLF भारत सरकार के संस्कृति मंत्रालय (Ministry of Culture) के अधीन कार्य करता है।
	9
	(C)
	भारत में वर्तमान में कुल 19 राज्यों ने अपने सार्वजनिक पुस्तकालय कानून लागू किए हैं।
	10
	(B)
	पुस्तकालय विधान (Library Legislation) पुस्तकालयों को वैधानिक संरक्षण, स्थायी वित्तीय स्रोत और प्रशासनिक ढांचा प्रदान करता है।
        """.trimIndent()
    }

    /**
     * Pre-populated Unit 1 Subtopic 4 (भारत के राष्ट्रीय लाइब्रेरी / National Library of India) content
     */
    fun getUnit1Subtopic4FullContent(): String {
        return """
1. मुख्य अवधारणाओं का विस्तृत सारांश (Core Concepts Summary)

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
   * स्वतंत्र भारत के राष्ट्रीय पुस्तकालय के प्रथम लाइब्रेरियन: बी. एस. केशवन (B. S. केशवन), 1947–1962 (इन्हें INB का जनक कहा जाता है)।
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

2. वस्तुनिष्ठ प्रश्नोत्तरी (Multiple Choice Quiz)

प्रश्न 1
भारतीय राष्ट्रीय पुस्तकालय (National Library of India) का उल्लेख भारतीय संविधान की किस अनुसूची एवं अनुच्छेद के अंतर्गत 'राष्ट्रीय महत्व की संस्था' के रूप में मिलता है?
* (A) 8वीं अनुसूची, अनुच्छेद 343
* (B) 7वीं अनुसूची, अनुच्छेद 62
* (C) 6वीं अनुसूची, अनुच्छेद 51A
* (D) 9वीं अनुसूची, अनुच्छेद 31

प्रश्न 2
कलकत्ता पब्लिक लाइब्रेरी (Calcutta Public Library) की स्थापना किस तिथि को हुई थी?
* (A) 21 मार्च 1836
* (B) 15 अगस्त 1891
* (C) 30 जनवरी 1903
* (D) 1 फरवरी 1953

प्रश्न 3
कलकत्ता पब्लिक लाइब्रेरी के प्रमुख संस्थापक एवं प्रथम प्रोप्राइटर कौन थे?
* (A) राजा राममोहन राय
* (B) ईश्वरचंद्र विद्यासागर
* (C) द्वारकानाथ टैगोर
* (D) बंकिम चंद्र चटर्जी

प्रश्न 4
किस वायसराय के प्रयासों से कलकत्ता पब्लिक लाइब्रेरी और इंपीरियल लाइब्रेरी का विलय कर 1902 में 'इंपीरियल लाइब्रेरी एक्ट' पारित किया गया?
* (A) लॉर्ड विलियम बेंटिंक
* (B) लॉर्ड डलहौजी
* (C) लॉर्ड कर्जन
* (D) लॉर्ड माउंटबेटन

प्रश्न 5
इंपीरियल लाइब्रेरी को मेटकाफ हॉल में आम जनता के अध्ययन हेतु किस तिथि को खोला गया था?
* (A) 21 मार्च 1836
* (B) 30 जनवरी 1903
* (C) 15 अगस्त 1947
* (D) 26 जनवरी 1950

प्रश्न 6
इंपीरियल लाइब्रेरी के प्रथम लाइब्रेरियन (Head Librarian) कौन नियुक्त किए गए थे?
* (A) चार्ल्स मेटकाफ
* (B) जॉन मैकफर्लेन
* (C) बी. एस. केशवन
* (D) डॉ. एस. आर. रंगनाथन

प्रश्न 7
इंपीरियल लाइब्रेरी के पद पर नियुक्त होने वाले प्रथम भारतीय (First Indian Librarian) कौन थे?
* (A) हरिनाथ डे
* (B) के. एम. असदुल्लाह
* (C) बी. एस. केशवन
* (D) आर. के. दासगुप्ता

प्रश्न 8
स्वतंत्र भारत के राष्ट्रीय पुस्तकालय (National Library of India) के प्रथम लाइब्रेरियन कौन बने, जिन्हें "फादर ऑफ INB" भी कहा जाता है?
* (A) पी. एन. कौला
* (B) बी. एस. केशवन
* (C) एस. बशीरुद्दीन
* (D) डॉ. एस. आर. रंगनाथन

प्रश्न 9
स्वतंत्रता के पश्चात 1 फरवरी 1953 को भारत के राष्ट्रीय पुस्तकालय का विधिवत उद्घाटन किसके द्वारा किया गया था?
* (A) डॉ. राजेंद्र प्रसाद
* (B) पंडित जवाहरलाल नेहरू
* (C) मौलाना अबुल कलाम आज़ाद
* (D) डॉ. सर्वपल्ली राधाकृष्णन

प्रश्न 10
'इंडियन नेशनल बिब्लियोग्राफी' (INB) का प्रथम संस्करण किस ऐतिहासिक तिथि को प्रकाशित किया गया था?
* (A) 26 जनवरी 1950
* (B) 15 अगस्त 1958
* (C) 2 अक्टूबर 1962
* (D) 1 जनवरी 1954

प्रश्न 11
वर्तमान में 'इंडियन नेशनल बिब्लियोग्राफी' (INB) की प्रकाशन आवृत्ति (Frequency) क्या है?
* (A) साप्ताहिक (Weekly)
* (B) पाक्षिक (Fortnightly)
* (C) मासिक (Monthly)
* (D) त्रैमासिक (Quarterly)

प्रश्न 12
भारतीय राष्ट्रीय पुस्तकालय वर्ष में कुल कितने दिन पाठकों के उपयोग हेतु खुला रहता है?
* (A) 300 दिन
* (B) 350 दिन
* (C) 362 दिन
* (D) 365 दिन

प्रश्न 13
भारतीय राष्ट्रीय पुस्तकालय वर्ष में किन तीन राष्ट्रीय अवकाशों पर पूर्णतः बंद रहता है?
* (A) होली, दिवाली, ईद
* (B) 26 जनवरी, 15 अगस्त, 2 अक्टूबर
* (C) 1 जनवरी, 15 अगस्त, 25 दिसंबर
* (D) बुद्ध पूर्णिमा, महावीर जयंती, गुरु नानक जयंती

प्रश्न 14
भारतीय राष्ट्रीय पुस्तकालय वर्तमान में भारत सरकार के किस मंत्रालय के प्रशासनिक नियंत्रण में कार्य करता है?
* (A) शिक्षा मंत्रालय
* (B) संस्कृति मंत्रालय (Ministry of Culture)
* (C) विज्ञान एवं प्रौद्योगिकी मंत्रालय
* (D) सूचना एवं प्रसारण मंत्रालय

प्रश्न 15
वर्ष 1977 में राष्ट्रीय पुस्तकालय के मुख्य प्रशासनिक पद को लाइब्रेरियन से बदलकर निदेशक (Director) किया गया; इसके प्रथम निदेशक कौन थे?
* (A) प्रो. आर. के. दासगुप्ता
* (B) प्रो. स्वप्न चक्रवर्ती
* (C) प्रो. अजय प्रताप सिंह
* (D) डॉ. बी. एस. केशवन

उत्तर कुंजी (Answer Key)
प्रश्न संख्या
	सही विकल्प
	संक्षिप्त व्याख्या
	1
	(B)
	7वीं अनुसूची के अनुच्छेद 62 के तहत इसे संसद द्वारा घोषित राष्ट्रीय महत्व का संस्थान माना गया है।
	2
	(A)
	कलकत्ता पब्लिक लाइब्रेरी की स्थापना 21 मार्च 1836 को हुई थी।
	3
	(C)
	बाबू द्वारकानाथ टैगोर कलकत्ता पब्लिक लाइब्रेरी के पहले प्रोप्राइटर/संस्थापक सदस्य थे।
	4
	(C)
	तत्कालीन वायसराय लॉर्ड कर्जन ने 1902 में कानून बनाकर दोनों संस्थाओं का विलय किया।
	5
	(B)
	मेटकाफ हॉल में इंपीरियल लाइब्रेरी को 30 जनवरी 1903 को जनता के लिए खोला गया।
	6
	(B)
	ब्रिटिश म्यूजियम के जॉन मैकफर्लेन इंपीरियल लाइब्रेरी के पहले हेड लाइब्रेरियन बने।
	7
	(A)
	प्रख्यात विद्वान व भाषाविद हरिनाथ डे 1907 से 1911 तक इसके पहले भारतीय लाइब्रेरियन रहे।
	8
	(B)
	बी. एस. केशवन स्वतंत्र भारत के पहले लाइब्रेरियन थे और उन्होंने INB का संपादन शुरू किया।
	9
	(C)
	तत्कालीन केंद्रीय शिक्षा मंत्री मौलाना अबुल कलाम आज़ाद ने 1 फरवरी 1953 को इसका उद्घाटन किया।
	10
	(B)
	डिलीवरी ऑफ बुक्स एक्ट के तहत प्राप्त पुस्तकों के आधार पर 15 अगस्त 1958 को पहला INB प्रकाशित हुआ।
	11
	(C)
	INB वर्ष 2000 में कंप्यूटरीकृत हुई और वर्तमान में मासिक (Monthly) प्रकाशित होती है।
	12
	(C)
	यह संस्थान वर्ष भर में 362 दिन खुला रहता है।
	13
	(B)
	यह केवल तीन राष्ट्रीय पर्वों (26 जनवरी - गणतंत्र दिवस, 15 अगस्त - स्वतंत्रता दिवस, 2 अक्टूबर - गांधी जयंती) पर बंद रहता है।
	14
	(B)
	राष्ट्रीय पुस्तकालय भारत सरकार के संस्कृति मंत्रालय (Ministry of Culture) के प्रशासनिक नियंत्रण में है।
	15
	(A)
	1977 में लाइब्रेरियन के स्थान पर निदेशक पद बनाया गया और प्रो. आर. के. दासगुप्ता इसके पहले निदेशक बने।
        """.trimIndent()
    }

    /**
     * Pre-populated Unit 1 Subtopic 5 (विशिष्ट लाइब्रेरी / Special Library) content
     */
    fun getUnit1Subtopic5FullContent(): String {
        return """
1. मुख्य अवधारणाओं का विस्तृत सारांश (Core Concepts Summary)

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

2. वस्तुनिष्ठ प्रश्नोत्तरी (Multiple Choice Quiz)

प्रश्न 1
विशिष्ट पुस्तकालय (Special Library) मुख्य रूप से किसके लिए स्थापित किए जाते हैं?
* (A) स्कूल के छोटे बच्चों के लिए
* (B) आम जनता के मनोरंजन के लिए
* (C) किसी विशेष संगठन के शोधार्थियों एवं विशेषज्ञों के लिए
* (D) कॉलेज के स्नातक विद्यार्थियों के लिए

प्रश्न 2
निम्नलिखित में से कौन-सी अध्ययन सामग्री एक विशिष्ट पुस्तकालय का प्रमुख हिस्सा होती है?
* (A) कॉमिक्स और बाल साहित्य
* (B) उपन्यास और नाटक
* (C) पेटेंट, मानक और शोध पत्रिकाएँ (Journals)
* (D) सामान्य ज्ञान की गाइड बुक्स

प्रश्न 3
विशिष्ट पुस्तकालयों में 'SDI' का पूर्ण रूप (Full Form) क्या है?
* (A) Standard Document Information
* (B) Selective Dissemination of Information
* (C) System Design Interface
* (D) Serial Data Indexing

प्रश्न 4
पाठकों को उनके विषय क्षेत्र में हो रहे नवीनतम विकास और शोध से निरंतर अवगत कराने वाली सेवा क्या कहलाती है?
* (A) संदर्भ सेवा (Reference Service)
* (B) सामयिक अभिज्ञता सेवा (CAS - Current Awareness Service)
* (C) अनुवाद सेवा (Translation Service)
* (D) परिसंचरण सेवा (Circulation Service)

प्रश्न 5
"सटीक, संपूर्ण और त्वरित (Pin-pointed, Exhaustive, and Expeditious) सूचना प्रदान करना" किस पुस्तकालय का मुख्य लक्ष्य है?
* (A) सार्वजनिक पुस्तकालय
* (B) स्कूल पुस्तकालय
* (C) विशिष्ट पुस्तकालय
* (D) राष्ट्रीय पुस्तकालय

प्रश्न 6
निम्नलिखित में से कौन-सा एक विशिष्ट पुस्तकालय का उदाहरण है?
* (A) दिल्ली पब्लिक लाइब्रेरी
* (B) भारतीय अंतरिक्ष अनुसंधान संगठन (ISRO) का पुस्तकालय
* (C) पटना विश्वविद्यालय पुस्तकालय
* (D) राष्ट्रीय पुस्तकालय, कोलकाता

प्रश्न 7
विशिष्ट पुस्तकालयों में किस प्रकार की सेवा की सर्वाधिक मांग रहती है जो सामान्य पुस्तकालयों में प्रायः नहीं दी जाती?
* (A) पुस्तक उधार देना (Book Lending)
* (B) वाचनालय सुविधा (Reading Room)
* (C) सारकरण एवं अनुवाद सेवा (Abstracting & Translation Service)
* (D) मोबाइल लाइब्रेरी सेवा

प्रश्न 8
किसी संस्था (जैसे- DRDO या CSIR) का पुस्तकालय जो पूर्णतः उस संस्था के कर्मचारियों और वैज्ञानिकों को सेवा देता है, कहलाता है?
* (A) शैक्षणिक पुस्तकालय
* (B) सार्वजनिक पुस्तकालय
* (C) राष्ट्रीय पुस्तकालय
* (D) विशिष्ट पुस्तकालय

प्रश्न 9
डॉ. एस. आर. रंगनाथन के पुस्तकालय विज्ञान के किस सूत्र की पूर्ति विशिष्ट पुस्तकालयों की SDI और CAS सेवाओं द्वारा सबसे अधिक होती है?
* (A) प्रथम सूत्र (पुस्तकें उपयोग के लिए हैं)
* (B) द्वितीय सूत्र (प्रत्येक पाठक को उसकी पुस्तक मिले)
* (C) चतुर्थ सूत्र (पाठक का समय बचाएं)
* (D) पंचम सूत्र (पुस्तकालय एक वर्धनशील संस्था है)

प्रश्न 10
विशिष्ट पुस्तकालयों का बजट मुख्यतः किस पर निर्भर करता है?
* (A) पुस्तकालय उपकर (Library Cess) पर
* (B) मातृ संस्था (Parent Organization) द्वारा आवंटित अनुदान पर
* (C) आम जनता के चंदे पर
* (D) राज्य सरकार के शिक्षा विभाग पर

उत्तर कुंजी (Answer Key)
प्रश्न संख्या
	सही विकल्प
	संक्षिप्त व्याख्या
	1
	(C)
	विशिष्ट पुस्तकालय केवल किसी विशिष्ट संगठन, उद्योग या शोध केंद्र के वैज्ञानिकों और विशेषज्ञों (Special Users) को सेवा देते हैं।
	2
	(C)
	इनमें पारंपरिक पुस्तकों की बजाय प्राथमिक स्रोत जैसे पेटेंट्स (Patents), मानक (Standards) और शोध पत्रिकाओं (Journals) का अधिक संग्रह होता है।
	3
	(B)
	SDI का अर्थ Selective Dissemination of Information (चयनित सूचना प्रसार सेवा) है, जिसे H.P. Luhn (1958) ने प्रतिपादित किया था।
	4
	(B)
	CAS (Current Awareness Service) पाठकों को उनके क्षेत्र में हो रहे नवीनतम अनुसंधानों से अपडेट रखती है।
	5
	(C)
	रंगनाथन जी के अनुसार, एक विशिष्ट पुस्तकालय का कार्य उपयोगकर्ता को कम से कम समय में बिल्कुल सटीक (Pin-pointed) सूचना उपलब्ध कराना है।
	6
	(B)
	ISRO का पुस्तकालय केवल अंतरिक्ष विज्ञान के वैज्ञानिकों के लिए कार्य करता है, अतः यह एक विशिष्ट पुस्तकालय (Special Library) है।
	7
	(C)
	विदेशी भाषा के शोध पत्रों का अनुवाद (Translation) और बड़े लेखों का सार (Abstracting) बनाना विशिष्ट पुस्तकालयों की प्रमुख विशेषता है।
	8
	(D)
	मातृ संस्था (Parent Organization) के उद्देश्यों की पूर्ति के लिए स्थापित पुस्तकालय विशिष्ट पुस्तकालय की श्रेणी में आते हैं।
	9
	(C)
	CAS और SDI जैसी सेवाएं शोधार्थी को सूचना खोजने में लगने वाले समय को बचाती हैं, जो "पाठक का समय बचाएं" (Save the time of the user) सूत्र को चरितार्थ करता है।
	10
	(B)
	विशिष्ट पुस्तकालयों का अपना कोई स्वतंत्र आय का स्रोत नहीं होता; वे पूरी तरह से अपनी मातृ संस्था (Parent Institution) के बजट पर निर्भर होते हैं।
        """.trimIndent()
    }
}
