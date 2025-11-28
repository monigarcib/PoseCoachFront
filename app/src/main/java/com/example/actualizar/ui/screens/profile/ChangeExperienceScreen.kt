package com.example.actualizar.ui.screens.profile

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.actualizar.R
import com.example.actualizar.ui.components.UpdateButton

/* ======== TUNING (puedes mover sólo estos valores si quieres ajustes finos) ======== */
private val Green = Color(0xFF1D5E21)
private val ChipShape = RoundedCornerShape(12.dp)
private val ChipsWidth: Dp = 180.dp          // ancho del chip como en el mock
private const val ImgScale = 2.55f           // tamaño de la modelo
private val ImgOffsetX: Dp = (-38).dp        // recorte hacia la izquierda
private val ImgOffsetY: Dp = (-4).dp
private val ChipsBlockWidthFactor = 0.60f    // cuánto del ancho ocupa la columna de chips (a la derecha)
/* ================================================================================ */

enum class Experience { Avanzado, Intermedio, Principiante }

@SuppressLint("Range")
@Composable
fun ChangeExperienceScreen(navController: NavController) {
    var selected by remember { mutableStateOf<Experience?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Imagen: anclada a la IZQUIERDA y un poco fuera de pantalla para que el recorte coincida.
        Image(
            painter = painterResource(R.drawable.experiencia),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxHeight(1.05f)   // un poco más de alto visible
                .offset(x = (85).dp, y = (8).dp)   // mueve la imagen hacia adentro y ligeramente abajo
                .graphicsLayer(
                    scaleX = ImgScale,   // menor escala para que se vea completa
                    scaleY = ImgScale
                )
                .zIndex(0f),
            contentScale = ContentScale.Fit,
            alpha = 0.98f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .zIndex(1f)
        ) {
            Spacer(Modifier.height(8.dp))
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Atrás",
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }

            // Título CENTRADO como en el mock
            Text(
                text = "EXPERIENCIA",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )

            // Zona central: columna de chips pegada a la DERECHA
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .fillMaxWidth(ChipsBlockWidthFactor)
                        .padding(end = 6.dp) // ligero respiro del borde
                        .offset(y = 115.dp)
                ) {
                    ExperienceChip(
                        label = "Avanzado",
                        selected = selected == Experience.Avanzado,
                        onClick = { selected = Experience.Avanzado }
                    )
                    ExperienceChip(
                        label = "Intermedio",
                        selected = selected == Experience.Intermedio,
                        onClick = { selected = Experience.Intermedio }
                    )
                    ExperienceChip(
                        label = "Principiante",
                        selected = selected == Experience.Principiante,
                        onClick = { selected = Experience.Principiante }
                    )
                }
            }

            val ctx = LocalContext.current

            UpdateButton(
                onClick = {
                    Toast.makeText(ctx, "¡Experiencia actualizada!", Toast.LENGTH_SHORT).show()
                    // navController.popBackStack()
                },
                text = "ACTUALIZAR",
                enabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp)
            )
        }
    }
}

/** Chip rectangular con label a la IZQUIERDA y puntito a la DERECHA. */
@Composable
private fun ExperienceChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()

    val showGlow = pressed || selected
    val borderColor = if (selected) Green else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.82f)
    val dotFill = if (selected) Green else Color.White
    val dotStroke = if (selected) Green else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.82f)

    Box(modifier = Modifier.fillMaxWidth()) {
        if (showGlow) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .padding(vertical = 2.dp)
                    .blur(14.dp)
                    .background(
                        brush = Brush.radialGradient(
                            listOf(Green.copy(alpha = 0.42f), Color.Transparent)
                        ),
                        shape = ChipShape
                    )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(44.dp)
                .width(ChipsWidth)
                .border(1.dp, borderColor, ChipShape)
                .clickable(interactionSource = interaction, indication = null) { onClick() }
                .padding(horizontal = 14.dp)
        ) {
            // Texto (Figtree SemiBold)
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                ),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.width(10.dp))

            // Puntito a la DERECHA
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .border(1.dp, dotStroke, CircleShape)
                    .background(dotFill, CircleShape)
            )
        }
    }
}
