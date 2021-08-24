package com.marinj.shoppingwarfare.feature.createcategory.presentation.model

import androidx.compose.ui.graphics.Color

private val availableColorList = listOf(
    Color.Blue,
    Color.Cyan,
    Color.DarkGray,
    Color.Gray,
    Color.Green,
    Color.LightGray,
    Color.Magenta,
    Color.Red,
)

data class CreateCategoryViewState(
    val categoryName: String = "",
    val backgroundColor: Color? = null,
    val titleColor: Color? = null,
    val availableColors: List<Color> = availableColorList,
)
