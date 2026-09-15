package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.ui.QuizViewModel
import com.example.ui.components.ExplanationCard
import com.example.ui.components.OptionButton
import com.example.ui.components.TopHeader
import com.example.ui.theme.CorrectAnswerGreen
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.WisdomGold

@Composable
fun QuizPlayScreen(
    viewModel: QuizViewModel,
    modifier: Modifier = Modifier
) {
    val quizState by viewModel.quizState.collectAsState()
    val scale = 1.0f

    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler {
        showExitDialog = true
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = {
                Text(
                    text = "क्विज़ छोड़ें?",
                    fontWeight = FontWeight.Bold,
                    fontSize = (18 * scale).sp
                )
            },
            text = {
                Text(
                    text = "क्या आप वर्तमान टेस्ट छोड़ना चाहती हैं? आपकी प्रगति सुरक्षित नहीं होगी।",
                    fontSize = (15 * scale).sp
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    showExitDialog = false
                    viewModel.navigateBack()
                }) {
                    Text("हाँ, बाहर जाएं", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text("नहीं, अभ्यास जारी रखें")
                }
            }
        )
    }

    val currentQuestion = quizState.currentQuestion

    Scaffold(
        topBar = {
            TopHeader(
                title = quizState.title,
                subtitle = "प्रश्न ${quizState.currentIndex + 1} / ${quizState.questions.size}",
                showBack = true,
                onBack = { showExitDialog = true },
                isBookmarked = currentQuestion?.isBookmarked,
                onToggleBookmark = {
                    currentQuestion?.let { viewModel.toggleBookmark(it) }
                }
            )
        },
        bottomBar = {
            // Bottom navigation buttons
            Surface(
                tonalElevation = 6.dp,
                shadowElevation = 8.dp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (quizState.currentIndex > 0) {
                        OutlinedButton(
                            onClick = { viewModel.previousQuestion() },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .testTag("previous_question_button")
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "पिछला",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = (15 * scale).sp
                            )
                        }
                    }

                    val isLastQuestion = quizState.currentIndex == quizState.questions.size - 1
                    Button(
                        onClick = { viewModel.nextQuestion() },
                        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1.4f)
                            .height(50.dp)
                            .testTag("next_question_button")
                    ) {
                        Text(
                            text = if (isLastQuestion) "समाप्त करें" else "अगला प्रश्न",
                            fontWeight = FontWeight.Bold,
                            fontSize = (15 * scale).sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = if (isLastQuestion) Icons.Default.CheckCircle else Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null
                        )
                    }
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        if (currentQuestion == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("कोई प्रश्न उपलब्ध नहीं है।", fontSize = 16.sp)
            }
        } else {
            val selectedOption = quizState.userAnswers[currentQuestion.id]

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                // Progress Bar
                LinearProgressIndicator(
                    progress = { quizState.progressFraction },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp),
                    color = WisdomGold,
                    trackColor = Color(0xFFE2E8F0)
                )

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Category Badge & Question Status
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = currentQuestion.category,
                                fontSize = (12 * scale).sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        // Answered status pill
                        val isAnswered = selectedOption != null
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isAnswered) Color(0xFFDCFCE7) else Color(0xFFF1F5F9))
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = if (isAnswered) "उत्तर दिया गया" else "उत्तर चुनें",
                                fontSize = (12 * scale).sp,
                                fontWeight = FontWeight.Medium,
                                color = if (isAnswered) Color(0xFF15803D) else Color(0xFF64748B)
                            )
                        }
                    }

                    // Question Card
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Row(verticalAlignment = Alignment.Top) {
                                Text(
                                    text = "प्र. ${quizState.currentIndex + 1}. ",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = (18 * scale).sp
                                    ),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = currentQuestion.questionHindi,
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = (18 * scale).sp,
                                        lineHeight = (27 * scale).sp
                                    ),
                                    color = Color(0xFF1E293B)
                                )
                            }
                        }
                    }

                    // Options List
                    Text(
                        text = "विकल्प चुनें:",
                        fontSize = (14 * scale).sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF64748B)
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        OptionButton(
                            optionIndex = 1,
                            optionText = currentQuestion.optionA,
                            selectedOption = selectedOption,
                            correctOption = currentQuestion.correctOption,
                            scaleFactor = scale,
                            onClick = { viewModel.selectAnswer(1) },
                            modifier = Modifier.testTag("option_1")
                        )
                        OptionButton(
                            optionIndex = 2,
                            optionText = currentQuestion.optionB,
                            selectedOption = selectedOption,
                            correctOption = currentQuestion.correctOption,
                            scaleFactor = scale,
                            onClick = { viewModel.selectAnswer(2) },
                            modifier = Modifier.testTag("option_2")
                        )
                        OptionButton(
                            optionIndex = 3,
                            optionText = currentQuestion.optionC,
                            selectedOption = selectedOption,
                            correctOption = currentQuestion.correctOption,
                            scaleFactor = scale,
                            onClick = { viewModel.selectAnswer(3) },
                            modifier = Modifier.testTag("option_3")
                        )
                        OptionButton(
                            optionIndex = 4,
                            optionText = currentQuestion.optionD,
                            selectedOption = selectedOption,
                            correctOption = currentQuestion.correctOption,
                            scaleFactor = scale,
                            onClick = { viewModel.selectAnswer(4) },
                            modifier = Modifier.testTag("option_4")
                        )
                    }

                    // Detailed Explanation Card
                    // Automatically shows once answered, or mother can toggle it
                    if (selectedOption != null && quizState.showInstantExplanation) {
                        Spacer(modifier = Modifier.height(6.dp))
                        ExplanationCard(
                            question = currentQuestion,
                            selectedOption = selectedOption,
                            scaleFactor = scale,
                            modifier = Modifier.testTag("explanation_card")
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
