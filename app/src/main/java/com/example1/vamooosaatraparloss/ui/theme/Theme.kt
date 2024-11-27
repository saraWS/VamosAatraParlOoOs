package com.example1.vamooosaatraparloss.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Paleta de colores personalizados
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4CAF50), // Verde claro
    onPrimary = Color.White,
    secondary = Color(0xFF42A5F5), // Azul claro
    onSecondary = Color.White,
    background = Color(0xFFF5F5F5), // Gris muy claro
    surface = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF388E3C), // Verde oscuro
    onPrimary = Color.White,
    secondary = Color(0xFF1976D2), // Azul más oscuro
    onSecondary = Color.White,
    background = Color(0xFF303030), // Gris oscuro
    surface = Color(0xFF424242),
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun VamooosaAtraparlossTheme(
    darkTheme: Boolean = false, // Por defecto, usa el tema claro
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
