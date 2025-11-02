package com.example.actualizar.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.actualizar.R
import com.example.actualizar.ui.components.UpdateButton

private val CardBg = Color(0xFF1E1E1E)

@Composable
fun DeleteAccountConfirmScreen(
    navController: NavController,
    expectedUsername: String = "tuUsuario",
    onConfirmDelete: (String) -> Unit = {}
) {
    var typed by remember { mutableStateOf("") }
    val canDelete = typed.trim() == expectedUsername.trim() && typed.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp)
            .zIndex(1f)
    ) {
        Spacer(Modifier.height(36.dp))
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
            textAlign = TextAlign.Right,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp)
        )

        Spacer(Modifier.height(28.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Surface(
                color = CardBg,
                shape = RoundedCornerShape(20.dp),
                tonalElevation = 0.dp,
                shadowElevation = 0.dp,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth(0.88f)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 18.dp, vertical = 22.dp)
                ) {
                    Text(
                        text = "Confirmar eliminación de cuenta",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "¿Está seguro de que desea eliminar su cuenta? Esta acción no se puede revertir.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )

                    Spacer(Modifier.height(20.dp))

                    val red = Color(0xFFA3262A)
                    TextField(
                        value = typed,
                        onValueChange = { typed = it },
                        singleLine = true,
                        placeholder = {
                            Text(
                                "Escribe tu usuario",
                                color = Color.White,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = red,
                            unfocusedContainerColor = red,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .padding(horizontal = 2.dp),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(Modifier.height(18.dp))

                    UpdateButton(
                        text = "ELIMINAR",
                        enabled = canDelete,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        onConfirmDelete(typed)
                        navController.popBackStack()
                    }

                    Spacer(Modifier.height(12.dp))

                    UpdateButton(
                        text = "CANCELAR",
                        enabled = true,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}
