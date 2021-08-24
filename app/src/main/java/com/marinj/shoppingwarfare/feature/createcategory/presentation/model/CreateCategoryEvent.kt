package com.marinj.shoppingwarfare.feature.createcategory.presentation.model

import androidx.compose.ui.graphics.Color

sealed class CreateCategoryEvent {
    data class OnCategoryNameChanged(val categoryText: String) : CreateCategoryEvent()
    data class OnBackgroundColorChanged(val selectedColor: Color) : CreateCategoryEvent()
    data class OnTitleColorChanged(val selectedColor: Color) : CreateCategoryEvent()
    object OnCreateCategoryClicked : CreateCategoryEvent()
}