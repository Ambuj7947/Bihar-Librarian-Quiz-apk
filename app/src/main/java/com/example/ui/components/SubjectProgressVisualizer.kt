package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.IndigoSecondary
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.WisdomGold

data class SubjectProgressData(
    val categoryName: String,
    val shortLabel: String,
    val totalQuestions: Int,
    val attemptedQuestions: Int,
    val correctQuestions: Int,
    val color: Color
) {
    val completionPercentage: Int
        get() = if (totalQuestions > 0) ((attemptedQuestions * 100) / totalQuestions).coerceIn(0, 100) else 0

    val accuracyPercentage: Int
        get() = if (attemptedQuestions > 0) ((correctQuestions * 100) / attemptedQuestions).coerceIn(0, 100) else 0
}

@Composable
fun SubjectProgressVisualizer(
    progressList: List<SubjectProgressData>,
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedViewMode by remember { mutableIntStateOf(0) } // 0 = Horizontal Bar Chart, 1 = Radial Donut / Breakdown

    val totalAllQuestions = progressList.sumOf { it.totalQuestions }
    val totalAllAttempted = progressList.sumOf { it.attemptedQuestions }
    val overallPercentage = if (totalAllQuestions > 0) ((totalAllAttempted * 100) / totalAllQuestions).coerceIn(0, 100) else 0

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(20.dp))
            .testTag("visual_summary_section")
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header with overall journey score
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(IndigoSecondary.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.TrendingUp,
                            contentDescription = null,
                            tint = IndigoSecondary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "पाठ्यक्रम प्रगति विश्लेषण (Visual Summary)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color(0xFF1E293B)
                        )
                        Text(
                            text = "इकाईवार तैयारी एवं सटीकता ट्रैकर",
                            fontSize = 11.5.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }

                // Overall badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFEFF6FF))
                        .border(1.dp, Color(0xFFBFDBFE), RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "$overallPercentage% पूर्ण",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Color(0xFF1D4ED8)
                    )
                }
            }

            // Tabs for toggling between Bar Chart vs Donut Distribution
            TabRow(
                selectedTabIndex = selectedViewMode,
                containerColor = Color(0xFFF8FAFC),
                contentColor = IndigoSecondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Tab(
                    selected = selectedViewMode == 0,
                    onClick = { selectedViewMode = 0 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.BarChart, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("बार चार्ट (प्रगति)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                )
                Tab(
                    selected = selectedViewMode == 1,
                    onClick = { selectedViewMode = 1 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.PieChart, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("पाई / डोनट (सटीकता)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                )
            }

            if (selectedViewMode == 0) {
                // Interactive Horizontal Bar Chart Mode (Recharts Style)
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    progressList.forEach { item ->
                        SubjectBarRow(
                            item = item,
                            onClick = { onCategoryClick(item.categoryName) }
                        )
                    }
                }
            } else {
                // Donut Chart & Detailed Metrics Mode
                RadialDonutSummary(
                    progressList = progressList,
                    overallPercentage = overallPercentage,
                    totalAttempted = totalAllAttempted,
                    totalQuestions = totalAllQuestions
                )
            }
        }
    }
}

@Composable
private fun SubjectBarRow(
    item: SubjectProgressData,
    onClick: () -> Unit
) {
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(item.completionPercentage) {
        animatedProgress.animateTo(
            targetValue = item.completionPercentage / 100f,
            animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(vertical = 4.dp, horizontal = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(item.color)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = item.shortLabel,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = Color(0xFF1E293B),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (item.totalQuestions > 0) {
                    Text(
                        text = "${item.attemptedQuestions}/${item.totalQuestions} प्रश्न",
                        fontSize = 11.5.sp,
                        color = Color(0xFF64748B)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${item.completionPercentage}%",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.5.sp,
                        color = item.color
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFFEFF6FF))
                            .padding(horizontal = 7.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "थ्योरी नोट्स",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1D4ED8)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = Color(0xFF94A3B8),
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Multi-layered visual progress bar (Attempted vs Remaining)
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        ) {
            val width = size.width
            val height = size.height

            // Background track
            drawRoundRect(
                color = Color(0xFFF1F5F9),
                size = Size(width, height),
                cornerRadius = CornerRadius(height / 2, height / 2)
            )

            // Active bar
            val fillWidth = width * animatedProgress.value
            if (fillWidth > 0f) {
                drawRoundRect(
                    brush = Brush.horizontalGradient(
                        colors = listOf(item.color.copy(alpha = 0.85f), item.color)
                    ),
                    size = Size(fillWidth, height),
                    cornerRadius = CornerRadius(height / 2, height / 2)
                )
            }
        }
    }
}

@Composable
private fun RadialDonutSummary(
    progressList: List<SubjectProgressData>,
    overallPercentage: Int,
    totalAttempted: Int,
    totalQuestions: Int
) {
    val animatedAngle = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animatedAngle.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing)
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Center Donut Chart Canvas
        Box(
            modifier = Modifier
                .size(120.dp)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(110.dp)) {
                val strokeWidth = 14.dp.toPx()
                val radius = (size.minDimension - strokeWidth) / 2
                val centerOffset = Offset(size.width / 2, size.height / 2)

                // Background Ring
                drawCircle(
                    color = Color(0xFFF1F5F9),
                    radius = radius,
                    center = centerOffset,
                    style = Stroke(width = strokeWidth)
                )

                // Segments proportional to each category's attempted contribution
                val validAttempted = totalAttempted.coerceAtLeast(1)
                var currentStartAngle = -90f

                val activeQuestionsCategories = progressList.filter { it.totalQuestions > 0 }
                val validCategoriesCount = activeQuestionsCategories.size.coerceAtLeast(1)

                activeQuestionsCategories.forEach { item ->
                    val sweep = if (totalAttempted > 0) {
                        (item.attemptedQuestions.toFloat() / validAttempted) * 360f * animatedAngle.value
                    } else {
                        (1f / validCategoriesCount) * 360f * 0.1f
                    }

                    if (sweep > 0f) {
                        drawArc(
                            color = item.color,
                            startAngle = currentStartAngle,
                            sweepAngle = sweep.coerceAtLeast(1f),
                            useCenter = false,
                            topLeft = Offset(centerOffset.x - radius, centerOffset.y - radius),
                            size = Size(radius * 2, radius * 2),
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                        currentStartAngle += sweep
                    }
                }
            }

            // Inner text in donut
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$overallPercentage%",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = IndigoSecondary
                )
                Text(
                    text = "हल किया",
                    fontSize = 10.sp,
                    color = Color(0xFF64748B)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Legend and Key metrics
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            progressList.take(5).forEach { item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(item.color)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = item.shortLabel,
                            fontSize = 11.5.sp,
                            color = Color(0xFF334155),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Text(
                        text = if (item.totalQuestions > 0) "${item.completionPercentage}%" else "थ्योरी",
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.5.sp,
                        color = if (item.totalQuestions > 0) item.color else Color(0xFF1D4ED8)
                    )
                }
            }
        }
    }
}
