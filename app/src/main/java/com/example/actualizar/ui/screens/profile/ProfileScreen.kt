package com.example.actualizar.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.actualizar.ui.components.HighlightedText
import com.example.actualizar.ui.components.HaloContainer
import com.example.actualizar.ui.navigation.AppDestinations
import com.example.actualizar.ui.theme.AppGray

@Composable
fun ProfileScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(Modifier.height(40.dp))

        // Título grande → Comfortaa (displayLarge)
        Text(
            text = "PERFIL",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(8.dp))

        // Subtítulo con palabra en verde
        HighlightedText(
            prefix = "Cada ",
            highlighted = "cambio",
            suffix = " comienza con una decisión.",
            style = MaterialTheme.typography.bodySmall // Figtree
        )

        Spacer(Modifier.height(32.dp))

        SectionTitle("Información Personal")

        ProfileItem("Cuenta") { navController.navigate(AppDestinations.UpdateAccount.route) }
        ProfileItem("Fecha de Nacimiento") { navController.navigate(AppDestinations.UpdateBirthday.route) }
        ProfileItem("Género") { navController.navigate(AppDestinations.UpdateGender.route) }
        ProfileItem("Altura") { navController.navigate(AppDestinations.UpdateHeight.route) }

        Spacer(Modifier.height(24.dp))

        SectionTitle("Preferencias de entrenamiento")

        ProfileItem("Experiencia") { navController.navigate(AppDestinations.UpdateExperience.route) }
        ProfileItem("Objetivo") { navController.navigate(AppDestinations.UpdateGoal.route) }
        ProfileItem("Equipo disponible") { navController.navigate(AppDestinations.UpdateEquipment.route) }

        Spacer(Modifier.height(24.dp))

        SectionTitle("Configuración")

        //ProfileItem("Editar nombre de usuario") { navController.navigate(AppDestinations.UpdateAccount.route) }
        ProfileItem("Eliminar cuenta") { navController.navigate(AppDestinations.DeleteAccount.route) }
        //ProfileItem("Confirmar Eliminar cuenta") { navController.navigate(AppDestinations.DeleteAccountConfirm.route) }

    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall, // Figtree SemiBold
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun ProfileItem(title: String, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    HaloContainer(glow = isPressed) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                )
                .padding(vertical = 12.dp),   // ← exactamente como antes
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                title,
                style = MaterialTheme.typography.bodyMedium,           // Figtree
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(">", style = MaterialTheme.typography.bodyMedium, color = AppGray)
        }
    }
}
