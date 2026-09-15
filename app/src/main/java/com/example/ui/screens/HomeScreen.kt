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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DefaultQuestions
import com.example.ui.AppScreen
import com.example.ui.QuizViewModel
import com.example.ui.components.TopHeader
import com.example.ui.theme.IndigoSecondary
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.WisdomGold

@Composable
fun HomeScreen(
    viewModel: QuizViewModel,
    modifier: Modifier = Modifier
) {
    val allQuestions by viewModel.allQuestions.collectAsState()
    val bookmarkedQuestions by viewModel.bookmarkedQuestions.collectAsState()
    val mistakeQuestions by viewModel.mistakeQuestions.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val scale = 1.0f

    val attemptedQuestionsCount = allQuestions.count { it.timesAttempted > 0 }
    val totalAttempts = allQuestions.sumOf { it.timesAttempted }
    val totalCorrect = allQuestions.sumOf { it.timesCorrect }
    val accuracyPercentage = if (totalAttempts > 0) (totalCorrect * 100) / totalAttempts else 0

    Scaffold(
        topBar = {
            TopHeader(
                title = "बिहार लाइब्रेरियन परीक्षा",
                subtitle = "BLET पात्रता परीक्षा तैयारी",
                showBack = false,
                actionContent = {
                    IconButton(
                        onClick = { viewModel.navigateTo(AppScreen.ContentCreatorHub) },
                        modifier = Modifier.testTag("topbar_content_creator_hub_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.PostAdd,
                            contentDescription = "सामग्री / प्रश्न जोड़ें",
                            tint = Color.White
                        )
                    }
                }
            )
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
            // Hero Welcome Card
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(IndigoSecondary, Color(0xFF283593), Color(0xFF1E3A8A))
                            )
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocalLibrary,
                                    contentDescription = null,
                                    tint = WisdomGold,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "नमस्ते! स्वागत है 🙏",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = (20 * scale).sp,
                                    color = Color.White
                                )
                                Text(
                                    text = "बिहार लाइब्रेरियन बनने का सपना होगा साकार",
                                    fontSize = (13 * scale).sp,
                                    color = Color.White.copy(alpha = 0.85f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Daily Quiz Action Button
                        Button(
                            onClick = { viewModel.startDailyQuiz() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = WisdomGold,
                                contentColor = Color(0xFF451A03)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .testTag("start_daily_quiz_button")
                        ) {
                            Icon(Icons.Default.Today, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "आज का दैनिक टेस्ट शुरू करें (10 प्रश्न)",
                                fontWeight = FontWeight.Bold,
                                fontSize = (15 * scale).sp
                            )
                        }
                    }
                }
            }

            // Quick Stats Summary Row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatBox(
                        title = "कुल प्रश्न",
                        value = "${allQuestions.size}",
                        subtitle = "उपलब्ध",
                        color = SaffronPrimary,
                        scale = scale,
                        modifier = Modifier.weight(1f)
                    )
                    StatBox(
                        title = "हल किए",
                        value = "$attemptedQuestionsCount",
                        subtitle = "प्रश्न",
                        color = IndigoSecondary,
                        scale = scale,
                        modifier = Modifier.weight(1f)
                    )
                    StatBox(
                        title = "सटीकता",
                        value = "$accuracyPercentage%",
                        subtitle = "सही उत्तर",
                        color = Color(0xFF16A34A),
                        scale = scale,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Quick Actions (Full Mock, Mistakes, Bookmarks, Study Mode)
            item {
                Text(
                    text = "त्वरित अभ्यास मोड",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = (17 * scale).sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionCard(
                        title = "सम्पूर्ण मॉक टेस्ट",
                        desc = "20 मिश्रित प्रश्न",
                        icon = Icons.Default.School,
                        badgeColor = SaffronPrimary,
                        scale = scale,
                        onClick = { viewModel.startFullMockTest() },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("full_mock_test_card")
                    )

                    QuickActionCard(
                        title = "गलत प्रश्न सुधार",
                        desc = "${mistakeQuestions.size} प्रश्न बाकी",
                        icon = Icons.Default.ErrorOutline,
                        badgeColor = Color(0xFFDC2626),
                        scale = scale,
                        onClick = { viewModel.startMistakeRevision() },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("mistakes_card")
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionCard(
                        title = "महत्वपूर्ण प्रश्न",
                        desc = "${bookmarkedQuestions.size} बुकमार्क",
                        icon = Icons.Default.Bookmark,
                        badgeColor = WisdomGold,
                        scale = scale,
                        onClick = { viewModel.startBookmarkedQuiz() },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("bookmarks_card")
                    )

                    QuickActionCard(
                        title = "प्रश्न बैंक / अध्ययन",
                        desc = "सभी प्रश्न व्याख्या सहित",
                        icon = Icons.Default.AutoStories,
                        badgeColor = IndigoSecondary,
                        scale = scale,
                        onClick = { viewModel.navigateTo(AppScreen.QuestionBank) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("question_bank_card")
                    )
                }
            }

            // Dedicated Content Creator Hub Feature Card (YouTube + Notes + Auto-Quiz Separator)
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFF172E54), Color(0xFF1C3052))
                            )
                        )
                        .clickable { viewModel.navigateTo(AppScreen.ContentCreatorHub) }
                        .testTag("content_creator_hub_card")
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .background(WisdomGold.copy(alpha = 0.2f), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PostAdd,
                                        contentDescription = null,
                                        tint = WisdomGold,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }

                                Column {
                                    Text(
                                        text = "सामग्री निर्माता केंद्र",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = (16 * scale).sp
                                        ),
                                        color = Color.White
                                    )
                                    Text(
                                        text = "YouTube लिंक, नोट्स व ऑटो क्विज़ बनाएं",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontSize = (12 * scale).sp
                                        ),
                                        color = Color(0xFFCBD5E1)
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .background(WisdomGold, RoundedCornerShape(10.dp))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "नया पेज",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = Color(0xFF351000)
                                )
                            }
                        }

                        Text(
                            text = "सिंगल फाइल या टेक्स्ट प्रदान करें — सिस्टम नोट्स और प्रश्नों को अलग कर देगा और स्वतः नए क्विज़ तैयार करेगा।",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = (12 * scale).sp),
                            color = Color(0xFFE2E8F0)
                        )

                        Button(
                            onClick = { viewModel.navigateTo(AppScreen.ContentCreatorHub) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFD35400),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.SmartDisplay, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "वीडियो, नोट्स एवं क्विज़ जोड़ें ➔",
                                fontWeight = FontWeight.Bold,
                                fontSize = (13 * scale).sp
                            )
                        }
                    }
                }
            }

            // Categorized Practice Sets Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "बिहार लाइब्रेरियन पाठ्यक्रम एवं एक्स्ट्रा सेट्स",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = (17 * scale).sp
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "${categories.size} इकाइयाँ",
                        fontSize = (13 * scale).sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Category cards list
            items(categories) { categoryName ->
                val categoryQuestions = allQuestions.filter { it.category == categoryName }
                val answeredCount = categoryQuestions.count { it.timesAttempted > 0 }
                val icon = getCategoryIcon(categoryName)
                val subtitle = com.example.data.DefaultQuestions.unitEnglishSubtitles[categoryName]

                CategoryCard(
                    title = categoryName,
                    subtitle = subtitle,
                    questionCount = categoryQuestions.size,
                    answeredCount = answeredCount,
                    icon = icon,
                    scale = scale,
                    onPractice = { viewModel.startCategoryQuiz(categoryName) },
                    onStudy = { viewModel.navigateTo(AppScreen.StudyMode(categoryName)) },
                    modifier = Modifier.testTag("category_card_${categoryName.hashCode()}")
                )
            }

            // Bottom spacing for FAB
            item {
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}

@Composable
fun StatBox(
    title: String,
    value: String,
    subtitle: String,
    color: Color,
    scale: Float,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier.border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(14.dp))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = (11 * scale).sp,
                color = Color(0xFF64748B),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = (20 * scale).sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                fontSize = (10 * scale).sp,
                color = Color(0xFF94A3B8)
            )
        }
    }
}

@Composable
fun QuickActionCard(
    title: String,
    desc: String,
    icon: ImageVector,
    badgeColor: Color,
    scale: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(badgeColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = badgeColor,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = (15 * scale).sp,
                color = Color(0xFF1E293B)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = desc,
                fontSize = (12 * scale).sp,
                color = Color(0xFF64748B)
            )
        }
    }
}

@Composable
fun CategoryCard(
    title: String,
    subtitle: String? = null,
    questionCount: Int,
    answeredCount: Int,
    icon: ImageVector,
    scale: Float,
    onPractice: () -> Unit,
    onStudy: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier.border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = (15 * scale).sp,
                        color = Color(0xFF1E293B)
                    )
                    if (subtitle != null) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = subtitle,
                            fontSize = (11 * scale).sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (questionCount > 0) "$questionCount प्रश्न • $answeredCount हल किए" else "0 प्रश्न उपलब्ध (दैनिक नया जोड़ें)",
                        fontSize = (11 * scale).sp,
                        color = Color(0xFF64748B)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Two action buttons: Practice Quiz and Study Notes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onPractice,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1.2f)
                        .height(44.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "क्विज़ टेस्ट",
                        fontWeight = FontWeight.Bold,
                        fontSize = (14 * scale).sp
                    )
                }

                Button(
                    onClick = onStudy,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f),
                        contentColor = MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "प्रश्न देखें",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = (13 * scale).sp
                    )
                }
            }
        }
    }
}

fun getCategoryIcon(categoryName: String): ImageVector {
    return when {
        categoryName.contains("इकाई 1") || categoryName.contains("आधार") -> Icons.Default.LocalLibrary
        categoryName.contains("इकाई 2") || categoryName.contains("वर्गीकरण") || categoryName.contains("सूचीकरण") -> Icons.Default.Storage
        categoryName.contains("इकाई 3") || categoryName.contains("प्रबंधन") || categoryName.contains("विभाग") -> Icons.Default.School
        categoryName.contains("इकाई 4") || categoryName.contains("सूचना") || categoryName.contains("स्रोत") -> Icons.Default.AutoStories
        categoryName.contains("इकाई 5") || categoryName.contains("कंप्यूटर") -> Icons.Default.MenuBook
        categoryName.contains("इकाई 6") || categoryName.contains("एक्स्ट्रा") || categoryName.contains("Extra") -> Icons.Default.Quiz
        else -> Icons.Default.Psychology
    }
}
