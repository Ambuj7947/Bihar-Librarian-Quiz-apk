package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.QuestionEntity
import com.example.data.model.StudyMaterialEntity
import com.example.ui.AppScreen
import com.example.ui.QuizViewModel
import com.example.ui.components.ExplanationCard
import com.example.ui.components.TopHeader
import com.example.ui.theme.CorrectAnswerBg
import com.example.ui.theme.CorrectAnswerGreen
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.WisdomGold
import com.example.util.ContentSeparator

@Composable
fun QuestionBankScreen(
    viewModel: QuizViewModel,
    initialCategoryFilter: String? = null,
    modifier: Modifier = Modifier
) {
    val allQuestions by viewModel.allQuestions.collectAsState()
    val allStudyMaterials by viewModel.allStudyMaterials.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val scale = 1.0f

    var selectedCategory by remember { mutableStateOf(initialCategoryFilter ?: "सभी") }
    var searchQuery by remember { mutableStateOf("") }

    val relevantMaterials = remember(selectedCategory, allStudyMaterials) {
        if (selectedCategory == "सभी") emptyList()
        else allStudyMaterials.filter { it.unitCategory == selectedCategory }
    }

    val filteredQuestions = allQuestions.filter { question ->
        val matchesCat = selectedCategory == "सभी" || question.category == selectedCategory
        val matchesQuery = searchQuery.isBlank() ||
                question.questionHindi.contains(searchQuery, ignoreCase = true) ||
                question.explanationHindi.contains(searchQuery, ignoreCase = true) ||
                question.category.contains(searchQuery, ignoreCase = true)
        matchesCat && matchesQuery
    }

    Scaffold(
        topBar = {
            TopHeader(
                title = if (initialCategoryFilter != null) initialCategoryFilter else "प्रश्न बैंक एवं अध्ययन गाइड",
                subtitle = "${filteredQuestions.size} प्रश्न उपलब्ध",
                showBack = true,
                onBack = { viewModel.navigateBack() }
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
            // Search Input Field
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("प्रश्न, विषय या व्याख्या में खोजें...") },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null)
                    },
                    trailingIcon = {
                        if (searchQuery.isNotBlank()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "साफ करें")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_question_input"),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    singleLine = true
                )
            }

            // Category filter chips
            item {
                val chipList = listOf("सभी") + categories
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(chipList) { cat ->
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
                                .padding(horizontal = 14.dp, vertical = 7.dp)
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

            // Quick practice button for selected category
            if (selectedCategory != "सभी") {
                item {
                    Button(
                        onClick = { viewModel.startCategoryQuiz(selectedCategory) },
                        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "'$selectedCategory' का टेस्ट शुरू करें",
                            fontWeight = FontWeight.Bold,
                            fontSize = (14 * scale).sp
                        )
                    }
                }
            }

            // If selected unit has study materials (Video lecture and notes), show them nicely
            if (relevantMaterials.isNotEmpty()) {
                items(relevantMaterials) { material ->
                    UnitStudyMaterialBanner(
                        material = material,
                        scale = scale
                    )
                }
            }

            if (filteredQuestions.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = if (selectedCategory == "सभी") "अभी कोई प्रश्न उपलब्ध नहीं है" else "'$selectedCategory' में कोई प्रश्न नहीं है",
                                fontSize = (16 * scale).sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF64748B)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "प्रतिदिन नए प्रश्न जोड़ने के लिए नीचे दिए गए बटन पर टैप करें।",
                                fontSize = (13 * scale).sp,
                                color = Color(0xFF94A3B8)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { viewModel.navigateTo(AppScreen.AddQuestion) },
                                colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "नया प्रश्न जोड़ें",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = (14 * scale).sp
                                )
                            }
                        }
                    }
                }
            }

            // Questions with full study details
            items(filteredQuestions) { question ->
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
fun StudyQuestionCard(
    question: QuestionEntity,
    scale: Float,
    onToggleBookmark: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header row with category and bookmark
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = question.category,
                        fontSize = (11 * scale).sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(
                    onClick = onToggleBookmark,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = if (question.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "बुकमार्क",
                        tint = if (question.isBookmarked) WisdomGold else Color(0xFF94A3B8)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Question Text
            Text(
                text = question.questionHindi,
                fontWeight = FontWeight.Bold,
                fontSize = (16 * scale).sp,
                color = Color(0xFF1E293B),
                lineHeight = (24 * scale).sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Options list with highlighted correct option
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                StudyOptionItem(label = "क", text = question.optionA, isCorrect = question.correctOption == 1, scale = scale)
                StudyOptionItem(label = "ख", text = question.optionB, isCorrect = question.correctOption == 2, scale = scale)
                StudyOptionItem(label = "ग", text = question.optionC, isCorrect = question.correctOption == 3, scale = scale)
                StudyOptionItem(label = "घ", text = question.optionD, isCorrect = question.correctOption == 4, scale = scale)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Explanation Card
            ExplanationCard(
                question = question,
                selectedOption = question.correctOption,
                scaleFactor = scale
            )
        }
    }
}

@Composable
fun StudyOptionItem(
    label: String,
    text: String,
    isCorrect: Boolean,
    scale: Float
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(if (isCorrect) CorrectAnswerBg else Color(0xFFF8FAFC))
            .border(
                1.dp,
                if (isCorrect) CorrectAnswerGreen else Color(0xFFE2E8F0),
                RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(if (isCorrect) CorrectAnswerGreen else Color(0xFFE2E8F0)),
            contentAlignment = Alignment.Center
        ) {
            if (isCorrect) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            } else {
                Text(
                    text = label,
                    fontSize = (12 * scale).sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF64748B)
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            fontSize = (14 * scale).sp,
            fontWeight = if (isCorrect) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isCorrect) Color(0xFF14532D) else Color(0xFF334155)
        )
    }
}

@Composable
fun UnitStudyMaterialBanner(
    material: StudyMaterialEntity,
    scale: Float,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    var isNotesExpanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header badge & Topic title
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFFEFF6FF), RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "उप-विषय: ${material.subTopic}",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF1D4ED8)
                    )
                }

                Text(
                    text = "${material.questionsCount} अभ्यास प्रश्न",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = Color(0xFF16A34A)
                )
            }

            // YouTube Video Card
            if (material.youtubeUrl.isNotBlank()) {
                val videoId = material.youtubeVideoId.ifEmpty {
                    ContentSeparator.extractYouTubeVideoId(material.youtubeUrl) ?: ""
                }
                val thumbUrl = ContentSeparator.getYouTubeThumbnailUrl(videoId)

                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                        ) {
                            if (thumbUrl.isNotBlank()) {
                                AsyncImage(
                                    model = thumbUrl,
                                    contentDescription = "Video Thumbnail",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Brush.verticalGradient(
                                                listOf(Color(0xFF1E293B), Color(0xFF0F172A))
                                            )
                                        )
                                )
                            }

                            // Dark gradient overlay
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(Color.Transparent, Color(0xCC000000))
                                        )
                                    )
                            )

                            // Play Button Indicator
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .background(Color(0xCCDC2626), CircleShape)
                                    .align(Alignment.Center)
                                    .clickable {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(material.youtubeUrl))
                                        context.startActivity(intent)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play Video",
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            // Live / HD badge
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .background(Color(0xCCDC2626), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "यूट्यूब क्लास",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White,
                                    fontSize = 10.sp
                                )
                            }
                        }

                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = material.youtubeTitle.ifEmpty { "वीडियो लेक्चर" },
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color.White,
                                maxLines = 2
                            )

                            if (material.timestampNotes.isNotBlank()) {
                                Text(
                                    text = "विवरण: ${material.timestampNotes}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF94A3B8),
                                    fontSize = 11.sp
                                )
                            }

                            Button(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(material.youtubeUrl))
                                    context.startActivity(intent)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(38.dp)
                            ) {
                                Icon(Icons.Default.SmartDisplay, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "यूट्यूब पर वीडियो लेक्चर देखें",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            // Notes Section
            if (material.notesContent.isNotBlank()) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = null,
                                    tint = Color(0xFF172E54),
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "अध्ययन नोट्स (Study Notes)",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = Color(0xFF1E293B)
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                IconButton(
                                    onClick = {
                                        clipboardManager.setText(AnnotatedString(material.notesContent))
                                    },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "कॉपी करें",
                                        tint = Color(0xFF64748B),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }

                                IconButton(
                                    onClick = { isNotesExpanded = !isNotesExpanded },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isNotesExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                        contentDescription = if (isNotesExpanded) "कम करें" else "पूरा देखें",
                                        tint = Color(0xFF64748B),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }

                        Text(
                            text = material.notesContent,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                lineHeight = 20.sp,
                                color = Color(0xFF334155)
                            ),
                            maxLines = if (isNotesExpanded) Int.MAX_VALUE else 6
                        )

                        if (!isNotesExpanded) {
                            Text(
                                text = "पूरा नोट्स पढ़ने के लिए टैप करें...",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFFC85A17),
                                modifier = Modifier.clickable { isNotesExpanded = true }
                            )
                        }
                    }
                }
            }
        }
    }
}
