package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = SaffronPrimaryLight,
    onPrimary = Color.White,
    primaryContainer = SaffronPrimaryDark,
    onPrimaryContainer = Color(0xFFFFDBCF),
    secondary = IndigoSecondaryLight,
    onSecondary = Color.White,
    secondaryContainer = IndigoSecondaryDark,
    onSecondaryContainer = Color(0xFFD6E4FF),
    tertiary = WisdomGoldLight,
    onTertiary = Color.Black,
    background = DarkBg,
    onBackground = Color(0xFFE2E8F0),
    surface = DarkSurfaceCard,
    onSurface = Color(0xFFE2E8F0),
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = Color(0xFFCBD5E1)
)

private val LightColorScheme = lightColorScheme(
    primary = SaffronPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFEBDD),
    onPrimaryContainer = Color(0xFF5A1E04),
    secondary = IndigoSecondary,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE0ECFD),
    onSecondaryContainer = Color(0xFF0F264A),
    tertiary = WisdomGold,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFEF3C7),
    onTertiaryContainer = Color(0xFF78350F),
    background = ParchmentBgLight,
    onBackground = Color(0xFF1E293B),
    surface = SurfaceCardLight,
    onSurface = Color(0xFF1E293B),
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = Color(0xFF475569)
)

@Composable
fun BiharLibrarianTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
