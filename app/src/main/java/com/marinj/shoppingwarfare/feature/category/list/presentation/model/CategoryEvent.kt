package com.marinj.shoppingwarfare.feature.category.list.presentation.model

sealed interface CategoryEvent {
    object GetCategories : CategoryEvent
    object NavigateToCreateCategory : CategoryEvent
    data class DeleteCategory(val uiCategory: UiCategory) : CategoryEvent
    data class NavigateToCategoryDetail(
        val categoryId: String,
        val categoryName: String,
    ) : CategoryEvent
    data class UndoCategoryDeletion(val uiCategory: UiCategory) : CategoryEvent
}
