package com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model

sealed interface CreateCategoryViewEffect {
    object CreateCategoryViewSuccess : CreateCategoryViewEffect
    data class CreateCategoryViewFailure(val errorMessage: String) : CreateCategoryViewEffect
}
