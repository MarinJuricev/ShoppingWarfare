package com.marinj.shoppingwarfare.core.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val purpleDark = Color(0xFF7c4dff)
val purpleLight = Color(0xFF651fff)
val purple = Color(0xFF3f1dcb)
val white = Color(0xFFFFFFFF)

@Composable
fun MaterialTheme.textColor() = if (colors.isLight) Color.LightGray else Color.White
