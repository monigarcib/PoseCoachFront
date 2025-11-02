package com.example.actualizar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember


/**
 * Botón del mockup:
 * - Fondo blanco (o gris muy claro)
 * - Texto negro en mayúsculas (usa Figtree desde typography.bodyMedium)
 * - Pastillas verdes en los costados
 * - Sin glow
 */
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

    // Usamos Box + clickable semántico, o puedes envolver con Modifier.clickable si prefieres ripple
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(bg, shape)
            .semantics { role = androidx.compose.ui.semantics.Role.Button }
            .noRippleClickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        // Acentos verdes laterales (pastillas)
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

        // Texto (Figtree via bodyMedium)
        Text(
            text = text,
            color = fg,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}

/** Helper para quitar ripple (coincide con el look del mockup) */
fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    onClick: () -> Unit
): Modifier = this.composed {
    val interaction = remember { MutableInteractionSource() }
    this.clickable(
        enabled = enabled,
        indication = null,                 // sin ripple
        interactionSource = interaction,
        onClick = onClick
    )
}