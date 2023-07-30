package com.marinj.shoppingwarfare.ui.text

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.unit.sp

object SWTypography {

    private val DISPLAY = TextStyle(fontFamily = FontFamily.Default)

    val DisplayLarge = TextStyle(
        fontSize = 57.sp,
        lineHeight = 64.sp,
        fontWeight = W400,
    ).merge(DISPLAY)

    val DisplayMedium = TextStyle(
        fontSize = 45.sp,
        lineHeight = 52.sp,
        fontWeight = W400,
    ).merge(DISPLAY)

    val DisplaySmall = TextStyle(
        fontSize = 36.sp,
        lineHeight = 44.sp,
        fontWeight = W400,
    ).merge(DISPLAY)
}