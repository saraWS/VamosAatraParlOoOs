package com.example1.vamooosaatraparloss.ui.theme


import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Colores personalizados inspirados en Pokémon
private val LightColors = lightColorScheme(
    primary = Color(0xFF4CAF50), // Verde claro
    onPrimary = Color.White,
    secondary = Color(0xFF42A5F5), // Azul claro
    onSecondary = Color.White,
    background = Color(0xFFF5F5F5), // Gris muy claro
    surface = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

// Fuente para toda la app (puedes personalizar si deseas)
private val AppTypography = Typography(
    headlineSmall = Typography().headlineSmall.copy(color = Color.Black),
    bodyLarge = Typography().bodyLarge.copy(color = Color.Black)
)

@Composable
fun VamooosaAtraparlossTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = AppTypography,
        content = content
    )
}
