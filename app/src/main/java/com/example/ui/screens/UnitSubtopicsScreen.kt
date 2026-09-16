package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SmartDisplay
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SubtopicItem
import com.example.data.SubtopicRepository
import com.example.ui.AppScreen
import com.example.ui.QuizViewModel
import com.example.ui.components.TopHeader
import com.example.ui.theme.WisdomGold

enum class SubtopicFilter {
    ALL, LECTURES, NOTES, DPPS
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

    val subtopics = remember(categoryName, allQuestions, allStudyMaterials) {
        SubtopicRepository.getSubtopicsForCategory(categoryName, allQuestions, allStudyMaterials)
    }

    var activeFilter by remember { mutableStateOf(SubtopicFilter.ALL) }
    var selectedNotesItem by remember { mutableStateOf<SubtopicItem?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val filteredSubtopics = remember(subtopics, activeFilter) {
        when (activeFilter) {
            SubtopicFilter.ALL -> subtopics
            SubtopicFilter.LECTURES -> subtopics.filter { it.youtubeUrl.isNotBlank() }
            SubtopicFilter.NOTES -> subtopics.filter { it.notesContent.isNotBlank() }
            SubtopicFilter.DPPS -> subtopics.filter { it.questions.isNotEmpty() }
        }
    }

    Scaffold(
        topBar = {
            TopHeader(
                title = categoryName,
                subtitle = "${subtopics.size} उपविषय • लेक्चर्स व DPP",
                showBack = true,
                onBack = { viewModel.navigateBack() },
                actionContent = {
                    IconButton(
                        onClick = { viewModel.navigateTo(AppScreen.ContentCreatorHub) },
                        modifier = Modifier.testTag("unit_add_content_hub_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.PostAdd,
                            contentDescription = "नया उपविषय जोड़ें",
                            tint = Color.White
                        )
                    }
                }
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
            // Filter Chips Bar (matching Screenshot 2: All | Lectures | Notes | DPPs)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilterPill(
                    title = "All (सभी)",
                    isSelected = activeFilter == SubtopicFilter.ALL,
                    onClick = { activeFilter = SubtopicFilter.ALL }
                )
                FilterPill(
                    title = "Lectures (कक्षाएं)",
                    isSelected = activeFilter == SubtopicFilter.LECTURES,
                    onClick = { activeFilter = SubtopicFilter.LECTURES }
                )
                FilterPill(
                    title = "Notes (नोट्स)",
                    isSelected = activeFilter == SubtopicFilter.NOTES,
                    onClick = { activeFilter = SubtopicFilter.NOTES }
                )
                FilterPill(
                    title = "DPPs (प्रश्नोत्तरी)",
                    isSelected = activeFilter == SubtopicFilter.DPPS,
                    onClick = { activeFilter = SubtopicFilter.DPPS }
                )
            }

            HorizontalDivider(color = Color(0xFFE2E8F0), thickness = 1.dp)

            // Subtopics Cards List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (filteredSubtopics.isEmpty()) {
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp)
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
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "इस फ़िल्टर में कोई सामग्री नहीं मिली",
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF475569)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = { activeFilter = SubtopicFilter.ALL },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("सभी सामग्री देखें")
                                }
                            }
                        }
                    }
                } else {
                    items(filteredSubtopics, key = { it.id }) { subtopic ->
                        val questionsAttempted = subtopic.questions.count { it.timesAttempted > 0 }
                        val isAllAttempted = subtopic.questions.isNotEmpty() && questionsAttempted == subtopic.questions.size

                        SubtopicLectureCard(
                            subtopic = subtopic,
                            isCompleted = isAllAttempted,
                            onWatchVideo = {
                                if (subtopic.youtubeUrl.isNotBlank()) {
                                    try {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(subtopic.youtubeUrl))
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "वीडियो लिंक खोला नहीं जा सका", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    Toast.makeText(context, "इस उपविषय का वीडियो जल्द उपलब्ध होगा", Toast.LENGTH_SHORT).show()
                                }
                            },
                            onViewNotes = {
                                selectedNotesItem = subtopic
                            },
                            onAttemptQuiz = {
                                viewModel.startSubtopicQuiz(
                                    categoryName = categoryName,
                                    subTopicTitle = subtopic.title,
                                    questions = subtopic.questions
                                )
                            }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }

    // Modal Bottom Sheet for Notes reading & study
    selectedNotesItem?.let { item ->
        ModalBottomSheet(
            onDismissRequest = { selectedNotesItem = null },
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
                // Sheet Top Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.tag,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = item.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Row {
                        IconButton(onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Subtopic Notes", "${item.title}\n\n${item.notesContent}")
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "नोट्स कॉपी हो गए!", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(Icons.Default.ContentCopy, contentDescription = "कॉपी करें", tint = Color(0xFF64748B))
                        }
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE2E8F0))

                // Scrollable Notes Body
                LazyColumn(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .height(380.dp)
                ) {
                    item {
                        Text(
                            text = if (item.notesContent.isNotBlank()) item.notesContent else "इस उपविषय के विस्तृत नोट्स तैयार किए जा रहे हैं।",
                            fontSize = 14.sp,
                            lineHeight = 22.sp,
                            color = Color(0xFF334155),
                            fontWeight = FontWeight.Normal
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons at the bottom of the notes
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            selectedNotesItem = null
                            viewModel.startSubtopicQuiz(
                                categoryName = categoryName,
                                subTopicTitle = item.title,
                                questions = item.questions
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "इस विषय का DPP / क्विज़ दें (${item.questions.size} प्रश्न)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

/**
 * Filter Pill Component matching Screenshot 2 (e.g. All, Lectures, Notes, DPPs)
 */
@Composable
fun FilterPill(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(if (isSelected) Color(0xFF1E293B) else Color.White)
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFF1E293B) else Color(0xFFE2E8F0),
                shape = CircleShape
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color.White else Color(0xFF475569)
        )
    }
}

/**
 * Subtopic Lecture Card Component matching Screenshot 2 exactly:
 * - Video thumbnail with red circular Play button at bottom-right
 * - Title, duration, completion checkmark
 * - Buttons under video: Download, Notes, Watch
 * - Distinct DPP / Quiz strip at bottom: "Attempt DPP >"
 */
@Composable
fun SubtopicLectureCard(
    subtopic: SubtopicItem,
    isCompleted: Boolean,
    onWatchVideo: () -> Unit,
    onViewNotes: () -> Unit,
    onAttemptQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val hasVideo = subtopic.youtubeUrl.isNotBlank()

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
            .testTag("subtopic_card_${subtopic.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Top section: Video Thumbnail + Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.Top
            ) {
                // Left Video Thumbnail with Red Circular Play button superimposed (or Quiz icon if no video)
                Box(
                    modifier = Modifier
                        .size(width = 114.dp, height = 82.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.linearGradient(
                                if (hasVideo) listOf(Color(0xFF0F172A), Color(0xFF1E3A8A), Color(0xFF1E293B))
                                else listOf(Color(0xFF1E3A8A), Color(0xFF1E40AF), Color(0xFF3B82F6))
                            )
                        )
                        .clickable(onClick = if (hasVideo) onWatchVideo else onAttemptQuiz)
                ) {
                    // Educational Background Graphic
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = if (hasVideo) Icons.Default.SmartDisplay else Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = WisdomGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "BLET 2026",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Text(
                            text = subtopic.tag,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = WisdomGold
                        )
                    }

                    // Circular Action Button in bottom-right corner
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(6.dp)
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(if (hasVideo) Color(0xFFE11D48) else Color(0xFF10B981)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (hasVideo) Icons.Default.PlayArrow else Icons.Default.CheckCircle,
                            contentDescription = if (hasVideo) "Play Lecture" else "Start Quiz",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                // Right Info Column
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Meta Row: "Lecture • Date" + Completion Checkmark
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Lecture • ${subtopic.dateString}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF64748B)
                        )

                        // Checkmark status
                        if (isCompleted) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "पूर्ण हुआ",
                                tint = Color(0xFF10B981), // Green
                                modifier = Modifier.size(18.dp)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.CheckCircleOutline,
                                contentDescription = "शेष",
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    // Subtopic Title
                    Text(
                        text = subtopic.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        lineHeight = 19.sp,
                        color = Color(0xFF0F172A),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    // Duration
                    Text(
                        text = subtopic.duration,
                        fontSize = 12.sp,
                        color = Color(0xFF64748B),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Buttons Row under video: Notes & Quiz + Watch (download button removed as requested)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Notes & Quiz Button (as requested: button for quiz and notes under the video)
                Button(
                    onClick = onViewNotes,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF1F5F9),
                        contentColor = Color(0xFF1E293B)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp)
                        .testTag("notes_button_${subtopic.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = null,
                        tint = Color(0xFF1E293B),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Notes & Quiz",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        maxLines = 1
                    )
                }

                // Watch or Start Quiz Button
                Button(
                    onClick = if (hasVideo) onWatchVideo else onAttemptQuiz,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (hasVideo) Color(0xFFF1F5F9) else MaterialTheme.colorScheme.primary,
                        contentColor = if (hasVideo) Color(0xFF1E293B) else Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp)
                        .testTag("watch_button_${subtopic.id}")
                ) {
                    Icon(
                        imageVector = if (hasVideo) Icons.Default.PlayArrow else Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = if (hasVideo) Color(0xFFE11D48) else Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (hasVideo) "Watch" else "Start Quiz",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Bottom Highlight Banner: Subtopic Quiz & "Attempt DPP >" (matching Screenshot 2)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFF8FAFC))
                    .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(10.dp))
                    .clickable(onClick = onAttemptQuiz)
                    .padding(horizontal = 12.dp, vertical = 10.dp)
                    .testTag("attempt_dpp_button_${subtopic.id}")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = WisdomGold,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "${subtopic.tag}: DPP अभ्यास (${subtopic.questions.size} प्रश्न)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF334155),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "Attempt DPP",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
        }
    }
}
