package com.example.actualizar.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.actualizar.R
import com.example.actualizar.ui.components.UpdateButton
import com.example.actualizar.ui.components.darkTextFieldColors
import com.example.actualizar.ui.theme.AppGray

@Composable
fun ChangePhoneScreen(navController: NavController) {
    var phone by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        BoxWithConstraints(Modifier.fillMaxSize()) {
            val painter = painterResource(R.drawable.cambiar)
            val ratio = remember(painter) {
                val w = painter.intrinsicSize.width
                val h = painter.intrinsicSize.height
                if (w > 0 && h > 0) w / h else 0.65f
            }
            val targetHeight = maxHeight * 0.90f
            val targetWidth = targetHeight * ratio

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .height(targetHeight)
                    .width(targetWidth)
                    .sizeIn(maxWidth = maxWidth),
                contentScale = ContentScale.Fit,
                alpha = 0.98f
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(12.dp))

            Text(
                text = "CAMBIAR\nTELÉFONO",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Escribe tu nuevo teléfono",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(14.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it.filter { d -> d.isDigit() } },
                placeholder = {
                    Text(
                        "Teléfono",
                        style = MaterialTheme.typography.bodySmall,
                        color = AppGray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                singleLine = true,
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                ),
                colors = darkTextFieldColors(),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            )

            Spacer(Modifier.height(30.dp))

            UpdateButton(
                text = "ACTUALIZAR",
                enabled = phone.length in 8..15,
                modifier = Modifier.fillMaxWidth()
            ) { /* TODO */ }
        }
    }
}
