package com.whyranoid.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

// TODO: Dark Theme
private val DarkColorScheme = darkColorScheme(
    primary = WalkieColor.Primary,
    secondary = WalkieColor.Secondary,
    tertiary = WalkieColor.Tertiary,
    surface = Color.Black,
)

private val LightColorScheme = lightColorScheme(
    primary = WalkieColor.Primary,
    secondary = WalkieColor.Secondary,
    tertiary = WalkieColor.Tertiary,
    surface = Color.White,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun WalkieTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {

    // TODO : Dynamic Color
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Remember a SystemUiController
    val systemUiController = rememberSystemUiController()

    DisposableEffect(systemUiController, darkTheme) {

        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = darkTheme.not()
        )

        systemUiController.setStatusBarColor(
            color = colorScheme.surface,
            darkIcons = darkTheme.not()
        ) { requestedColor ->
            requestedColor
        }

        onDispose {}
    }

    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content,
    )
}
