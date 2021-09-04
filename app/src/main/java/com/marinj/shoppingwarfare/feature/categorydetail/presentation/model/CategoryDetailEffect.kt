package com.marinj.shoppingwarfare.feature.categorydetail.presentation.model

sealed class CategoryDetailEffect {
    data class Error(val errorMessage: String) : CategoryDetailEffect()
    object CategoryItemCreated : CategoryDetailEffect()
}
