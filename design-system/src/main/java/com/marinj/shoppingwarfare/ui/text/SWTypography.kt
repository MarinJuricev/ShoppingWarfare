package com.marinj.shoppingwarfare.ui.text

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.unit.sp

internal object SWTypography {

    private val DISPLAY = TextStyle(fontFamily = FontFamily.Default)
    private val HEADLINE = TextStyle(fontFamily = FontFamily.Default)
    private val TITLE = TextStyle(fontFamily = FontFamily.Default)
    private val LABEL = TextStyle(fontFamily = FontFamily.Default)
    private val BODY = TextStyle(fontFamily = FontFamily.Default)

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

    val HeadlineLarge = TextStyle(
        fontSize = 32.sp,
        lineHeight = 40.sp,
        fontWeight = W400,
    ).merge(HEADLINE)

    val HeadlineMedium = TextStyle(
        fontSize = 28.sp,
        lineHeight = 36.sp,
        fontWeight = W400,
    ).merge(HEADLINE)

    val HeadlineSmall = TextStyle(
        fontSize = 24.sp,
        lineHeight = 32.sp,
        fontWeight = W400,
    ).merge(HEADLINE)

    val TitleLarge = TextStyle(
        fontSize = 22.sp,
        lineHeight = 28.sp,
        fontWeight = W400,
    ).merge(TITLE)

    val TitleMedium = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = W500,
        letterSpacing = 0.15.sp,
    ).merge(TITLE)

    val TitleSmall = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = W500,
        letterSpacing = 0.5.sp,
    ).merge(TITLE)

    val LabelLarge = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = W500,
        letterSpacing = 0.1.sp,
    ).merge(LABEL)

    val LabelMedium = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontWeight = W500,
        letterSpacing = 0.5.sp,
    ).merge(LABEL)

    val LabelSmall = TextStyle(
        fontSize = 11.sp,
        lineHeight = 16.sp,
        fontWeight = W500,
        letterSpacing = 0.5.sp,
    ).merge(LABEL)

    val BodyLarge = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = W500,
        letterSpacing = 0.5.sp,
    ).merge(BODY)

    val BodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = W500,
        letterSpacing = 0.25.sp,
    ).merge(BODY)

    val BodySmall = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontWeight = W400,
    ).merge(BODY)
}