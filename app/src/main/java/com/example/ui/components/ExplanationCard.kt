package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.QuestionEntity
import com.example.ui.theme.CorrectAnswerGreen
import com.example.ui.theme.ExplanationBoxBg
import com.example.ui.theme.ExplanationBoxBorder
import com.example.ui.theme.TipBoxBg
import com.example.ui.theme.TipBoxBorder

@Composable
fun ExplanationCard(
    question: QuestionEntity,
    selectedOption: Int?,
    scaleFactor: Float = 1.0f,
    modifier: Modifier = Modifier
) {
    val isCorrect = selectedOption == question.correctOption
    val correctLabel = when (question.correctOption) {
        1 -> "विकल्प (क)"
        2 -> "विकल्प (ख)"
        3 -> "विकल्प (ग)"
        4 -> "विकल्प (घ)"
        else -> ""
    }
    val correctText = question.getOptionText(question.correctOption)

    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + slideInVertically { it / 4 }
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(ExplanationBoxBg)
                .border(1.5.dp, ExplanationBoxBorder, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            // Header with answer badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "व्याख्या",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "विस्तृत व्याख्या एवं सही उत्तर",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = (17 * scaleFactor).sp
                    ),
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Correct Answer Tag
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFDCFCE7))
                    .border(1.dp, CorrectAnswerGreen, RoundedCornerShape(10.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "सही",
                        tint = CorrectAnswerGreen,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "सही उत्तर: $correctLabel - $correctText",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = (15 * scaleFactor).sp,
                        color = Color(0xFF14532D)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Explanation body text
            Text(
                text = question.explanationHindi,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = (16 * scaleFactor).sp,
                    lineHeight = (24 * scaleFactor).sp
                ),
                color = Color(0xFF1E293B)
            )

            // Key highlight memory tip if available
            if (question.keyHighlight.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(TipBoxBg)
                        .border(1.dp, TipBoxBorder, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = "महत्वपूर्ण बिंदु",
                        tint = Color(0xFFB45309),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "याद रखने योग्य मुख्य बिंदु:",
                            fontWeight = FontWeight.Bold,
                            fontSize = (14 * scaleFactor).sp,
                            color = Color(0xFF92400E)
                        )
                        Text(
                            text = question.keyHighlight,
                            fontSize = (14 * scaleFactor).sp,
                            lineHeight = (20 * scaleFactor).sp,
                            color = Color(0xFF78350F)
                        )
                    }
                }
            }
        }
    }
}
