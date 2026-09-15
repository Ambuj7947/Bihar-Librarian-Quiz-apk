package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.SendTimeExtension
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.DefaultQuestions
import com.example.data.model.QuestionEntity
import com.example.data.model.StudyMaterialEntity
import com.example.ui.AppScreen
import com.example.ui.QuizViewModel
import com.example.ui.theme.CorrectAnswerGreen
import com.example.ui.theme.IndigoSecondary
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.WisdomGold
import com.example.util.ContentSeparator
import com.example.util.ParsedQuestionItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentHubScreen(
    viewModel: QuizViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val statusMessage by viewModel.statusMessage.collectAsState()
    val savedMaterials by viewModel.allStudyMaterials.collectAsState()

    LaunchedEffect(statusMessage) {
        statusMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearStatusMessage()
        }
    }

    // Main Mode: 0 = सामग्री जोड़ें (Add / Separate Content), 1 = सहेजी गई सामग्री (Saved Materials List)
    var selectedScreenTab by remember { mutableIntStateOf(0) }

    // Section 1: Unit & Subtopic Selection
    val unitOptions = listOf(
        DefaultQuestions.UNIT_1,
        DefaultQuestions.UNIT_2,
        DefaultQuestions.UNIT_3,
        DefaultQuestions.UNIT_4,
        DefaultQuestions.UNIT_5
    )
    var selectedUnit by remember { mutableStateOf(unitOptions[0]) }
    var isUnitDropdownExpanded by remember { mutableStateOf(false) }
    var subTopicText by remember { mutableStateOf("") }

    // Section 2: YouTube Lecture Link
    var youtubeUrl by remember { mutableStateOf("https://youtube.com/watch?v=blat_lib_sc_unit01_lec03") }
    var lectureTitle by remember { mutableStateOf("इकाई 1: पुस्तकालय विज्ञान के 5 सूत्र (Dr. S.R. Ranganathan 5 Laws)") }
    var channelName by remember { mutableStateOf("BLAT मेंटर बिहार • 1080p HD") }
    var timestampNotes by remember { mutableStateOf("05:20 - प्रथम सूत्र, 14:10 - द्वितीय सूत्र, 25:30 - महत्वपूर्ण बहुविकल्पीय प्रश्न") }

    val detectedVideoId = remember(youtubeUrl) {
        ContentSeparator.extractYouTubeVideoId(youtubeUrl)
    }

    // Section 3: Document & Text / Smart Separator
    var rawInputText by remember { mutableStateOf("") }
    var separatedNotesText by remember { mutableStateOf("") }
    val parsedQuestions = remember { mutableStateListOf<ParsedQuestionItem>() }
    var hasSeparatedContent by remember { mutableStateOf(false) }
    var selectedSeparatedTab by remember { mutableIntStateOf(0) } // 0 = Notes, 1 = Questions

    // Post-Save Dialog state
    var showSuccessDialog by remember { mutableStateOf(false) }
    var latestSavedQuestions by remember { mutableStateOf<List<QuestionEntity>>(emptyList()) }

    // File picker launcher for .txt / .doc / .pdf text
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { fileUri ->
            try {
                context.contentResolver.openInputStream(fileUri)?.bufferedReader()?.use { reader ->
                    val content = reader.readText()
                    rawInputText = content
                    val result = ContentSeparator.separateNotesAndQuestions(content)
                    separatedNotesText = result.notesText
                    parsedQuestions.clear()
                    parsedQuestions.addAll(result.questions)
                    hasSeparatedContent = true
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("फाइल सफलतापूर्वक लोड की गई: नोट्स और ${result.questions.size} प्रश्न अलग किए गए!")
                    }
                }
            } catch (e: Exception) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("फाइल पढ़ने में त्रुटि: कृपया टेक्स्ट कॉपी-पेस्ट करें।")
                }
            }
        }
    }

    // Function to run the separation
    fun performSeparation(textToSeparate: String) {
        if (textToSeparate.isBlank()) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("कृपया पहले नोट्स या प्रश्न सामग्री दर्ज करें।")
            }
            return
        }
        val result = ContentSeparator.separateNotesAndQuestions(textToSeparate)
        separatedNotesText = result.notesText
        parsedQuestions.clear()
        parsedQuestions.addAll(result.questions)
        hasSeparatedContent = true
        coroutineScope.launch {
            snackbarHostState.showSnackbar("सफलतापूर्वक अलग किया गया: नोट्स और ${result.questions.size} क्विज़ प्रश्न तैयार हैं!")
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF151C27))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(
                            onClick = { viewModel.navigateBack() },
                            modifier = Modifier.testTag("content_hub_back_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "वापस जाएं",
                                tint = Color.White
                            )
                        }

                        Column {
                            Text(
                                text = "सामग्री / प्रश्न जोड़ें",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 19.sp
                                ),
                                color = Color.White
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(7.dp)
                                        .background(Color(0xFFEE671C), CircleShape)
                                )
                                Text(
                                    text = "BLET पात्रता परीक्षा तैयारी",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = WisdomGold
                                )
                            }
                        }
                    }

                    // Profile / Badge Icon
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(SaffronPrimary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PostAdd,
                            contentDescription = null,
                            tint = Color(0xFF351000),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Top View Switcher Tabs (सामग्री जोड़ें vs सहेजी गई सामग्री)
                TabRow(
                    selectedTabIndex = selectedScreenTab,
                    containerColor = Color(0xFF151C27),
                    contentColor = WisdomGold
                ) {
                    Tab(
                        selected = selectedScreenTab == 0,
                        onClick = { selectedScreenTab = 0 },
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.AddCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                                Text("सामग्री निर्माता केंद्र", fontWeight = FontWeight.SemiBold)
                            }
                        }
                    )
                    Tab(
                        selected = selectedScreenTab == 1,
                        onClick = { selectedScreenTab = 1 },
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp))
                                Text("सहेजी गई सामग्री (${savedMaterials.size})", fontWeight = FontWeight.SemiBold)
                            }
                        }
                    )
                }
            }
        },
        bottomBar = {
            if (selectedScreenTab == 0) {
                // Sticky Action Bar at the bottom
                Card(
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF151C27)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("ड्राफ्ट स्थानीय रूप से सहेजा गया।")
                                }
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color(0xFFDCE2F2)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("save_draft_button")
                        ) {
                            Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("ड्राफ्ट रखें", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                        }

                        Button(
                            onClick = {
                                if (!hasSeparatedContent && rawInputText.isNotBlank()) {
                                    performSeparation(rawInputText)
                                }
                                viewModel.saveContentHubData(
                                    unitCategory = selectedUnit,
                                    subTopic = subTopicText,
                                    youtubeUrl = youtubeUrl,
                                    youtubeTitle = lectureTitle,
                                    timestampNotes = timestampNotes,
                                    notesContent = separatedNotesText.ifBlank { rawInputText },
                                    parsedQuestions = parsedQuestions,
                                    onSaved = { savedList ->
                                        latestSavedQuestions = savedList
                                        showSuccessDialog = true
                                    }
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFD35400),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1.5f)
                                .height(48.dp)
                                .testTag("publish_content_button")
                        ) {
                            Icon(Icons.Default.SendTimeExtension, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("सामग्री प्रकाशित करें", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        },
        containerColor = Color(0xFF0D141E)
    ) { paddingValues ->
        if (selectedScreenTab == 1) {
            // View Saved Materials List
            SavedMaterialsView(
                savedMaterials = savedMaterials,
                onPlayVideo = { url ->
                    if (url.isNotBlank()) {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("वीडियो खोलने में असमर्थ")
                            }
                        }
                    }
                },
                onDelete = { material ->
                    viewModel.deleteStudyMaterial(material)
                },
                onStartQuizForUnit = { unit ->
                    viewModel.startCategoryQuiz(unit)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            )
        } else {
            // Main Content Creator Hub Screen
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { Spacer(modifier = Modifier.height(4.dp)) }

                // 1. Motivational Academic Banner
                item {
                    AcademicBannerCard()
                }

                // 2. Section 1: Target Unit / Topic Selector
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AccountTree,
                                        contentDescription = null,
                                        tint = Color(0xFFC85A17),
                                        modifier = Modifier.size(22.dp)
                                    )
                                    Text(
                                        text = "इकाई चुनें (Select Unit)",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = Color(0xFF1A202C)
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .background(Color(0xFFFFF5EE), RoundedCornerShape(6.dp))
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Text(
                                        text = "कुल 5 इकाइयाँ",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                                        color = Color(0xFFC85A17)
                                    )
                                }
                            }

                            Text(
                                text = "जिस इकाई के तहत आप वीडियो, नोट्स व प्रश्न जोड़ना चाहते हैं उसे चुनें:",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF718096)
                            )

                            // Dropdown selector
                            ExposedDropdownMenuBox(
                                expanded = isUnitDropdownExpanded,
                                onExpandedChange = { isUnitDropdownExpanded = !isUnitDropdownExpanded },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                OutlinedTextField(
                                    value = selectedUnit,
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isUnitDropdownExpanded) },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = Color(0xFFF8FAFC),
                                        unfocusedContainerColor = Color(0xFFF8FAFC),
                                        focusedTextColor = Color(0xFF1A202C),
                                        unfocusedTextColor = Color(0xFF1A202C),
                                        focusedBorderColor = Color(0xFFC85A17),
                                        unfocusedBorderColor = Color(0xFFE2E8F0)
                                    ),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .menuAnchor()
                                        .fillMaxWidth()
                                        .testTag("unit_selector_dropdown")
                                )

                                ExposedDropdownMenu(
                                    expanded = isUnitDropdownExpanded,
                                    onDismissRequest = { isUnitDropdownExpanded = false }
                                ) {
                                    unitOptions.forEach { unit ->
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = unit,
                                                    style = MaterialTheme.typography.bodyMedium.copy(
                                                        fontWeight = if (unit == selectedUnit) FontWeight.Bold else FontWeight.Normal
                                                    )
                                                )
                                            },
                                            onClick = {
                                                selectedUnit = unit
                                                isUnitDropdownExpanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            // Topic Input Field (User custom input)
                            OutlinedTextField(
                                value = subTopicText,
                                onValueChange = { subTopicText = it },
                                label = { Text("विषय / टॉपिक का नाम (Topic Name)") },
                                placeholder = { Text("उदा. रंगनाथन के 5 सूत्र, DDC वर्गीकरण...") },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color(0xFFF8FAFC),
                                    unfocusedContainerColor = Color(0xFFF8FAFC),
                                    focusedTextColor = Color(0xFF1A202C),
                                    unfocusedTextColor = Color(0xFF1A202C),
                                    focusedBorderColor = Color(0xFFC85A17),
                                    unfocusedBorderColor = Color(0xFFE2E8F0)
                                ),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("topic_name_input")
                            )
                        }
                    }
                }

                // 3. Section 2: YouTube Lecture Video Section
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
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
                                            .size(32.dp)
                                            .background(Color(0xFFFEE2E2), RoundedCornerShape(8.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.SmartDisplay,
                                            contentDescription = null,
                                            tint = Color(0xFFDC2626),
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }

                                    Column {
                                        Text(
                                            text = "यूट्यूब वीडियो क्लास",
                                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                            color = Color(0xFF1A202C)
                                        )
                                        Text(
                                            text = "YouTube Video Lecture Link",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFF718096)
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(WisdomGold, CircleShape)
                                )
                            }

                            // URL input with Paste button
                            OutlinedTextField(
                                value = youtubeUrl,
                                onValueChange = { youtubeUrl = it },
                                placeholder = { Text("https://youtube.com/watch?v=... या लिंक पेस्ट करें") },
                                leadingIcon = {
                                    Icon(Icons.Default.Link, contentDescription = null, tint = Color(0xFF718096))
                                },
                                trailingIcon = {
                                    TextButton(
                                        onClick = {
                                            val clipboardText = clipboardManager.getText()?.text
                                            if (!clipboardText.isNullOrBlank()) {
                                                youtubeUrl = clipboardText
                                            }
                                        }
                                    ) {
                                        Text("पेस्ट", color = Color(0xFFC85A17), fontWeight = FontWeight.Bold)
                                    }
                                },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color(0xFFF8FAFC),
                                    unfocusedContainerColor = Color(0xFFF8FAFC),
                                    focusedTextColor = Color(0xFF1A202C),
                                    unfocusedTextColor = Color(0xFF1A202C),
                                    focusedBorderColor = Color(0xFFC85A17),
                                    unfocusedBorderColor = Color(0xFFE2E8F0)
                                ),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("youtube_url_input")
                            )

                            // Video Detected Preview Card
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(150.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0xFF1E293B)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (detectedVideoId != null) {
                                            val thumbUrl = ContentSeparator.getYouTubeThumbnailUrl(detectedVideoId)
                                            AsyncImage(
                                                model = thumbUrl,
                                                contentDescription = "Video Thumbnail",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier.fillMaxSize()
                                            )
                                        } else {
                                            // Stylized fallback academic backdrop
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .background(
                                                        Brush.verticalGradient(
                                                            listOf(Color(0xFF1E293B), Color(0xFF0F172A))
                                                        )
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.SmartDisplay,
                                                    contentDescription = null,
                                                    tint = Color.White.copy(alpha = 0.3f),
                                                    modifier = Modifier.size(64.dp)
                                                )
                                            }
                                        }

                                        // Play Overlay Button
                                        Box(
                                            modifier = Modifier
                                                .size(48.dp)
                                                .background(Color(0xFFDC2626), CircleShape)
                                                .clickable {
                                                    if (youtubeUrl.isNotBlank()) {
                                                        try {
                                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
                                                            context.startActivity(intent)
                                                        } catch (e: Exception) {
                                                            coroutineScope.launch {
                                                                snackbarHostState.showSnackbar("वीडियो लिंक अमान्य है")
                                                            }
                                                        }
                                                    }
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.PlayArrow,
                                                contentDescription = "वीडियो चलाएं",
                                                tint = Color.White,
                                                modifier = Modifier.size(28.dp)
                                            )
                                        }

                                        // Status badge
                                        Box(
                                            modifier = Modifier
                                                .align(Alignment.TopStart)
                                                .padding(8.dp)
                                                .background(Color(0xFF059669), RoundedCornerShape(12.dp))
                                                .padding(horizontal = 8.dp, vertical = 2.dp)
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                                            ) {
                                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                                                Text("सत्यापित वीडियो", style = MaterialTheme.typography.labelSmall, color = Color.White)
                                            }
                                        }

                                        // Duration Tag
                                        Box(
                                            modifier = Modifier
                                                .align(Alignment.BottomEnd)
                                                .padding(8.dp)
                                                .background(Color.Black.copy(alpha = 0.8f), RoundedCornerShape(4.dp))
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text("42:15 मिनट", style = MaterialTheme.typography.labelSmall, color = Color.White)
                                        }
                                    }

                                    Column(modifier = Modifier.padding(horizontal = 4.dp)) {
                                        Text(
                                            text = lectureTitle,
                                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                            color = Color.White,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = "चैनल: $channelName",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFFCBD5E1)
                                        )
                                    }
                                }
                            }

                            // Timestamp Notes / Video Description
                            OutlinedTextField(
                                value = timestampNotes,
                                onValueChange = { timestampNotes = it },
                                label = { Text("लेक्चर विवरण / टाइमस्टैम्प नोट्स (वैकल्पिक)") },
                                placeholder = { Text("05:20 - प्रथम सूत्र, 14:10 - द्वितीय सूत्र...") },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color(0xFFF8FAFC),
                                    unfocusedContainerColor = Color(0xFFF8FAFC),
                                    focusedTextColor = Color(0xFF1A202C),
                                    unfocusedTextColor = Color(0xFF1A202C),
                                    focusedBorderColor = Color(0xFFC85A17),
                                    unfocusedBorderColor = Color(0xFFE2E8F0)
                                ),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                // 4. Section 3: Study Notes & Document Upload / Smart Separator Section
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
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
                                            .size(32.dp)
                                            .background(Color(0xFFEFF6FF), RoundedCornerShape(8.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.MenuBook,
                                            contentDescription = null,
                                            tint = Color(0xFF2563EB),
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }

                                    Column {
                                        Text(
                                            text = "नोट्स एवं क्विज़ प्रश्न अलग करें",
                                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                            color = Color(0xFF1A202C)
                                        )
                                        Text(
                                            text = "Smart Notes & Q&A Auto Separator",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFF718096)
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .background(Color(0xFFEFF6FF), RoundedCornerShape(6.dp))
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Text(
                                        text = if (hasSeparatedContent) "${parsedQuestions.size} प्रश्न अलग किए" else "सिंगल फाइल / टेक्स्ट",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                                        color = Color(0xFF1E3A8A)
                                    )
                                }
                            }

                            Text(
                                text = "एक ही फाइल या टेक्स्ट में नोट्स और प्रश्न-उत्तर दोनों प्रदान करें। सिस्टम स्वचालित रूप से नोट्स और प्रश्नों को अलग कर देगा और तुरंत क्विज़ बना देगा।",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF4A5568)
                            )

                            // Dropzone / File Browse or Sample Button
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFBFD)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(48.dp)
                                            .background(Color(0xFFFFF5EE), CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CloudUpload,
                                            contentDescription = null,
                                            tint = Color(0xFFC85A17),
                                            modifier = Modifier.size(26.dp)
                                        )
                                    }

                                    Text(
                                        text = "सिंगल फाइल (TXT/DOC/PDF) अपलोड करें या टेक्स्ट लिखें",
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                        color = Color(0xFF1A202C),
                                        textAlign = TextAlign.Center
                                    )

                                    Text(
                                        text = "नोट्स और प्रश्न-उत्तर दोनों एक साथ शामिल हो सकते हैं",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFF718096),
                                        textAlign = TextAlign.Center
                                    )

                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                        modifier = Modifier.padding(top = 4.dp)
                                    ) {
                                        Button(
                                            onClick = { filePickerLauncher.launch("*/*") },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFF172E54),
                                                contentColor = Color.White
                                            ),
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier.testTag("browse_file_button")
                                        ) {
                                            Icon(Icons.Default.FolderOpen, contentDescription = null, modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("फाइल ब्राउज़ करें", fontSize = 12.sp)
                                        }

                                        OutlinedButton(
                                            onClick = {
                                                val sample = ContentSeparator.getSampleContent()
                                                rawInputText = sample
                                                performSeparation(sample)
                                            },
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                contentColor = Color(0xFFC85A17)
                                            ),
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier.testTag("load_sample_button")
                                        ) {
                                            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("सैंपल डेटा लोड करें", fontSize = 12.sp)
                                        }
                                    }
                                }
                            }

                            // Large Text Area for Raw Notes + Q&A
                            OutlinedTextField(
                                value = rawInputText,
                                onValueChange = {
                                    rawInputText = it
                                    hasSeparatedContent = false
                                },
                                label = { Text("नोट्स एवं प्रश्न-उत्तर सामग्री यहाँ पेस्ट करें") },
                                placeholder = {
                                    Text(
                                        "उदाहरण:\n" +
                                                "# नोट्स: डॉ. रंगनाथन ने 1928 में 5 सूत्रों का प्रतिपादन किया...\n\n" +
                                                "प्र. 1. पांच सूत्रों का प्रतिपादन किसने किया?\n" +
                                                "(A) डॉ. रंगनाथन\n(B) मेलविल डेवी\n(C) कटर\n(D) सेयर्स\nउत्तर: (A)\nव्याख्या: 1928 में मीनाक्षी कॉलेज में।"
                                    )
                                },
                                minLines = 5,
                                maxLines = 12,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color(0xFFF8FAFC),
                                    unfocusedContainerColor = Color(0xFFF8FAFC),
                                    focusedTextColor = Color(0xFF1A202C),
                                    unfocusedTextColor = Color(0xFF1A202C),
                                    focusedBorderColor = Color(0xFFC85A17),
                                    unfocusedBorderColor = Color(0xFFE2E8F0)
                                ),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("raw_content_input")
                            )

                            // Prominent Trigger Button: "स्वचालित अलग करें (Separate Notes & Quiz)"
                            Button(
                                onClick = { performSeparation(rawInputText) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFC85A17),
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(46.dp)
                                    .testTag("auto_separate_button")
                            ) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "नोट्स और प्रश्न अलग करें एवं क्विज़ बनाएं",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }

                // 5. Separated Content Preview (Notes vs Generated Quizzes)
                if (hasSeparatedContent || separatedNotesText.isNotBlank() || parsedQuestions.isNotEmpty()) {
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = "स्वचालित अलग किया गया परिणाम (Separated Result)",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color(0xFF1A202C)
                                )

                                TabRow(
                                    selectedTabIndex = selectedSeparatedTab,
                                    containerColor = Color(0xFFF1F5F9),
                                    contentColor = Color(0xFFC85A17),
                                    modifier = Modifier.clip(RoundedCornerShape(10.dp))
                                ) {
                                    Tab(
                                        selected = selectedSeparatedTab == 0,
                                        onClick = { selectedSeparatedTab = 0 },
                                        text = {
                                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                                Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp))
                                                Text("अलग किए गए नोट्स", fontWeight = FontWeight.SemiBold)
                                            }
                                        }
                                    )
                                    Tab(
                                        selected = selectedSeparatedTab == 1,
                                        onClick = { selectedSeparatedTab = 1 },
                                        text = {
                                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                                Icon(Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(16.dp))
                                                Text("क्विज़ प्रश्न (${parsedQuestions.size})", fontWeight = FontWeight.SemiBold)
                                            }
                                        }
                                    )
                                }

                                if (selectedSeparatedTab == 0) {
                                    // Notes Preview & Editor
                                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "अध्ययन सामग्री / थ्योरी नोट्स:",
                                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                                color = Color(0xFF4A5568)
                                            )
                                            Text(
                                                text = "${separatedNotesText.length} वर्ण",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = Color(0xFF718096)
                                            )
                                        }

                                        OutlinedTextField(
                                            value = separatedNotesText,
                                            onValueChange = { separatedNotesText = it },
                                            minLines = 4,
                                            maxLines = 10,
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedContainerColor = Color(0xFFF8FAFC),
                                                unfocusedContainerColor = Color(0xFFF8FAFC),
                                                focusedTextColor = Color(0xFF1A202C),
                                                unfocusedTextColor = Color(0xFF1A202C),
                                                focusedBorderColor = Color(0xFF2563EB),
                                                unfocusedBorderColor = Color(0xFFE2E8F0)
                                            ),
                                            shape = RoundedCornerShape(10.dp),
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                } else {
                                    // Generated Quiz Questions List
                                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                        parsedQuestions.forEachIndexed { index, item ->
                                            ParsedQuestionCard(
                                                index = index + 1,
                                                item = item,
                                                onCorrectOptionChange = { newCorrect ->
                                                    parsedQuestions[index] = item.copy(correctOption = newCorrect)
                                                },
                                                onDelete = {
                                                    parsedQuestions.removeAt(index)
                                                }
                                            )
                                        }

                                        // Add another question button
                                        OutlinedButton(
                                            onClick = {
                                                parsedQuestions.add(
                                                    ParsedQuestionItem(
                                                        questionHindi = "नया प्रश्न यहाँ लिखें...",
                                                        optionA = "विकल्प A",
                                                        optionB = "विकल्प B",
                                                        optionC = "विकल्प C",
                                                        optionD = "विकल्प D",
                                                        correctOption = 1,
                                                        explanationHindi = "व्याख्या यहाँ दर्ज करें।"
                                                    )
                                                )
                                            },
                                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFC85A17)),
                                            shape = RoundedCornerShape(10.dp),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Icon(Icons.Default.AddCircle, contentDescription = null, modifier = Modifier.size(18.dp))
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("+ नया प्रश्न जोड़ें (Add Question)", fontWeight = FontWeight.SemiBold)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // 6. Summary Card & Submission Action
                item {
                    SummaryPreparationCard(
                        videoCount = if (detectedVideoId != null || youtubeUrl.isNotBlank()) 1 else 0,
                        notesLength = separatedNotesText.length,
                        quizCount = parsedQuestions.size
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }

    // Success Dialog with instant quiz trigger
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = CorrectAnswerGreen,
                    modifier = Modifier.size(44.dp)
                )
            },
            title = {
                Text(
                    text = "सामग्री सफलतापूर्वक प्रकाशित!",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "आपके द्वारा जोड़े गए ${latestSavedQuestions.size} प्रश्न क्विज़ बैंक में सम्मिलित कर दिए गए हैं और नोट्स सुरक्षित कर लिए गए हैं।",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = Color(0xFFDCE2F2)
                    )
                    Text(
                        text = "इकाई: $selectedUnit",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = WisdomGold,
                        textAlign = TextAlign.Center
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        viewModel.startQuizWithCustomQuestions(
                            title = "नया जोड़ा गया टेस्ट: $subTopicText",
                            category = selectedUnit,
                            questions = latestSavedQuestions
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SaffronPrimary,
                        contentColor = Color(0xFF351000)
                    )
                ) {
                    Icon(Icons.Default.PlayCircle, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("🎯 अभी यह क्विज़ शुरू करें", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showSuccessDialog = false
                        viewModel.navigateTo(AppScreen.Home)
                    }
                ) {
                    Text("होम स्क्रीन पर जाएं", color = Color(0xFFDCE2F2))
                }
            },
            containerColor = Color(0xFF151C27)
        )
    }
}

@Composable
fun AcademicBannerCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF172E54)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(WisdomGold.copy(alpha = 0.2f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PostAdd,
                        contentDescription = null,
                        tint = WisdomGold,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column {
                    Text(
                        text = "सामग्री निर्माता केंद्र",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Text(
                        text = "BLET पाठ्यक्रम में नई अध्ययन सामग्री जोड़ें",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFCBD5E1)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .background(WisdomGold, RoundedCornerShape(12.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "सत्र 2025",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF351000)
                )
            }
        }
    }
}

@Composable
fun ParsedQuestionCard(
    index: Int,
    item: ParsedQuestionItem,
    onCorrectOptionChange: (Int) -> Unit,
    onDelete: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFBFD)),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
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
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color(0xFFFEF3C7), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$index",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFFD97706)
                        )
                    }
                    Text(
                        text = "प्रश्न संख्या: 0$index",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF1A202C)
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "हटाएं", tint = Color(0xFFEF4444), modifier = Modifier.size(18.dp))
                }
            }

            // Question prompt
            Text(
                text = item.questionHindi,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = Color(0xFF1A202C)
            )

            // 4 Options
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf(
                    Triple(1, "A", item.optionA),
                    Triple(2, "B", item.optionB),
                    Triple(3, "C", item.optionC),
                    Triple(4, "D", item.optionD)
                ).forEach { (optNum, letter, text) ->
                    val isCorrect = item.correctOption == optNum
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isCorrect) Color(0xFFECFDF5) else Color(0xFFF1F5F9))
                            .clickable { onCorrectOptionChange(optNum) }
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(
                                        if (isCorrect) Color(0xFF10B981) else Color(0xFFE2E8F0),
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = letter,
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = if (isCorrect) Color.White else Color(0xFF475569)
                                )
                            }

                            Text(
                                text = text,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = if (isCorrect) FontWeight.Bold else FontWeight.Normal
                                ),
                                color = if (isCorrect) Color(0xFF065F46) else Color(0xFF1A202C)
                            )
                        }

                        if (isCorrect) {
                            Box(
                                modifier = Modifier
                                    .background(Color(0xFF10B981), RoundedCornerShape(12.dp))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                                    Text("सही उत्तर", style = MaterialTheme.typography.labelSmall, color = Color.White)
                                }
                            }
                        } else {
                            Icon(
                                imageVector = Icons.Default.RadioButtonUnchecked,
                                contentDescription = "सही चुनें",
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            // Explanation box if present
            if (item.explanationHindi.isNotBlank()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFFFBEB), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        text = "व्याख्या: ${item.explanationHindi}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF92400E)
                    )
                }
            }
        }
    }
}

@Composable
fun SummaryPreparationCard(
    videoCount: Int,
    notesLength: Int,
    quizCount: Int
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF151C27)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = WisdomGold, modifier = Modifier.size(20.dp))
                    Text(
                        text = "तैयारी सारांश",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                }

                Box(
                    modifier = Modifier
                        .background(Color(0xFF064E3B), RoundedCornerShape(12.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "प्रकाशन हेतु तैयार",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = Color(0xFF34D399)
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SummaryBox(
                    count = "$videoCount",
                    label = "वीडियो क्लास",
                    color = SaffronPrimary,
                    modifier = Modifier.weight(1f)
                )
                SummaryBox(
                    count = if (notesLength > 0) "उपलब्ध" else "0",
                    label = "पीडीएफ / नोट्स",
                    color = WisdomGold,
                    modifier = Modifier.weight(1f)
                )
                SummaryBox(
                    count = "$quizCount",
                    label = "क्विज़ प्रश्न",
                    color = Color(0xFF34D399),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun SummaryBox(
    count: String,
    label: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color(0xFF232A36), RoundedCornerShape(10.dp))
            .padding(vertical = 10.dp, horizontal = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = count,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = color
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFFE0C0B2),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SavedMaterialsView(
    savedMaterials: List<StudyMaterialEntity>,
    onPlayVideo: (String) -> Unit,
    onDelete: (StudyMaterialEntity) -> Unit,
    onStartQuizForUnit: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (savedMaterials.isEmpty()) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MenuBook,
                    contentDescription = null,
                    tint = Color(0xFF475569),
                    modifier = Modifier.size(64.dp)
                )
                Text(
                    text = "अभी तक कोई सामग्री सहेजी नहीं गई है",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFFCBD5E1)
                )
                Text(
                    text = "'सामग्री निर्माता केंद्र' टैब पर जाएं और YouTube लिंक, नोट्स व प्रश्न जोड़ें!",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF94A3B8),
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(savedMaterials, key = { it.id }) { material ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF151C27)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = material.unitCategory,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = SaffronPrimary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = { onDelete(material) },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "हटाएं", tint = Color(0xFFEF4444), modifier = Modifier.size(16.dp))
                            }
                        }

                        if (material.subTopic.isNotBlank()) {
                            Text(
                                text = "उप-विषय: ${material.subTopic}",
                                style = MaterialTheme.typography.labelMedium,
                                color = WisdomGold
                            )
                        }

                        if (material.youtubeUrl.isNotBlank()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF1E293B))
                                    .clickable { onPlayVideo(material.youtubeUrl) }
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Default.SmartDisplay, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(22.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = material.youtubeTitle.ifBlank { "यूट्यूब वीडियो लेक्चर" },
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                        color = Color.White,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "वीडियो देखने के लिए टैप करें",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color(0xFF94A3B8)
                                    )
                                }
                                Icon(Icons.Default.OpenInNew, contentDescription = null, tint = Color(0xFF94A3B8), modifier = Modifier.size(16.dp))
                            }
                        }

                        if (material.notesContent.isNotBlank()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFF1E293B), RoundedCornerShape(8.dp))
                                    .padding(10.dp)
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "अध्ययन नोट्स:",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = Color(0xFF93C5FD)
                                    )
                                    Text(
                                        text = material.notesContent,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFFE2E8F0),
                                        maxLines = 4,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }

                        // Bottom Actions
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${material.questionsCount} क्विज़ प्रश्न शामिल",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF34D399)
                            )

                            Button(
                                onClick = { onStartQuizForUnit(material.unitCategory) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFC85A17),
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(34.dp)
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("क्विज़ खेलें", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
