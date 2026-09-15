package com.example.ui.screens

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
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
import com.example.ui.QuizViewModel
import com.example.ui.components.ExplanationCard
import com.example.ui.components.TopHeader
import com.example.ui.theme.CorrectAnswerGreen
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.WisdomGold
import com.example.ui.theme.WrongAnswerRed

@Composable
fun BookmarksScreen(
    viewModel: QuizViewModel,
    modifier: Modifier = Modifier
) {
    val bookmarkedQuestions by viewModel.bookmarkedQuestions.collectAsState()
    val textScale by viewModel.textScale.collectAsState()
    val scale = textScale.scale

    Scaffold(
        topBar = {
            TopHeader(
                title = "महत्वपूर्ण प्रश्न (बुकमार्क)",
                subtitle = "${bookmarkedQuestions.size} प्रश्न सुरक्षित",
                showBack = true,
                onBack = { viewModel.navigateBack() },
                currentTextScale = textScale,
                onCycleTextScale = { viewModel.cycleTextScale() }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            if (bookmarkedQuestions.isNotEmpty()) {
                item {
                    Button(
                        onClick = { viewModel.startBookmarkedQuiz() },
                        colors = ButtonDefaults.buttonColors(containerColor = WisdomGold, contentColor = Color(0xFF451A03)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("start_bookmarked_quiz_btn")
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "सभी सुरक्षित प्रश्नों का टेस्ट दें (${bookmarkedQuestions.size})",
                            fontWeight = FontWeight.Bold,
                            fontSize = (15 * scale).sp
                        )
                    }
                }
            } else {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.BookmarkBorder,
                                contentDescription = null,
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(54.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "अभी कोई बुकमार्क प्रश्न नहीं है",
                                fontSize = (17 * scale).sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF64748B)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "क्विज़ हल करते समय स्टार आइकन दबाकर महत्वपूर्ण प्रश्नों को यहाँ सुरक्षित कर सकती हैं।",
                                fontSize = (14 * scale).sp,
                                color = Color(0xFF94A3B8),
                                lineHeight = (20 * scale).sp
                            )
                        }
                    }
                }
            }

            items(bookmarkedQuestions) { question ->
                StudyQuestionCard(
                    question = question,
                    scale = scale,
                    onToggleBookmark = { viewModel.toggleBookmark(question) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
fun MistakesScreen(
    viewModel: QuizViewModel,
    modifier: Modifier = Modifier
) {
    val mistakeQuestions by viewModel.mistakeQuestions.collectAsState()
    val textScale by viewModel.textScale.collectAsState()
    val scale = textScale.scale

    Scaffold(
        topBar = {
            TopHeader(
                title = "गलत प्रश्नों का सुधार",
                subtitle = "${mistakeQuestions.size} प्रश्न सुधार हेतु शेष",
                showBack = true,
                onBack = { viewModel.navigateBack() },
                currentTextScale = textScale,
                onCycleTextScale = { viewModel.cycleTextScale() }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            if (mistakeQuestions.isNotEmpty()) {
                item {
                    Button(
                        onClick = { viewModel.startMistakeRevision() },
                        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("start_mistake_quiz_btn")
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "गलत प्रश्नों का पुनः टेस्ट शुरू करें (${mistakeQuestions.size})",
                            fontWeight = FontWeight.Bold,
                            fontSize = (15 * scale).sp
                        )
                    }
                }
            } else {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = CorrectAnswerGreen,
                                modifier = Modifier.size(54.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "बहुत खूब माँ! कोई गलत प्रश्न बाकी नहीं है! 🌟",
                                fontSize = (18 * scale).sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E293B)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "जब भी किसी टेस्ट में कोई प्रश्न गलत होगा, वह यहाँ स्वतः जुड़ जाएगा ताकि आप उसका बार-बार अभ्यास कर सकें।",
                                fontSize = (14 * scale).sp,
                                color = Color(0xFF64748B),
                                lineHeight = (20 * scale).sp
                            )
                        }
                    }
                }
            }

            items(mistakeQuestions) { question ->
                StudyQuestionCard(
                    question = question,
                    scale = scale,
                    onToggleBookmark = { viewModel.toggleBookmark(question) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}
