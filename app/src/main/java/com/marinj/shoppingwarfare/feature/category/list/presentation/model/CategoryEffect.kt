package com.marinj.shoppingwarfare.feature.category.list.presentation.model

sealed class CategoryEffect {
    data class DeleteCategory(val uiCategory: UiCategory) : CategoryEffect()
    data class Error(val errorMessage: String) : CategoryEffect()
    data class NavigateToCategoryDetail(
        val categoryId: String,
        val categoryName: String,
    ) : CategoryEffect()
}
