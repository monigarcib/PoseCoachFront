package com.example.actualizar.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.text.TextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.style.TextAlign

private val HighlightGreen = Color(0xFF1D5E21)

/**
 * Texto que mezcla una palabra en verde con el resto blanco según el diseño.
 */
@Composable
fun HighlightedText(
    prefix: String,
    highlighted: String,
    suffix: String,
    style: TextStyle,
    prefixColor: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f)
) {
    val txt = buildAnnotatedString {
        withStyle(SpanStyle(color = prefixColor)) { append(prefix) }
        withStyle(SpanStyle(color = HighlightGreen, fontWeight = FontWeight.Medium)) { append(highlighted) }
        withStyle(SpanStyle(color = prefixColor)) { append(suffix) }
    }
    Text(text = txt, style = style)
}

/**
 * Estilo de campos en modo oscuro para Material3
 */
@Composable
fun darkTextFieldColors() = TextFieldDefaults.colors(
    focusedTextColor = MaterialTheme.colorScheme.onBackground,
    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
    cursorColor = MaterialTheme.colorScheme.onBackground,
    focusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
    unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.35f),
    focusedContainerColor = Color.Transparent,
    unfocusedContainerColor = Color.Transparent
)
