package com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model

sealed class CreateCategoryEffect {
    object CreateCategorySuccess : CreateCategoryEffect()
    data class CreateCategoryFailure(val errorMessage: String) : CreateCategoryEffect()
}
