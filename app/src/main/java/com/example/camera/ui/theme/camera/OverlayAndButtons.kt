package com.example.camera.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Overlay estilo maqueta:
 * - Retícula 3x3 calculada con Canvas (sin offsets fijos)
 * - Esquinas tipo "L"
 * - Texto ¡Postura Correcta! y badge REC
 */
@Composable
fun PoseOverlay(
    showRec: Boolean,
    showHint: Boolean,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {

        // ====== RETÍCULA 3x3 (FIX: usar strokeWidth y cap en drawLine) ======
        Canvas(Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val x1 = w / 3f
            val x2 = 2f * w / 3f
            val y1 = h / 3f
            val y2 = 2f * h / 3f

            val color = Color.White.copy(alpha = 0.65f)
            val strokeW = 2f

            // verticales
            drawLine(
                color = color,
                start = Offset(x1, 0f),
                end = Offset(x1, h),
                strokeWidth = strokeW,
                cap = StrokeCap.Round
            )
            drawLine(
                color = color,
                start = Offset(x2, 0f),
                end = Offset(x2, h),
                strokeWidth = strokeW,
                cap = StrokeCap.Round
            )

            // horizontales
            drawLine(
                color = color,
                start = Offset(0f, y1),
                end = Offset(w, y1),
                strokeWidth = strokeW,
                cap = StrokeCap.Round
            )
            drawLine(
                color = color,
                start = Offset(0f, y2),
                end = Offset(w, y2),
                strokeWidth = strokeW,
                cap = StrokeCap.Round
            )
        }

        // ====== ESQUINAS, TEXTO y REC (dejas tal cual lo que ya tenías) ======
        CornerL(Alignment.TopStart)
        CornerL(Alignment.TopEnd)
        CornerL(Alignment.BottomStart)
        CornerL(Alignment.BottomEnd)

        if (showHint) {
            Text(
                text = "¡Postura Correcta!",
                color = Color(0xFF00A651),
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 72.dp)
            )
        }

        if (showRec) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Box(
                    Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(Color.Red)
                )
                Spacer(Modifier.width(6.dp))
                Text("REC", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}



@Composable
private fun CornerL(
    align: Alignment,
    len: Dp = 32.dp,
    thick: Dp = 4.dp,
    color: Color = Color(0xFFD9D9D9)
) {
    Box(Modifier.fillMaxSize()) {
        // Horizontal
        Box(
            Modifier
                .align(align)
                .padding(18.dp)
                .size(len, thick)
                .background(color, RoundedCornerShape(2.dp))
        )
        // Vertical
        Box(
            Modifier
                .align(align)
                .padding(18.dp)
                .size(thick, len)
                .background(color, RoundedCornerShape(2.dp))
        )
    }
}
