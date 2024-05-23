package com.example.myapplication.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)
private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40,
    secondary = Color(0xFF4CAF50),
    background = Color(0xFFFFFFFF),       // Light gray for background
    surface = Color(0xFFD9D9D9),          // White for cards
    primary = Color(0xFF7355B2),
)

private val CustomLightColorScheme1 = lightColorScheme(
    primary = Color(0xFF7355B2),
    onPrimary = Color.White,
    secondary = Color(0xFF7355B2),
    onSecondary = Color.White,
    background = Color(0xFFD9D9D9),
    onBackground = Color.Black,
    surface = Color(0xFFFFFFFF),
    onSurface = Color.Black,
    error = Color(0xFFB00020),
    onError = Color.White
)
//0xFF7355B2
private val CustomDarkColorScheme1 = darkColorScheme(
    primary = Color(0xFF312153),
    onPrimary = Color(0xFF794BCA),
    secondary = Color(0xFF312153),
    onSecondary = Color.Black,
    background = Color(0xFF1E1F22),
    onBackground = Color.White,
    surface = Color(0xFF2B2D30),
    onSurface = Color.White,
    error = Color(0xFFCF6679),
    onError = Color.Black
)
//FF312153
//FF331D5A

private val CustomLightColorScheme2 = lightColorScheme(
    primary = Purple80,
    onPrimary = Color.White,
    secondary = Pink80,
    onSecondary = Color.White,
    background = Color(0xFFD9D9D9),
    onBackground = Color.Black,
    surface = Color(0xFFFFFFFF),
    onSurface = Color.Black,
    error = Color(0xFFB00020),
    onError = Color.White
)
private val CustomDarkColorScheme2 = darkColorScheme(
    primary = Purple80,
    onPrimary = Color.Black,
    secondary = Pink80,
    onSecondary = Color.Black,
    background = PurpleGrey80,
    onBackground = Color.White,
    surface = PurpleGrey80,
    onSurface = Color.White,
    error = Pink40,
    onError = Color.Black
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    // Choose the appropriate color scheme based on the theme
    val context = LocalContext.current
//    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val colorScheme = if (darkTheme) CustomDarkColorScheme1 else CustomLightColorScheme1
//    val colorScheme = if (darkTheme) CustomDarkColorScheme2 else CustomLightColorScheme2

//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Ensure you define this typography
        content = content
    )
}