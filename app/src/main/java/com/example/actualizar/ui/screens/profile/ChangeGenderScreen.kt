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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.actualizar.R
import com.example.actualizar.ui.components.UpdateButton

private val Green = Color(0xFF1D5E21)
private val ChipShape = RoundedCornerShape(8.dp) // más cuadrado, como el mock

enum class Gender { Masculino, Femenino }

@SuppressLint("Range")
@Composable
fun ChangeGenderScreen(navController: NavController) {
    var selected by remember { mutableStateOf<Gender?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Imagen grande a la derecha, un poco hacia adentro y sin cortarse
        Image(
            painter = painterResource(R.drawable.fechana_gen),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight(1.02f)
                .padding(end = 60.dp)
                .offset(x = (-0.5).dp, y = (-2).dp)
                .graphicsLayer(
                    scaleX = 2.50f,  // más grande
                    scaleY = 2.50f
                ),
            contentScale = ContentScale.Fit,
            alpha = 0.98f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Atrás",
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }

            // Título centrado (Comfortaa)
            Text(
                text = "GÉNERO",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Right,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            // Zona central: colocamos los chips centrados verticalmente
            Box(
                modifier = Modifier
                    .weight(1f) // toma todo el espacio entre título y botón
                    .fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .align(Alignment.CenterStart) // centrados vertical, alineados a la izquierda
                        .fillMaxWidth(0.58f)         // ancho como en el mock
                ) {
                    GenderChipSquare(
                        label = "Masculino",
                        selected = selected == Gender.Masculino,
                        onClick = { selected = Gender.Masculino }
                    )
                    GenderChipSquare(
                        label = "Femenino",
                        selected = selected == Gender.Femenino,
                        onClick = { selected = Gender.Femenino }
                    )
                }
            }

            val ctx = LocalContext.current

            UpdateButton(
                onClick = {
                    Toast.makeText(ctx, "¡Genero actualizado!", Toast.LENGTH_SHORT).show()
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

/** Chip rectangular 28dp alto, con puntito interno; halo verde al presionar/seleccionar. */
@Composable
private fun GenderChipSquare(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()

    val showGlow = pressed || selected
    val borderColor = if (selected) Green else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
    val dotFill = if (selected) Green else Color.White
    val dotStroke = if (selected) Green else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)

    Box(modifier = Modifier.fillMaxWidth()) {
        if (showGlow) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .padding(vertical = 2.dp)
                    .blur(14.dp)
                    .background(
                        brush = Brush.radialGradient(
                            listOf(Green.copy(alpha = 0.45f), Color.Transparent)
                        ),
                        shape = ChipShape
                    )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(40.dp)                        // más compacto (mock)
                .width(115.dp)
                .border(1.dp, borderColor, ChipShape) // borde fino
                .clickable(interactionSource = interaction, indication = null) { onClick() }
                .padding(horizontal = 12.dp)
        ) {
            // Puntito interior
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .border(1.dp, dotStroke, CircleShape)
                    .background(dotFill, CircleShape)
            )

            Spacer(Modifier.width(8.dp))

            // Texto (Figtree SemiBold)
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, fontSize = 15.sp),
                color = MaterialTheme.colorScheme.onBackground

            )
        }
    }
}
