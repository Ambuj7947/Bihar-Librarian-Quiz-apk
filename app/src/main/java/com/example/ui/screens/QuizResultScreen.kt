package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppScreen
import com.example.ui.QuizViewModel
import com.example.ui.components.ExplanationCard
import com.example.ui.components.TopHeader
import com.example.ui.theme.CorrectAnswerBg
import com.example.ui.theme.CorrectAnswerGreen
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.WisdomGold
import com.example.ui.theme.WrongAnswerBg
import com.example.ui.theme.WrongAnswerRed

@Composable
fun QuizResultScreen(
    viewModel: QuizViewModel,
    modifier: Modifier = Modifier
) {
    val quizState by viewModel.quizState.collectAsState()
    val scale = 1.0f

    val total = quizState.questions.size
    val correct = quizState.correctAnswersCount
    val wrong = quizState.wrongAnswersCount
    val unattempted = total - (correct + wrong)
    val percentage = if (total > 0) (correct * 100) / total else 0

    val (greetingTitle, greetingSubtitle, badgeColor) = when {
        percentage >= 80 -> Triple(
            "अति उत्तम प्रदर्शन! 🌟",
            "शानदार प्रदर्शन! आपकी मेहनत से सफलता निश्चित है।",
            CorrectAnswerGreen
        )
        percentage >= 50 -> Triple(
            "बहुत अच्छा प्रयास! 👍",
            "आपका अभ्यास सही दिशा में है, गलत प्रश्नों का एक बार पुनरीक्षण करें।",
            SaffronPrimary
        )
        else -> Triple(
            "अभ्यास जारी रखें! 💪",
            "निराश न हों! नीचे दिए गए प्रश्नों की विस्तृत व्याख्या ध्यान से पढ़ें।",
            Color(0xFFE65100)
        )
    }

    BackHandler {
        viewModel.navigateTo(AppScreen.Home)
    }

    Scaffold(
        topBar = {
            TopHeader(
                title = "टेस्ट परिणाम एवं व्याख्या",
                subtitle = quizState.title,
                showBack = true,
                onBack = { viewModel.navigateTo(AppScreen.Home) }
            )
        },
        bottomBar = {
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
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.navigateTo(AppScreen.Home) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Icon(Icons.Default.Home, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "होम",
                            fontWeight = FontWeight.Bold,
                            fontSize = (15 * scale).sp
                        )
                    }

                    Button(
                        onClick = {
                            if (quizState.category == "दैनिक अभ्यास") {
                                viewModel.startDailyQuiz()
                            } else {
                                viewModel.startCategoryQuiz(quizState.category)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1.3f)
                            .height(50.dp)
                    ) {
                        Icon(Icons.Default.Replay, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "पुनः अभ्यास",
                            fontWeight = FontWeight.Bold,
                            fontSize = (15 * scale).sp
                        )
                    }
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Encouragement & Score Card
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(20.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Score Circle
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(badgeColor.copy(alpha = 0.12f))
                                .border(3.dp, badgeColor, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$percentage%",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = (26 * scale).sp,
                                    color = badgeColor
                                )
                                Text(
                                    text = "$correct / $total",
                                    fontSize = (12 * scale).sp,
                                    color = Color(0xFF64748B),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = greetingTitle,
                            fontWeight = FontWeight.Bold,
                            fontSize = (20 * scale).sp,
                            color = Color(0xFF1E293B)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = greetingSubtitle,
                            fontSize = (14 * scale).sp,
                            color = Color(0xFF64748B),
                            lineHeight = (20 * scale).sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Stat counters
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            StatPill(
                                label = "सही उत्तर",
                                count = "$correct",
                                color = CorrectAnswerGreen,
                                bg = CorrectAnswerBg,
                                scale = scale
                            )
                            StatPill(
                                label = "गलत उत्तर",
                                count = "$wrong",
                                color = WrongAnswerRed,
                                bg = WrongAnswerBg,
                                scale = scale
                            )
                            StatPill(
                                label = "अनुत्तरित",
                                count = "$unattempted",
                                color = Color(0xFF64748B),
                                bg = Color(0xFFF1F5F9),
                                scale = scale
                            )
                        }
                    }
                }
            }

            // Detailed Solution Header
            item {
                Text(
                    text = "सभी प्रश्नों का विस्तृत समाधान एवं व्याख्या:",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = (17 * scale).sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Questions list with solution and explanation
            itemsIndexed(quizState.questions) { index, question ->
                val selectedOpt = quizState.userAnswers[question.id]
                val isCorrect = selectedOpt == question.correctOption
                val isAnswered = selectedOpt != null

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            if (isCorrect) CorrectAnswerGreen.copy(alpha = 0.5f) else Color(0xFFE2E8F0),
                            RoundedCornerShape(16.dp)
                        )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Question Header & Status
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "प्रश्न ${index + 1}",
                                fontWeight = FontWeight.Bold,
                                fontSize = (14 * scale).sp,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        when {
                                            !isAnswered -> Color(0xFFF1F5F9)
                                            isCorrect -> CorrectAnswerBg
                                            else -> WrongAnswerBg
                                        }
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = when {
                                            !isAnswered -> Icons.Default.Close
                                            isCorrect -> Icons.Default.Check
                                            else -> Icons.Default.Close
                                        },
                                        contentDescription = null,
                                        tint = when {
                                            !isAnswered -> Color(0xFF64748B)
                                            isCorrect -> CorrectAnswerGreen
                                            else -> WrongAnswerRed
                                        },
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = when {
                                            !isAnswered -> "छोड़ा गया"
                                            isCorrect -> "सही उत्तर"
                                            else -> "गलत उत्तर"
                                        },
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = (12 * scale).sp,
                                        color = when {
                                            !isAnswered -> Color(0xFF64748B)
                                            isCorrect -> Color(0xFF14532D)
                                            else -> Color(0xFF7F1D1D)
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Question Text
                        Text(
                            text = question.questionHindi,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = (16 * scale).sp,
                            color = Color(0xFF1E293B),
                            lineHeight = (24 * scale).sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Selected vs Correct answer info
                        if (selectedOpt != null && !isCorrect) {
                            Text(
                                text = "आपका चुना गया उत्तर: विकल्प ($selectedOpt) - ${question.getOptionText(selectedOpt)}",
                                fontSize = (14 * scale).sp,
                                color = WrongAnswerRed,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }

                        // Explanation Box
                        ExplanationCard(
                            question = question,
                            selectedOption = selectedOpt,
                            scaleFactor = scale
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}

@Composable
fun StatPill(
    label: String,
    count: String,
    color: Color,
    bg: Color,
    scale: Float
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(bg)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = count,
                fontWeight = FontWeight.Bold,
                fontSize = (18 * scale).sp,
                color = color
            )
            Text(
                text = label,
                fontSize = (11 * scale).sp,
                fontWeight = FontWeight.Medium,
                color = color.copy(alpha = 0.9f)
            )
        }
    }
}
