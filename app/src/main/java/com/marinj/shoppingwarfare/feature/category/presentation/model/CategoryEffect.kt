package com.marinj.shoppingwarfare.feature.category.presentation.model

sealed class CategoryEffect {
    //TODO instead of categoryTitle introduce an ID
    data class DeleteCategory(val categoryTitle: String) : CategoryEffect()
    data class Error(val errorMessage: String) : CategoryEffect()
    data class NavigateToCategoryDetail(val categoryTitle: String) : CategoryEffect()
}
