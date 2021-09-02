package com.marinj.shoppingwarfare.feature.category.presentation.model

sealed class CategoryEvent {
    object GetCategories : CategoryEvent()
    data class DeleteCategory(val uiCategory: UiCategory) : CategoryEvent()
    data class NavigateToCategoryDetail(val categoryId: String) : CategoryEvent()
    data class UndoCategoryDeletion(val uiCategory: UiCategory) : CategoryEvent()
}
