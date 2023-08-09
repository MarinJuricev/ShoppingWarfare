package com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model

import androidx.compose.ui.graphics.Color

sealed interface CreateCategoryEvent {
    data class OnCategoryNameChanged(val categoryText: String) : CreateCategoryEvent
    data class OnBackgroundColorChanged(val selectedColor: Color) : CreateCategoryEvent
    data class OnTitleColorChanged(val selectedColor: Color) : CreateCategoryEvent
    object OnCreateCategoryClicked : CreateCategoryEvent
    object OnBackClicked : CreateCategoryEvent
}
