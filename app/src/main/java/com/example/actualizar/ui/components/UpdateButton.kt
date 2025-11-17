package com.example.actualizar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip

@Composable
fun UpdateButton(
    text: String = "ACTUALIZAR",
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val bg = if (enabled) Color(0xFFEDEDED) else Color(0xFF2B2B2B)
    val fg = if (enabled) Color.Black else Color(0xFF8A8A8A)
    val green = Color(0xFF1D5E21)
    val shape = RoundedCornerShape(12.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(shape) // asegura el hitbox y la forma
            .background(bg)
            .semantics { role = Role.Button }
            .clickable(                     // sin ripple, pero clickeable
                enabled = enabled,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        // Pastillas verdes laterales
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 10.dp)
                .size(width = 8.dp, height = 22.dp)
                .background(green, RoundedCornerShape(8.dp))
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp)
                .size(width = 8.dp, height = 22.dp)
                .background(green, RoundedCornerShape(8.dp))
        )

        // Texto (mantiene tipografía/estilo del mockup)
        Text(
            text = text,
            color = fg,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            textAlign = TextAlign.Center
        )
    }
}
