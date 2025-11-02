package com.example.actualizar.ui.screens.profile

import android.annotation.SuppressLint
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

/* ======== TUNING ======== */
private val Green = Color(0xFF1D5E21)
private val ChipShape = RoundedCornerShape(12.dp)
private val ChipMinWidth: Dp = 220.dp          // para “Máquinas de gimnasio”
private const val ImgScale = 2.30f             // escala para encuadre como la captura
private val ImgOffsetX: Dp = (-42).dp          // trae la foto desde la IZQUIERDA
private val ImgOffsetY: Dp = (-6).dp
private val ChipsBlockWidthFactor = 0.60f      // ancho del bloque derecho
/* ======================== */

enum class Equipment { Mancuernas, SoloCuerpo, Bandas, Maquinas }

@SuppressLint("Range")
@Composable
fun ChangeEquipmentScreen(navController: NavController) {
    // selección ÚNICA
    var selected by remember { mutableStateOf<Equipment?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Imagen: anclada a la IZQUIERDA, recortada como en el mock
        Image(
            painter = painterResource(R.drawable.equipo),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxHeight(1.08f)
                .offset(x = (50).dp, y = (-10).dp)
                .graphicsLayer(
                    scaleX = 2.25f,
                    scaleY = 2.25f
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

            // Título en dos líneas alineado a la derecha
            Text(
                text = "EQUIPO\nDISPONIBLE",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Right,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )

            // Chips a la derecha y un poco más ABAJO
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .fillMaxWidth(ChipsBlockWidthFactor)
                        .padding(end = 6.dp)
                        .offset(y = 115.dp)
                ) {
                    EquipmentChip(
                        label = "Mancuernas",
                        selected = selected == Equipment.Mancuernas,
                        onClick = { selected = Equipment.Mancuernas }
                    )
                    EquipmentChip(
                        label = "Sólo mi cuerpo",
                        selected = selected == Equipment.SoloCuerpo,
                        onClick = { selected = Equipment.SoloCuerpo }
                    )
                    EquipmentChip(
                        label = "Bandas de resistencia",
                        selected = selected == Equipment.Bandas,
                        onClick = { selected = Equipment.Bandas }
                    )
                    EquipmentChip(
                        label = "Máquinas de gimnasio",
                        selected = selected == Equipment.Maquinas,
                        onClick = { selected = Equipment.Maquinas }
                    )
                }
            }

            UpdateButton(
                text = "ACTUALIZAR",
                enabled = selected != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(bottom = 90.dp)
            ) {
                // TODO: envía 'selected' (Equipment?) al ViewModel / backend
            }
        }
    }
}

/** Chip reutilizable (selección única): label a la izquierda y puntito a la derecha. */
@Composable
private fun EquipmentChip(
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
                .defaultMinSize(minWidth = ChipMinWidth)
                .border(1.dp, borderColor, ChipShape)
                .clickable(interactionSource = interaction, indication = null) { onClick() }
                .padding(horizontal = 14.dp)
        ) {
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

            Box(
                modifier = Modifier
                    .size(10.dp)
                    .border(1.dp, dotStroke, CircleShape)
                    .background(dotFill, CircleShape)
            )
        }
    }
}
