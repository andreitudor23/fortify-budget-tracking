package com.echipappa.fortify.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val FortifyColorScheme = darkColorScheme(
    primary = ElectricBlue,
    onPrimary = Color.White,
    secondary = ElectricBlueDark,
    onSecondary = Color.White,
    background = NavyDark,
    onBackground = TextPrimary,
    surface = NavyCard,
    onSurface = TextPrimary,
    surfaceVariant = NavyCardLight,
    onSurfaceVariant = TextSecondary,
    error = DangerRed,
    onError = Color.White
)

@Composable
fun FortifyTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = FortifyColorScheme,
        typography = Typography,
        content = content
    )
}