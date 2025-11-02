package com.example.actualizar.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.actualizar.R

private val Green = Color(0xFF1D5E21)

/** Botón negro con borde verde y glow */
@Composable
private fun GlowOutlineButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(12.dp)
    Box(modifier = modifier) {
        // Glow
        Box(
            modifier = Modifier
                .matchParentSize()
                .blur(16.dp)
                .background(
                    brush = Brush.radialGradient(
                        listOf(Green.copy(alpha = 0.45f), Color.Transparent)
                    ),
                    shape = shape
                )
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(48.dp)
                .border(2.dp, Green, shape)
                .background(Color.Black, shape)
                .clickable(enabled = enabled, onClick = onClick)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = if (enabled) Color.White else Color.White.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun DeleteAccountScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Imagen decorativa (ajústala según tu recurso)
        Image(
            painter = painterResource(R.drawable.ajustesdecuenta),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxHeight(0.65f)
                .offset(y = 24.dp)
                .graphicsLayer(scaleX = 1.25f, scaleY = 1.25f),
            contentScale = ContentScale.Crop,
            alpha = 0.95f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .zIndex(1f)
        ) {
            Spacer(Modifier.height(36.dp)) // más margen superior
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Atrás",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Text(
                text = "ELIMINAR\nCUENTA",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Right, // centrado como en el mock
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp)
            )

            Spacer(Modifier.height(40.dp))

            GlowOutlineButton(
                text = "ELIMINAR CUENTA",
                onClick = { navController.navigate("delete_account_confirm") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
