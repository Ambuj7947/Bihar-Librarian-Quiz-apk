package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DefaultQuestions
import com.example.ui.QuizViewModel
import com.example.ui.components.TopHeader
import com.example.ui.theme.CorrectAnswerGreen
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.WisdomGold

@Composable
fun AddQuestionScreen(
    viewModel: QuizViewModel,
    modifier: Modifier = Modifier
) {
    val categories by viewModel.categories.collectAsState()
    val userAddedQuestions by viewModel.userAddedQuestions.collectAsState()
    val statusMessage by viewModel.statusMessage.collectAsState()
    val scale = 1.0f

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(statusMessage) {
        statusMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearStatusMessage()
        }
    }

    var selectedCategory by remember { mutableStateOf(DefaultQuestions.CAT_FOUNDATIONS) }
    var questionText by remember { mutableStateOf("") }
    var optionA by remember { mutableStateOf("") }
    var optionB by remember { mutableStateOf("") }
    var optionC by remember { mutableStateOf("") }
    var optionD by remember { mutableStateOf("") }
    var correctOption by remember { mutableIntStateOf(1) } // 1..4
    var explanationText by remember { mutableStateOf("") }
    var keyHighlightText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopHeader(
                title = "नया प्रश्न जोड़ें",
                subtitle = "दैनिक प्रश्नोत्तरी बैंक विस्तार",
                showBack = true,
                onBack = { viewModel.navigateBack() }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Helper instructions
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFFDE68A), RoundedCornerShape(16.dp))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Color(0xFFB45309),
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "यहाँ आप प्रतिदिन नए प्रश्न, चारों विकल्प और उनकी विस्तृत व्याख्या जोड़ सकते हैं। यह स्वतः आपके अभ्यास सेट और दैनिक टेस्ट में शामिल हो जाएंगे।",
                            fontSize = (13 * scale).sp,
                            lineHeight = (19 * scale).sp,
                            color = Color(0xFF78350F)
                        )
                    }
                }
            }

            // Category Selection
            item {
                Text(
                    text = "1. विषय / श्रेणी चुनें:",
                    fontWeight = FontWeight.Bold,
                    fontSize = (15 * scale).sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { cat ->
                        val isSelected = cat == selectedCategory
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (isSelected) SaffronPrimary else Color.White)
                                .border(
                                    1.dp,
                                    if (isSelected) SaffronPrimary else Color(0xFFCBD5E1),
                                    RoundedCornerShape(20.dp)
                                )
                                .clickable { selectedCategory = cat }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = cat,
                                fontSize = (13 * scale).sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color.White else Color(0xFF334155)
                            )
                        }
                    }
                }
            }

            // Question Input
            item {
                Text(
                    text = "2. प्रश्न दर्ज करें (हिंदी में):",
                    fontWeight = FontWeight.Bold,
                    fontSize = (15 * scale).sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = questionText,
                    onValueChange = { questionText = it },
                    placeholder = { Text("जैसे: डॉ. रंगनाथन ने पुस्तकालय विज्ञान के 5 सूत्र कब दिए?") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_question"),
                    minLines = 2,
                    maxLines = 4,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }

            // 4 Options
            item {
                Text(
                    text = "3. चारों विकल्प दर्ज करें:",
                    fontWeight = FontWeight.Bold,
                    fontSize = (15 * scale).sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OptionInputField(
                        label = "विकल्प (क)",
                        value = optionA,
                        onValueChange = { optionA = it },
                        isCorrect = correctOption == 1,
                        onMarkCorrect = { correctOption = 1 },
                        scale = scale,
                        tag = "input_option_a"
                    )
                    OptionInputField(
                        label = "विकल्प (ख)",
                        value = optionB,
                        onValueChange = { optionB = it },
                        isCorrect = correctOption == 2,
                        onMarkCorrect = { correctOption = 2 },
                        scale = scale,
                        tag = "input_option_b"
                    )
                    OptionInputField(
                        label = "विकल्प (ग)",
                        value = optionC,
                        onValueChange = { optionC = it },
                        isCorrect = correctOption == 3,
                        onMarkCorrect = { correctOption = 3 },
                        scale = scale,
                        tag = "input_option_c"
                    )
                    OptionInputField(
                        label = "विकल्प (घ)",
                        value = optionD,
                        onValueChange = { optionD = it },
                        isCorrect = correctOption == 4,
                        onMarkCorrect = { correctOption = 4 },
                        scale = scale,
                        tag = "input_option_d"
                    )
                }
            }

            // Explanation Input
            item {
                Text(
                    text = "4. विस्तृत समाधान एवं व्याख्या (अनिवार्य):",
                    fontWeight = FontWeight.Bold,
                    fontSize = (15 * scale).sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = explanationText,
                    onValueChange = { explanationText = it },
                    placeholder = { Text("विस्तृत विवरण लिखें: सही उत्तर क्यों है, लेखक/संस्था का नाम, ऐतिहासिक संदर्भ एवं महत्वपूर्ण वर्ष...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_explanation"),
                    minLines = 3,
                    maxLines = 6,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }

            // Key Highlight Memory Tip
            item {
                Text(
                    text = "5. याद रखने योग्य मुख्य बिंदु (वैकल्पिक):",
                    fontWeight = FontWeight.Bold,
                    fontSize = (15 * scale).sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = keyHighlightText,
                    onValueChange = { keyHighlightText = it },
                    placeholder = { Text("जैसे: प्रतिपादन: 1928 | पुस्तक प्रकाशन: 1931") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_highlight"),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }

            // Save Button
            item {
                Button(
                    onClick = {
                        val success = viewModel.addNewQuestion(
                            category = selectedCategory,
                            questionHindi = questionText,
                            optionA = optionA,
                            optionB = optionB,
                            optionC = optionC,
                            optionD = optionD,
                            correctOption = correctOption,
                            explanationHindi = explanationText,
                            keyHighlight = keyHighlightText
                        )
                        if (success) {
                            // Clear inputs
                            questionText = ""
                            optionA = ""
                            optionB = ""
                            optionC = ""
                            optionD = ""
                            explanationText = ""
                            keyHighlightText = ""
                            correctOption = 1
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .testTag("save_question_button")
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "नया प्रश्न सुरक्षित करें",
                        fontWeight = FontWeight.Bold,
                        fontSize = (16 * scale).sp
                    )
                }
            }

            // Section: User Added Questions History
            if (userAddedQuestions.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "आपके द्वारा जोड़े गए प्रश्न (${userAddedQuestions.size}):",
                        fontWeight = FontWeight.Bold,
                        fontSize = (16 * scale).sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                items(userAddedQuestions) { question ->
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(14.dp))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = question.category,
                                    fontSize = (12 * scale).sp,
                                    color = SaffronPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                                IconButton(
                                    onClick = { viewModel.deleteQuestion(question) },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "हटाएं",
                                        tint = Color(0xFFEF4444),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Text(
                                text = question.questionHindi,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = (15 * scale).sp,
                                color = Color(0xFF1E293B)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "सही: विकल्प (${question.correctOption}) - ${question.getOptionText(question.correctOption)}",
                                fontSize = (13 * scale).sp,
                                color = CorrectAnswerGreen,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
fun OptionInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isCorrect: Boolean,
    onMarkCorrect: () -> Unit,
    scale: Float,
    tag: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier
                .weight(1f)
                .testTag(tag),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Correct Answer Selector Chip
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(if (isCorrect) CorrectAnswerGreen else Color(0xFFF1F5F9))
                .clickable { onMarkCorrect() }
                .padding(horizontal = 10.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isCorrect) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Text(
                    text = if (isCorrect) "सही उत्तर" else "सही चुनें",
                    fontSize = (12 * scale).sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isCorrect) Color.White else Color(0xFF64748B)
                )
            }
        }
    }
}
