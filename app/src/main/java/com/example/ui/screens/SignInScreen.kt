package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.QuizViewModel
import com.example.ui.theme.IndigoSecondary
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.WisdomGold

@Composable
fun SignInScreen(
    viewModel: QuizViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isOnline by viewModel.isOnline.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Student ID & Password, 1 = Google Sign-In
    var isRegisterMode by remember { mutableStateOf(false) }

    var studentIdInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF1E3A8A), IndigoSecondary)
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "पीछे जाएं",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "विद्यार्थी लॉगिन एवं प्रगति सिंक",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                    Text(
                        text = "बिहार लाइब्रेरियन परीक्षा 2026",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Online / Offline Status Badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isOnline) Color(0xFFDCFCE7) else Color(0xFFF1F5F9))
                    .border(
                        1.dp,
                        if (isOnline) Color(0xFF86EFAC) else Color(0xFFCBD5E1),
                        RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Icon(
                    imageVector = if (isOnline) Icons.Default.CloudDone else Icons.Default.CloudOff,
                    contentDescription = null,
                    tint = if (isOnline) Color(0xFF16A34A) else Color(0xFF64748B),
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = if (isOnline) "🟢 इंटरनेट उपलब्ध (क्लाउड सिंक सक्रिय)" else "⚪ ऑफलाइन मोड (ऑनलाइन आने पर डेटा स्वतः सिंक होगा)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isOnline) Color(0xFF15803D) else Color(0xFF475569)
                )
            }

            // Benefits highlight card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDE68A)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "📱 नया डिवाइस या ऐप अपडेट? आपकी प्रगति सुरक्षित रहेगी!",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF92400E)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "लॉगिन करने के बाद यदि आप दूसरा फोन बदलते हैं या ऐप का नया अपडेट आता है, तो आपके द्वारा हल किए गए सभी 50+ प्रश्न, सटीकता और बुकमार्क्स तुरंत सुरक्षित रूप से पुनर्स्थापित (Restore) हो जाएंगे।",
                        fontSize = 12.5.sp,
                        color = Color(0xFF78350F),
                        lineHeight = 18.sp
                    )
                }
            }

            // Already logged in state
            if (currentUser != null) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE0E7FF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = null,
                                tint = IndigoSecondary,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        Text(
                            text = "आप पहले से लॉगिन हैं!",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFF1E293B)
                        )
                        Text(
                            text = currentUser?.displayName ?: "",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = IndigoSecondary
                        )
                        Text(
                            text = "खाता ID: ${currentUser?.emailOrId}",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                viewModel.syncWithCloudNow()
                                if (isOnline) {
                                    Toast.makeText(context, "क्लाउड सिंक सफलतापूर्वक शुरू हुआ!", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "ऑफलाइन: प्रगति फोन में सुरक्षित है। इंटरनेट आते ही स्वतः सिंक होगी।", Toast.LENGTH_LONG).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("sync_now_button")
                        ) {
                            Text("☁️ अपनी प्रगति अभी सिंक करें (Sync Now)", fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = { viewModel.signOut() },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("लॉग आउट करें (Log Out)", color = Color(0xFFDC2626))
                        }
                    }
                }
            } else {
                // Login Mode Tabs
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color(0xFFF1F5F9),
                    contentColor = IndigoSecondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("🔑 छात्र ID व पासवर्ड", fontWeight = FontWeight.SemiBold, fontSize = 13.sp) }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("🌐 Google लॉगिन", fontWeight = FontWeight.SemiBold, fontSize = 13.sp) }
                    )
                }

                // Error message
                if (errorMessage != null) {
                    Text(
                        text = errorMessage ?: "",
                        color = Color(0xFFDC2626),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }

                if (selectedTab == 0) {
                    // Student ID & Password Form
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Text(
                                text = if (isRegisterMode) "नया विद्यार्थी खाता बनाएं" else "छात्र ID व पासवर्ड से लॉगिन करें",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color(0xFF1E293B)
                            )

                            OutlinedTextField(
                                value = studentIdInput,
                                onValueChange = {
                                    studentIdInput = it
                                    errorMessage = null
                                },
                                label = { Text("छात्र ID / रोल नंबर / मोबाइल नंबर") },
                                leadingIcon = {
                                    Icon(Icons.Default.Person, contentDescription = null, tint = IndigoSecondary)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("student_id_input"),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true
                            )

                            OutlinedTextField(
                                value = passwordInput,
                                onValueChange = {
                                    passwordInput = it
                                    errorMessage = null
                                },
                                label = { Text("पासवर्ड") },
                                leadingIcon = {
                                    Icon(Icons.Default.Lock, contentDescription = null, tint = IndigoSecondary)
                                },
                                trailingIcon = {
                                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                        Icon(
                                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                            contentDescription = "पासवर्ड देखें"
                                        )
                                    }
                                },
                                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("student_password_input"),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true
                            )

                            Button(
                                onClick = {
                                    isLoading = true
                                    errorMessage = null
                                    viewModel.signInWithStudentId(
                                        studentId = studentIdInput,
                                        password = passwordInput,
                                        isRegister = isRegisterMode,
                                        onComplete = { success, msg ->
                                            isLoading = false
                                            if (success) {
                                                Toast.makeText(context, "सफलतापूर्वक लॉगिन हुआ! पुरानी प्रगति बहाल की गई।", Toast.LENGTH_SHORT).show()
                                                viewModel.navigateBack()
                                            } else {
                                                errorMessage = msg
                                            }
                                        }
                                    )
                                },
                                enabled = !isLoading && studentIdInput.isNotBlank() && passwordInput.isNotBlank(),
                                colors = ButtonDefaults.buttonColors(containerColor = IndigoSecondary),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .testTag("login_submit_button")
                            ) {
                                if (isLoading) {
                                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                                } else {
                                    Text(
                                        text = if (isRegisterMode) "खाता बनाएं एवं प्रगति सिंक करें" else "लॉगिन करें (प्रगति बहाल करें)",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = if (isRegisterMode) "पहले से खाता है? " else "नया विद्यार्थी हैं? ",
                                    color = Color(0xFF64748B),
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = if (isRegisterMode) "लॉगिन करें" else "नया खाता बनाएं",
                                    color = SaffronPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    modifier = Modifier.clickable {
                                        isRegisterMode = !isRegisterMode
                                        errorMessage = null
                                    }
                                )
                            }
                        }
                    }
                } else {
                    // Google Sign-In Option
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "एक-टैप में Google से लॉगिन करें",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color(0xFF1E293B)
                            )
                            Text(
                                text = "अपने Google अकाउंट से लॉगिन करने पर आपका सारा डेटा क्लाउड में सुरक्षित हो जाएगा। नया फोन लेने पर बस उसी Google अकाउंट से लॉगिन करें और आपकी सारी प्रगति तुरंत आ जाएगी।",
                                fontSize = 13.sp,
                                color = Color(0xFF64748B),
                                textAlign = TextAlign.Center
                            )

                            Button(
                                onClick = {
                                    isLoading = true
                                    errorMessage = null
                                    viewModel.signInWithGoogle(
                                        onComplete = { success, msg ->
                                            isLoading = false
                                            if (success) {
                                                Toast.makeText(context, "Google से लॉगिन सफल! आपकी प्रगति बहाल कर दी गई।", Toast.LENGTH_SHORT).show()
                                                viewModel.navigateBack()
                                            } else if (msg != null) {
                                                errorMessage = msg
                                            }
                                        }
                                    )
                                },
                                enabled = !isLoading,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF4285F4),
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp)
                                    .testTag("google_signin_button")
                            ) {
                                if (isLoading) {
                                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                                } else {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "G",
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 20.sp,
                                            color = Color.White
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(
                                            text = "Sign in with Google",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Continue as Guest button
                Text(
                    text = "अभी लॉगिन नहीं करना चाहते? बाद में करें",
                    color = Color(0xFF64748B),
                    fontSize = 13.sp,
                    modifier = Modifier.clickable { viewModel.navigateBack() }
                )
            }
        }
    }
}
