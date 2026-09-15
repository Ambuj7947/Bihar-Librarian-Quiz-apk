package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
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
import com.example.ui.theme.CorrectAnswerBg
import com.example.ui.theme.CorrectAnswerGreen
import com.example.ui.theme.WrongAnswerBg
import com.example.ui.theme.WrongAnswerRed

@Composable
fun OptionButton(
    optionIndex: Int, // 1 to 4
    optionText: String,
    selectedOption: Int?,
    correctOption: Int,
    scaleFactor: Float = 1.0f,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val letterHindi = when (optionIndex) {
        1 -> "क"
        2 -> "ख"
        3 -> "ग"
        4 -> "घ"
        else -> ""
    }

    val isSelected = selectedOption == optionIndex
    val isRevealed = selectedOption != null
    val isThisCorrect = optionIndex == correctOption

    // Determine colors
    val (bgColor, borderColor, textColor, badgeBg, badgeTextColor) = when {
        !isRevealed -> {
            // Default unselected state
            Tuple5(
                Color.White,
                Color(0xFFE2E8F0),
                Color(0xFF1E293B),
                Color(0xFFF1F5F9),
                Color(0xFF475569)
            )
        }
        isSelected && isThisCorrect -> {
            // User selected and it's correct!
            Tuple5(
                CorrectAnswerBg,
                CorrectAnswerGreen,
                Color(0xFF14532D),
                CorrectAnswerGreen,
                Color.White
            )
        }
        isSelected && !isThisCorrect -> {
            // User selected and it's wrong
            Tuple5(
                WrongAnswerBg,
                WrongAnswerRed,
                Color(0xFF7F1D1D),
                WrongAnswerRed,
                Color.White
            )
        }
        isRevealed && isThisCorrect -> {
            // Not selected, but this was the correct answer (show to user)
            Tuple5(
                Color(0xFFF0FDF4),
                CorrectAnswerGreen,
                Color(0xFF14532D),
                CorrectAnswerGreen,
                Color.White
            )
        }
        else -> {
            // Other neutral unselected options after answer
            Tuple5(
                Color(0xFFF8FAFC),
                Color(0xFFE2E8F0),
                Color(0xFF64748B),
                Color(0xFFE2E8F0),
                Color(0xFF64748B)
            )
        }
    }

    val borderWidth = if (isRevealed && (isSelected || isThisCorrect)) 2.dp else 1.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(bgColor)
            .border(borderWidth, borderColor, RoundedCornerShape(14.dp))
            .clickable(enabled = selectedOption == null) {
                onClick()
            }
            .padding(horizontal = 14.dp, vertical = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Letter Badge (क, ख, ग, घ)
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(badgeBg),
                contentAlignment = Alignment.Center
            ) {
                if (isRevealed && isThisCorrect) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "सही",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                } else if (isRevealed && isSelected && !isThisCorrect) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "गलत",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text(
                        text = letterHindi,
                        fontWeight = FontWeight.Bold,
                        fontSize = (16 * scaleFactor).sp,
                        color = badgeTextColor
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Option Content Text
            Text(
                text = optionText,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = (16 * scaleFactor).sp,
                    lineHeight = (23 * scaleFactor).sp,
                    fontWeight = if (isSelected || (isRevealed && isThisCorrect)) FontWeight.SemiBold else FontWeight.Normal
                ),
                color = textColor,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

private data class Tuple5<A, B, C, D, E>(
    val a: A, val b: B, val c: C, val d: D, val e: E
)
