package com.marinj.shoppingwarfare.feature.category.list.presentation.model

sealed class CategoryViewEffect {
    data class DeleteCategoryView(val uiCategory: UiCategory) : CategoryViewEffect()
    data class Error(val errorMessage: String) : CategoryViewEffect()
}
