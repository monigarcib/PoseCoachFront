package com.example.actualizar.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.actualizar.R

// Títulos grandes → Comfortaa
val Comfortaa = FontFamily(
    Font(R.font.comfortaa_regular, FontWeight.Normal),
    Font(R.font.comfortaa_bold,    FontWeight.Bold),
)

// Texto normal → Comfortaa
val Figtree = FontFamily(
    Font(R.font.figtree_regular,   FontWeight.Normal),
    Font(R.font.figtree_medium,    FontWeight.Medium),
    Font(R.font.figtree_semibold,  FontWeight.SemiBold),
    Font(R.font.figtree_bold,      FontWeight.Bold),
)

val AppTypography = Typography(
    displayLarge = TextStyle(      // Títulos grandes (“PERFIL”, etc.)
        fontFamily = Comfortaa,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        lineHeight = 36.sp
    ),
    headlineSmall = TextStyle(     // Títulos de sección
        fontFamily = Figtree,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),
    bodyMedium = TextStyle(        // Filas/items
        fontFamily = Figtree,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(         // Descripciones/placeholder
        fontFamily = Figtree,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        color = AppGray
    ),
    labelLarge = TextStyle(        // Botones (texto)
        fontFamily = Comfortaa,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 16.sp
    )
)
