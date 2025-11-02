package com.example.actualizar.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.actualizar.R
import com.example.actualizar.ui.components.HaloContainer
import com.example.actualizar.ui.theme.AppGray

@Composable
fun UpdateAccountScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // ✅ Imagen en la esquina inferior derecha
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            val painter = painterResource(R.drawable.ajustesdecuenta)
            val ratio = remember(painter) {
                val w = painter.intrinsicSize.width
                val h = painter.intrinsicSize.height
                if (w > 0f && h > 0f) w / h else 0.65f // fallback si no hay tamaño intrínseco
            }

            // Queremos que ocupe ~60% del alto disponible
            val targetHeight = maxHeight * 0.60f
            val targetWidth = targetHeight * ratio

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .height(targetHeight)
                    .width(targetWidth)
                    .padding(end = 0.dp, bottom = 24.dp)
                    .sizeIn(maxWidth = maxWidth, maxHeight = maxHeight), // no se sale
                contentScale = ContentScale.Fit, // ⬅️ nunca recorta
                alpha = 0.98f
            )
        }



        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(8.dp))

            // Flecha de regreso
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Atrás",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            // Título grande
            Text(
                text = "AJUSTES DE\nCUENTA",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            Spacer(Modifier.height(24.dp))

            GlowPressItem("Cambiar Nombre de Usuario") {
                navController.navigate("change_username")
            }
            GlowPressItem("Cambiar e-mail") {
                navController.navigate("change_email")
            }
            GlowPressItem("Cambiar teléfono") {
                navController.navigate("change_phone")
            }
        }
    }
}

@Composable
private fun GlowPressItem(
    title: String,
    onClick: () -> Unit
) {
    val interaction = remember { MutableInteractionSource() }
    val isPressed by interaction.collectIsPressedAsState()

    HaloContainer(glow = isPressed) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = interaction,
                    indication = null,
                    onClick = onClick
                )
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(">", style = MaterialTheme.typography.bodyMedium, color = AppGray)
        }
    }
}
