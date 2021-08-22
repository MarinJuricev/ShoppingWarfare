package com.marinj.shoppingwarfare.feature.createcategory.presentation.model

sealed class CreateCategoryEffect {
    object CreateCategorySuccess : CreateCategoryEffect()
    data class CreateCategoryFailure(val errorMessage: String) : CreateCategoryEffect()
}
