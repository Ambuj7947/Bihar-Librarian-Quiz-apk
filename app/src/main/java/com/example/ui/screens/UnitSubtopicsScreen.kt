package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DefaultQuestions
import com.example.data.SubtopicItem
import com.example.data.SubtopicRepository
import com.example.ui.AppScreen
import com.example.ui.QuizViewModel
import com.example.ui.components.TopHeader
import com.example.ui.theme.CorrectAnswerGreen
import com.example.ui.theme.IndigoSecondary
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.WisdomGold

enum class UnitSectionTab(val label: String) {
    ALL("📑 सभी"),
    NOTES("📖 अध्ययन नोट्स"),
    DPP("🎯 DPP अभ्यास")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitSubtopicsScreen(
    viewModel: QuizViewModel,
    categoryName: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val allQuestions by viewModel.allQuestions.collectAsState()
    val allStudyMaterials by viewModel.allStudyMaterials.collectAsState()
    val isExtraUnit = DefaultQuestions.isExtraQuestionsUnit(categoryName)
    val isUnitWithoutDpp = remember(categoryName, allQuestions) {
        DefaultQuestions.isUnitWithoutDpp(categoryName)
    }

    val subtopics = remember(categoryName, allQuestions, allStudyMaterials) {
        SubtopicRepository.getSubtopicsForCategory(categoryName, allQuestions, allStudyMaterials)
    }

    val totalQuestions = remember(subtopics) { subtopics.sumOf { it.questions.size } }
    val totalNotesCount = remember(subtopics) { subtopics.count { it.notesContent.isNotBlank() } }
    val completedSubtopicsCount = remember(subtopics) {
        subtopics.count { it.questions.isNotEmpty() && it.questions.all { q -> q.timesAttempted > 0 } }
    }

    val availableTabs = remember(isUnitWithoutDpp, totalQuestions) {
        if (isUnitWithoutDpp || totalQuestions == 0) {
            listOf(UnitSectionTab.ALL, UnitSectionTab.NOTES)
        } else {
            listOf(UnitSectionTab.ALL, UnitSectionTab.NOTES, UnitSectionTab.DPP)
        }
    }

    var selectedTab by remember { mutableStateOf(UnitSectionTab.ALL) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedNotesItem by remember { mutableStateOf<SubtopicItem?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(availableTabs) {
        if (selectedTab !in availableTabs) {
            selectedTab = UnitSectionTab.ALL
        }
    }

    // Filter by search query
    val searchedSubtopics = remember(subtopics, searchQuery) {
        if (searchQuery.isBlank()) {
            subtopics
        } else {
            subtopics.filter { sub ->
                sub.title.contains(searchQuery, ignoreCase = true) ||
                sub.tag.contains(searchQuery, ignoreCase = true) ||
                sub.notesContent.contains(searchQuery, ignoreCase = true) ||
                sub.questions.any { q -> q.questionHindi.contains(searchQuery, ignoreCase = true) }
            }
        }
    }

    Scaffold(
        topBar = {
            TopHeader(
                title = categoryName,
                subtitle = if (isExtraUnit) {
                    "${subtopics.size} अभ्यास सेट्स • $totalQuestions प्रश्न"
                } else if (isUnitWithoutDpp || totalQuestions == 0) {
                    "$totalNotesCount अध्ययन नोट्स (विस्तृत थ्योरी)"
                } else {
                    "$totalNotesCount अध्ययन नोट्स • $totalQuestions DPP प्रश्न"
                },
                showBack = true,
                onBack = { viewModel.navigateBack() }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF8FAFC))
        ) {
            // Top Unit Overview Card: Notes & DPP Metrics
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 1.dp,
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UnitMetricItem(
                        title = if (isExtraUnit) "कुल सेट्स" else "कुल उपविषय",
                        value = "${subtopics.size}",
                        accentColor = IndigoSecondary
                    )
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(28.dp)
                            .background(Color(0xFFE2E8F0))
                    )
                    UnitMetricItem(
                        title = "📖 अध्ययन नोट्स",
                        value = "$totalNotesCount",
                        accentColor = Color(0xFFB45309)
                    )
                    if (!isUnitWithoutDpp && totalQuestions > 0) {
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(28.dp)
                                .background(Color(0xFFE2E8F0))
                        )
                        UnitMetricItem(
                            title = "🎯 DPP प्रश्न",
                            value = "$totalQuestions",
                            accentColor = SaffronPrimary
                        )
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(28.dp)
                                .background(Color(0xFFE2E8F0))
                        )
                        UnitMetricItem(
                            title = "✓ हल किए DPP",
                            value = "$completedSubtopicsCount",
                            accentColor = CorrectAnswerGreen
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(28.dp)
                                .background(Color(0xFFE2E8F0))
                        )
                        UnitMetricItem(
                            title = "📚 थ्योरी नोट्स",
                            value = "संपूर्ण",
                            accentColor = Color(0xFF0284C7)
                        )
                    }
                }
            }

            // Search bar for fast filtering
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("उपविषय या नोट्स खोजें...", fontSize = 13.5.sp) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "खोजें",
                        tint = Color(0xFF64748B),
                        modifier = Modifier.size(18.dp)
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotBlank()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "हटाएं", tint = Color(0xFF64748B))
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color(0xFFE2E8F0)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .testTag("unit_search_input")
            )

            // Section Switcher based on Available Tabs
            val selectedTabIndex = availableTabs.indexOf(selectedTab).coerceAtLeast(0)
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.White,
                contentColor = MaterialTheme.colorScheme.primary,
                indicator = { tabPositions ->
                    if (selectedTabIndex in tabPositions.indices) {
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = MaterialTheme.colorScheme.primary,
                            height = 3.dp
                        )
                    }
                },
                divider = {
                    HorizontalDivider(color = Color(0xFFE2E8F0))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                availableTabs.forEach { tab ->
                    Tab(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        text = {
                            Text(
                                text = when (tab) {
                                    UnitSectionTab.ALL -> "📑 सभी (${subtopics.size})"
                                    UnitSectionTab.NOTES -> "📖 नोट्स ($totalNotesCount)"
                                    UnitSectionTab.DPP -> "🎯 DPP अभ्यास ($totalQuestions Qs)"
                                },
                                fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 13.5.sp,
                                color = if (selectedTab == tab) MaterialTheme.colorScheme.primary else Color(0xFF64748B)
                            )
                        },
                        modifier = Modifier.testTag("unit_tab_${tab.name.lowercase()}")
                    )
                }
            }

            // Main Content Area based on Selected Tab
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                if (searchedSubtopics.isEmpty()) {
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp)
                                .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Description,
                                    contentDescription = null,
                                    tint = Color(0xFF94A3B8),
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.height(14.dp))
                                Text(
                                    text = if (searchQuery.isNotBlank()) "कोई मेल नहीं मिला" else "इस इकाई में सामग्री तैयार की जा रही है",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color(0xFF1E293B)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = if (searchQuery.isNotBlank()) "कृपया भिन्न कीवर्ड से खोजें।" else "इस इकाई की अध्ययन सामग्री एवं अभ्यास प्रश्न जल्द उपलब्ध होंगे।",
                                    fontSize = 13.sp,
                                    color = Color(0xFF64748B),
                                    textAlign = TextAlign.Center
                                )
                                if (searchQuery.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    OutlinedButton(onClick = { searchQuery = "" }) {
                                        Text("खोज रीसेट करें")
                                    }
                                }
                            }
                        }
                    }
                } else {
                    when (selectedTab) {
                        UnitSectionTab.ALL -> {
                            // Paired View: Both Notes & DPP clean card
                            items(searchedSubtopics, key = { it.id }) { subtopic ->
                                val questionsAttempted = subtopic.questions.count { it.timesAttempted > 0 }
                                val isAllAttempted = subtopic.questions.isNotEmpty() && questionsAttempted == subtopic.questions.size

                                UnitOverviewSubtopicCard(
                                    subtopic = subtopic,
                                    isCompleted = isAllAttempted,
                                    isExtraUnit = isExtraUnit,
                                    onReadNotes = { selectedNotesItem = subtopic },
                                    onStartDpp = {
                                        viewModel.startSubtopicQuiz(
                                            categoryName = categoryName,
                                            subTopicTitle = subtopic.title,
                                            questions = subtopic.questions
                                        )
                                    }
                                )
                            }
                        }

                        UnitSectionTab.NOTES -> {
                            // Dedicated Notes View
                            items(searchedSubtopics, key = { it.id }) { subtopic ->
                                DedicatedNotesCard(
                                    subtopic = subtopic,
                                    isExtraUnit = isExtraUnit,
                                    onReadNotes = { selectedNotesItem = subtopic },
                                    onCopyNotes = {
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        val clip = ClipData.newPlainText("Notes", "${subtopic.title}\n\n${subtopic.notesContent}")
                                        clipboard.setPrimaryClip(clip)
                                        Toast.makeText(context, "नोट्स कॉपी हो गए!", Toast.LENGTH_SHORT).show()
                                    },
                                    onJumpToDpp = {
                                        viewModel.startSubtopicQuiz(
                                            categoryName = categoryName,
                                            subTopicTitle = subtopic.title,
                                            questions = subtopic.questions
                                        )
                                    }
                                )
                            }
                        }

                        UnitSectionTab.DPP -> {
                            // Dedicated DPP Practice View
                            items(searchedSubtopics, key = { it.id }) { subtopic ->
                                val questionsAttempted = subtopic.questions.count { it.timesAttempted > 0 }
                                val correctCount = subtopic.questions.count { it.timesCorrect > 0 }
                                val isAllAttempted = subtopic.questions.isNotEmpty() && questionsAttempted == subtopic.questions.size

                                DedicatedDppCard(
                                    subtopic = subtopic,
                                    isCompleted = isAllAttempted,
                                    questionsAttempted = questionsAttempted,
                                    correctCount = correctCount,
                                    isExtraUnit = isExtraUnit,
                                    onStartDpp = {
                                        viewModel.startSubtopicQuiz(
                                            categoryName = categoryName,
                                            subTopicTitle = subtopic.title,
                                            questions = subtopic.questions
                                        )
                                    }
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(48.dp))
                }
            }
        }
    }

    // Modal Bottom Sheet: Full Notes Reader
    selectedNotesItem?.let { item ->
        NotesReaderBottomSheet(
            subtopic = item,
            categoryName = categoryName,
            onDismiss = { selectedNotesItem = null },
            sheetState = sheetState,
            onStartDpp = {
                val currentItem = item
                selectedNotesItem = null
                viewModel.startSubtopicQuiz(
                    categoryName = categoryName,
                    subTopicTitle = currentItem.title,
                    questions = currentItem.questions
                )
            }
        )
    }
}

/**
 * Top Stat Item Widget
 */
@Composable
private fun UnitMetricItem(
    title: String,
    value: String,
    accentColor: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontSize = 17.sp,
            fontWeight = FontWeight.ExtraBold,
            color = accentColor
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = title,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF64748B)
        )
    }
}

/**
 * Tab 0: Unified Overview Card pairing Notes & DPP
 */
@Composable
fun UnitOverviewSubtopicCard(
    subtopic: SubtopicItem,
    isCompleted: Boolean,
    isExtraUnit: Boolean,
    onReadNotes: () -> Unit,
    onStartDpp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
            .testTag("overview_card_${subtopic.id}")
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header: Topic Tag & Completion Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isExtraUnit) Color(0xFFFEF3C7) else Color(0xFFEFF6FF))
                            .padding(horizontal = 9.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = subtopic.tag,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isExtraUnit) Color(0xFFB45309) else Color(0xFF1D4ED8)
                        )
                    }

                    Text(
                        text = "BLET 2026",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF94A3B8)
                    )
                }

                if (isCompleted) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFFDCFCE7))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = CorrectAnswerGreen,
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = "DPP पूर्ण",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF166534)
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFFF1F5F9))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.RadioButtonUnchecked,
                                contentDescription = null,
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "लंबित अभ्यास",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF64748B)
                            )
                        }
                    }
                }
            }

            // Title
            Text(
                text = subtopic.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 22.sp,
                color = Color(0xFF0F172A)
            )

            // Notes summary preview box if available
            if (subtopic.notesContent.isNotBlank()) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFFFFBEB),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDE68A)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = null,
                            tint = Color(0xFFB45309),
                            modifier = Modifier
                                .size(16.dp)
                                .padding(top = 2.dp)
                        )
                        Text(
                            text = subtopic.notesContent.replace("#", "").replace("\n", " ").trim().take(130) + "...",
                            fontSize = 12.5.sp,
                            lineHeight = 18.sp,
                            color = Color(0xFF92400E),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            // Badges row: Notes Available + DPP Questions (if questions exist)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFFEF3C7))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(Icons.Default.Description, contentDescription = null, tint = Color(0xFF92400E), modifier = Modifier.size(13.dp))
                        Text("थ्योरी नोट्स", fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF92400E))
                    }
                }

                if (subtopic.questions.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFFF0FDF4))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Default.Assignment, contentDescription = null, tint = CorrectAnswerGreen, modifier = Modifier.size(13.dp))
                            Text("${subtopic.questions.size} DPP प्रश्न", fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF166534))
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFFEFF6FF))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Default.MenuBook, contentDescription = null, tint = Color(0xFF1D4ED8), modifier = Modifier.size(13.dp))
                            Text("विस्तृत नोट्स उपलब्ध", fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1D4ED8))
                        }
                    }
                }
            }

            HorizontalDivider(color = Color(0xFFF1F5F9))

            // Action Buttons: If DPP exists, show both; if no DPP, show dedicated Notes button
            if (subtopic.questions.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = onReadNotes,
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp)
                            .testTag("notes_btn_${subtopic.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(17.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "नोट्स पढ़ें",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.5.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Button(
                        onClick = onStartDpp,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isExtraUnit) Color(0xFF1E3A8A) else SaffronPrimary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        modifier = Modifier
                            .weight(1.2f)
                            .height(46.dp)
                            .testTag("dpp_btn_${subtopic.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isExtraUnit) "टेस्ट दें (${subtopic.questions.size} Qs)" else "DPP दें (${subtopic.questions.size} Qs)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.5.sp
                        )
                    }
                }
            } else {
                Button(
                    onClick = onReadNotes,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("notes_btn_${subtopic.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "विस्तृत थ्योरी नोट्स पढ़ें",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

/**
 * Tab 1: Dedicated Study Notes Card
 */
@Composable
fun DedicatedNotesCard(
    subtopic: SubtopicItem,
    isExtraUnit: Boolean,
    onReadNotes: () -> Unit,
    onCopyNotes: () -> Unit,
    onJumpToDpp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
            .testTag("dedicated_notes_card_${subtopic.id}")
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header tag
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFFEF3C7))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(Icons.Default.MenuBook, contentDescription = null, tint = Color(0xFFB45309), modifier = Modifier.size(14.dp))
                        Text(
                            text = "${subtopic.tag} • विस्तृत अध्ययन नोट्स",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFB45309)
                        )
                    }
                }

                IconButton(
                    onClick = onCopyNotes,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "नोट्स कॉपी करें",
                        tint = Color(0xFF64748B),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Title
            Text(
                text = subtopic.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.5.sp,
                lineHeight = 23.sp,
                color = Color(0xFF0F172A)
            )

            // Notes excerpt block
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFFAF8F5),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF3EDE2)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onReadNotes() }
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = if (subtopic.notesContent.isNotBlank()) {
                            subtopic.notesContent
                                .replace("#", "")
                                .replace("**", "")
                                .replace("---", "")
                                .trim()
                                .take(200) + "..."
                        } else {
                            "इस उपविषय के विस्तृत नोट्स तैयार किए जा रहे हैं।"
                        },
                        fontSize = 13.5.sp,
                        lineHeight = 20.sp,
                        color = Color(0xFF334155),
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "पूरा नोट्स विस्तार से पढ़ने के लिए टैप करें ➔",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFC85A17)
                    )
                }
            }

            // Actions row: [पूरा नोट्स पढ़ें] (and optional [DPP दें] if questions exist)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onReadNotes,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (subtopic.questions.isNotEmpty()) Color(0xFF1E293B) else MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    modifier = if (subtopic.questions.isNotEmpty()) {
                        Modifier.weight(1f).height(46.dp)
                    } else {
                        Modifier.fillMaxWidth().height(46.dp)
                    }
                ) {
                    Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("पूरा नोट्स पढ़ें", fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
                }

                if (subtopic.questions.isNotEmpty()) {
                    OutlinedButton(
                        onClick = onJumpToDpp,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1.1f)
                            .height(46.dp)
                    ) {
                        Icon(Icons.Default.Assignment, contentDescription = null, tint = SaffronPrimary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("DPP दें (${subtopic.questions.size} Qs)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = SaffronPrimary)
                    }
                }
            }
        }
    }
}

/**
 * Tab 2: Dedicated Daily Practice Problems (DPP) Card
 */
@Composable
fun DedicatedDppCard(
    subtopic: SubtopicItem,
    isCompleted: Boolean,
    questionsAttempted: Int,
    correctCount: Int,
    isExtraUnit: Boolean,
    onStartDpp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
            .testTag("dedicated_dpp_card_${subtopic.id}")
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header: DPP Number & Completion Pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFEFF6FF))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(Icons.Default.Assignment, contentDescription = null, tint = Color(0xFF1D4ED8), modifier = Modifier.size(14.dp))
                        Text(
                            text = if (isExtraUnit) subtopic.tag else "DPP-${String.format("%02d", subtopic.subtopicNumber)}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1D4ED8)
                        )
                    }
                }

                if (isCompleted) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFFDCFCE7))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = CorrectAnswerGreen, modifier = Modifier.size(14.dp))
                            Text("पूर्ण ($correctCount/${subtopic.questions.size} सही)", fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = Color(0xFF166534))
                        }
                    }
                } else if (questionsAttempted > 0) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFFFEF3C7))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("प्रगति पर ($questionsAttempted/${subtopic.questions.size})", fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFFB45309))
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFFF1F5F9))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("लंबित अभ्यास", fontSize = 11.5.sp, fontWeight = FontWeight.Medium, color = Color(0xFF64748B))
                    }
                }
            }

            // Subtopic Question Title
            Text(
                text = subtopic.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 22.sp,
                color = Color(0xFF0F172A)
            )

            // Info box: questions count and format
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF8FAFC), RoundedCornerShape(10.dp))
                    .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(10.dp))
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "दैनिक अभ्यास प्रश्न (DPP)",
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF64748B)
                    )
                    Text(
                        text = "${subtopic.questions.size} बहुविकल्पीय प्रश्न (MCQ)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFE0E7FF))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "विस्तृत व्याख्या सहित",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF3730A3)
                    )
                }
            }

            // Primary CTA: Start DPP Test
            Button(
                onClick = onStartDpp,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isExtraUnit) Color(0xFF1E3A8A) else SaffronPrimary,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("start_dpp_button_${subtopic.id}")
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isCompleted) "पुनः DPP अभ्यास दें (${subtopic.questions.size} Qs)" else "DPP टेस्ट शुरू करें (${subtopic.questions.size} Qs)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.5.sp
                )
            }
        }
    }
}

/**
 * Enhanced ModalBottomSheet: Clean Notes Reader with Reading Controls
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesReaderBottomSheet(
    subtopic: SubtopicItem,
    categoryName: String,
    onDismiss: () -> Unit,
    sheetState: androidx.compose.material3.SheetState,
    onStartDpp: () -> Unit
) {
    val context = LocalContext.current
    var fontScale by remember { mutableFloatStateOf(1.0f) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp)
                .navigationBarsPadding()
        ) {
            // Sheet Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
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
                                text = subtopic.tag,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF92400E)
                            )
                        }
                        Text(
                            text = "BLET 2026 अध्ययन नोट्स",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF64748B)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = subtopic.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Font size adjuster
                    IconButton(onClick = {
                        fontScale = if (fontScale >= 1.25f) 0.9f else fontScale + 0.15f
                    }) {
                        Icon(Icons.Default.FormatSize, contentDescription = "फॉन्ट आकार बदलें", tint = Color(0xFF64748B))
                    }

                    // Copy button
                    IconButton(onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Notes", "${subtopic.title}\n\n${subtopic.notesContent}")
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(context, "नोट्स कॉपी हो गए!", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "कॉपी करें", tint = Color(0xFF64748B))
                    }

                    // Share button
                    IconButton(onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, subtopic.title)
                            putExtra(Intent.EXTRA_TEXT, "${subtopic.title}\n\n${subtopic.notesContent}\n\n— बिहार लाइब्रेरियन परीक्षा 2026 तैयारी")
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "नोट्स साझा करें"))
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "साझा करें", tint = Color(0xFF64748B))
                    }

                    // Close button
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "बंद करें", tint = Color(0xFF64748B))
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE2E8F0))

            // Scrollable Notes Body
            LazyColumn(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .height(390.dp)
            ) {
                item {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFFAF8F5),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF1E8DC)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (subtopic.notesContent.isNotBlank()) subtopic.notesContent else "इस उपविषय के विस्तृत नोट्स तैयार किए जा रहे हैं।",
                            fontSize = (14.5 * fontScale).sp,
                            lineHeight = (23 * fontScale).sp,
                            color = Color(0xFF1E293B),
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (subtopic.questions.isNotEmpty()) {
                // Direct CTA: Jump Straight into DPP
                Button(
                    onClick = onStartDpp,
                    colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("notes_reader_start_dpp_button")
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "नोट्स पढ़ लिए? अब इसका DPP अभ्यास दें (${subtopic.questions.size} Qs)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.5.sp
                    )
                }
            } else {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E293B)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("notes_reader_close_button")
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "नोट्स अध्ययन पूर्ण",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.5.sp
                    )
                }
            }
        }
    }
}
