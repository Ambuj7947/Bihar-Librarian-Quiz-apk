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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.CloudSync
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
import androidx.compose.runtime.remember
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
import com.example.data.SubtopicRepository
import com.example.ui.AppScreen
import com.example.ui.QuizViewModel
import com.example.ui.components.SubjectProgressData
import com.example.ui.components.SubjectProgressVisualizer
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
    val allStudyMaterials by viewModel.allStudyMaterials.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    val isOnline by viewModel.isOnline.collectAsState()
    val scale = 1.0f

    val attemptedQuestionsCount = allQuestions.count { it.timesAttempted > 0 }
    val totalAttempts = allQuestions.sumOf { it.timesAttempted }
    val totalCorrect = allQuestions.sumOf { it.timesCorrect }
    val accuracyPercentage = if (totalAttempts > 0) (totalCorrect * 100) / totalAttempts else 0

    // Palette of colors for units in visual progress breakdown
    val categoryColors = listOf(
        Color(0xFF2563EB), // Blue - Unit 1
        Color(0xFF0D9488), // Teal - Unit 2
        Color(0xFF7C3AED), // Violet - Unit 3
        Color(0xFFD97706), // Amber - Unit 4
        Color(0xFFDC2626), // Crimson - Unit 5
        Color(0xFF059669)  // Emerald - Extra / Unit 6
    )

    val subjectProgressList = remember(categories, allQuestions) {
        categories.mapIndexed { index, catName ->
            val isExtra = com.example.data.DefaultQuestions.isExtraQuestionsUnit(catName)
            val catQuestions = allQuestions.filter {
                if (isExtra) com.example.data.DefaultQuestions.isExtraQuestionsUnit(it.category)
                else it.category == catName
            }
            val attempted = catQuestions.count { it.timesAttempted > 0 }
            val correct = catQuestions.sumOf { it.timesCorrect }
            val color = categoryColors.getOrElse(index % categoryColors.size) { SaffronPrimary }

            // Clean short label
            val shortLabel = when {
                catName.contains("इकाई 1") || catName.contains("पुस्तकालय एवं समाज") -> "इकाई 1: पुस्तकालय समाज"
                catName.contains("इकाई 2") || catName.contains("वर्गीकरण") -> "इकाई 2: वर्गीकरण एवं सूचीकरण"
                catName.contains("इकाई 3") || catName.contains("सूचना सेवाएं") -> "इकाई 3: सूचना एवं संदर्भ"
                catName.contains("इकाई 4") || catName.contains("प्रबंधन") -> "इकाई 4: पुस्तकालय प्रबंधन"
                catName.contains("इकाई 5") || catName.contains("कंप्यूटर") -> "इकाई 5: ICT एवं कंप्यूटर"
                else -> catName.take(24)
            }

            SubjectProgressData(
                categoryName = catName,
                shortLabel = shortLabel,
                totalQuestions = catQuestions.size,
                attemptedQuestions = attempted,
                correctQuestions = correct,
                color = color
            )
        }
    }

    Scaffold(
        topBar = {
            TopHeader(
                title = "बिहार लाइब्रेरियन परीक्षा",
                subtitle = "BLET पात्रता परीक्षा तैयारी",
                showBack = false,
                actionContent = {
                    IconButton(
                        onClick = { viewModel.navigateTo(AppScreen.SignIn) },
                        modifier = Modifier.testTag("account_signin_icon_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "लॉगिन / प्रोफ़ाइल",
                            tint = if (currentUser != null) WisdomGold else Color.White
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
            // Cloud Sync & Login Status Banner
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (currentUser != null) Color(0xFFEFF6FF) else Color(0xFFFFFBEB)
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (currentUser != null) Color(0xFFBFDBFE) else Color(0xFFFDE68A)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.navigateTo(AppScreen.SignIn) }
                        .testTag("login_sync_banner_card")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = if (currentUser != null) Icons.Default.CloudDone else Icons.Default.CloudSync,
                                contentDescription = null,
                                tint = if (currentUser != null) Color(0xFF2563EB) else Color(0xFFD97706),
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = if (currentUser != null)
                                        "प्रगति सुरक्षित: ${currentUser?.displayName}"
                                    else
                                        "नया फोन / अपडेट? डेटा सुरक्षित रखने हेतु लॉगिन करें",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (currentUser != null) Color(0xFF1E40AF) else Color(0xFF92400E)
                                )
                                Text(
                                    text = if (currentUser != null)
                                        "क्लाउड सिंक सक्रिय • ऑनलाइन आने पर स्वतः बैकअप"
                                    else
                                        "ID पासवर्ड या Google से लॉगिन करें • कभी प्रगति रीसेट नहीं होगी",
                                    fontSize = 11.sp,
                                    color = if (currentUser != null) Color(0xFF3B82F6) else Color(0xFFB45309)
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = if (currentUser != null) Color(0xFF2563EB) else Color(0xFFD97706),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
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

            // Visual Summary Section (Interactive Recharts-style Bar & Donut Charts for Category Progress)
            item {
                SubjectProgressVisualizer(
                    progressList = subjectProgressList,
                    onCategoryClick = { categoryName ->
                        viewModel.navigateToUnitSubtopics(categoryName)
                    }
                )
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

            // Categorized Practice Sets Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "बिहार लाइब्रेरियन पाठ्यक्रम (इकाइयाँ)",
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

            // Category cards list - no buttons inside, one arrow to go under unit section
            itemsIndexed(categories) { index, categoryName ->
                val isExtra = com.example.data.DefaultQuestions.isExtraQuestionsUnit(categoryName)
                val categoryQuestions = allQuestions.filter {
                    if (isExtra) com.example.data.DefaultQuestions.isExtraQuestionsUnit(it.category)
                    else it.category == categoryName
                }
                val answeredCount = categoryQuestions.count { it.timesAttempted > 0 }
                val icon = getCategoryIcon(categoryName)
                val subtitle = com.example.data.DefaultQuestions.unitEnglishSubtitles[categoryName]
                val subtopicsCount = SubtopicRepository.getSubtopicsForCategory(categoryName, allQuestions, allStudyMaterials).size

                CategoryCard(
                    title = categoryName,
                    subtitle = subtitle,
                    chapterIndex = index + 1,
                    subtopicsCount = subtopicsCount,
                    questionCount = categoryQuestions.size,
                    answeredCount = answeredCount,
                    isExtraUnit = isExtra,
                    icon = icon,
                    scale = scale,
                    onClick = { viewModel.navigateToUnitSubtopics(categoryName) },
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
    chapterIndex: Int = 1,
    subtopicsCount: Int = 5,
    questionCount: Int = 0,
    answeredCount: Int = 0,
    isExtraUnit: Boolean = false,
    icon: ImageVector,
    scale: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val chapterTag = String.format("CH - %02d", chapterIndex)

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Top Pill: e.g. "CH - 01" matching Screenshot 1
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFE0F2FE))
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = chapterTag,
                        fontSize = (11 * scale).sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0369A1)
                    )
                }

                // Title: Bold and prominent
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = (16 * scale).sp,
                    color = Color(0xFF0F172A),
                    lineHeight = (22 * scale).sp
                )

                if (!subtitle.isNullOrBlank()) {
                    Text(
                        text = subtitle,
                        fontSize = (12 * scale).sp,
                        color = Color(0xFF64748B),
                        fontWeight = FontWeight.Normal
                    )
                }

                // Notes & DPP Stats Badges (NO Lectures)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFFFEF3C7))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = if (isExtraUnit) "अभ्यास सेट्स : $subtopicsCount" else "नोट्स : $subtopicsCount उपविषय",
                            fontSize = (11.5 * scale).sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF92400E)
                        )
                    }
                    if (questionCount > 0 || isExtraUnit) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFFDCFCE7))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = if (isExtraUnit) "MCQs : ${if (questionCount > 0) "$questionCount प्रश्न" else "0 प्रश्न"}" else "DPP : $questionCount प्रश्न",
                                fontSize = (11.5 * scale).sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF166534)
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFFEFF6FF))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "थ्योरी नोट्स",
                                fontSize = (11.5 * scale).sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF1D4ED8)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Right Chevron Arrow (One arrow to go under unit section - NO buttons inside card)
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF8FAFC)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "इकाई खोलें",
                    tint = Color(0xFF64748B),
                    modifier = Modifier.size(22.dp)
                )
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
