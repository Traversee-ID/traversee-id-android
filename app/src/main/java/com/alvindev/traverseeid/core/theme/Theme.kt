package com.alvindev.traverseeid.core.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorPalette = darkColors(
    primary = TraverseePrimary,
    primaryVariant = TraverseePrimaryVariant,
    secondary = TraverseeSecondary,
    secondaryVariant = TraverseeSecondaryVariant,
)

private val LightColorPalette = lightColors(
    primary = TraverseePrimary,
    primaryVariant = TraverseePrimaryVariant,
    secondary = TraverseeSecondary,
    secondaryVariant = TraverseeSecondaryVariant,
    onPrimary = Color.White,
    onSecondary = Color.White,


    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun TraverseeTheme(content: @Composable () -> Unit) {
    val colors = LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}