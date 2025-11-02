package com.example.actualizar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Marco con halo (glow) verde alrededor del contenido.
 * - Rodea el item con borde sutil + halo suave, como en el mockup.
 * - NO cambia el layout interno de tu Row.
 */
@Composable
fun HaloContainer(
    modifier: Modifier = Modifier,
    glow: Boolean,
    content: @Composable () -> Unit
) {
    val shape = RoundedCornerShape(12.dp)
    val green = Color(0xFF1D5E21)

    Box(modifier = modifier) {
        if (glow) {
            // Capa de HALO (desenfoque) afuera del borde
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .blur(
                        radius = 16.dp,
                        edgeTreatment = BlurredEdgeTreatment.Unbounded // que “salga” del borde
                    )
                    .background(
                        brush = Brush.radialGradient(
                            listOf(green.copy(alpha = 0.40f), Color.Transparent)
                        ),
                        shape = shape
                    )
            )
            // Borde sutil para “contener” el halo
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .border(width = 1.dp, color = green.copy(alpha = 0.80f), shape = shape)
            )
        }
        // Contenido real (tu Row original)
        Box(
            modifier = Modifier
                .padding(0.dp) // por si quieres micro-ajuste del relleno con halo
        ) {
            content()
        }
    }
}
