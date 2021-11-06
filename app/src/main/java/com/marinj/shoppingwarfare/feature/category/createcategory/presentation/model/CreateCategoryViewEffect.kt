package com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model

sealed class CreateCategoryViewEffect {
    object CreateCategoryViewSuccess : CreateCategoryViewEffect()
    data class CreateCategoryViewFailure(val errorMessage: String) : CreateCategoryViewEffect()
}
