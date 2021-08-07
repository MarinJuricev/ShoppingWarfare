package com.marinj.shoppingwarfare.feature.createcategory.presentation.viewmodel

import androidx.compose.ui.graphics.Color

sealed class CreateCategoryEvent {
    data class OnCategoryNameChanged(val categoryText: String) : CreateCategoryEvent()
    data class OnColorChanged(val selectedColor: Color) : CreateCategoryEvent()
    object OnCreateCategoryClicked : CreateCategoryEvent()
}