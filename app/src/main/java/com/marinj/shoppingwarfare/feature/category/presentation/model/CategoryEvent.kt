package com.marinj.shoppingwarfare.feature.category.presentation.model

sealed class CategoryEvent {
    object GetCategories : CategoryEvent()
    data class DeleteCategory(val categoryName: String) : CategoryEvent()
    data class NavigateToCategoryDetail(val categoryName: String) : CategoryEvent()
}
