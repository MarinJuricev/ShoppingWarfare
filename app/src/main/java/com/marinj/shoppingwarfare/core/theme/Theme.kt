package com.marinj.shoppingwarfare.core.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = purpleDark,
    primaryVariant = purple,
    secondary = purpleLight,
    background = white,
)

@Composable
fun ShoppingWarfareTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}
