package com.example.actualizar.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkScheme = darkColorScheme(
    primary       = GreenMain,
    onPrimary     = AppWhite,
    background    = AppBlack,
    onBackground  = AppWhite,
    surface       = AppBlack,
    onSurface     = AppWhite
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkScheme,
        typography  = AppTypography,
        content     = content
    )
}
