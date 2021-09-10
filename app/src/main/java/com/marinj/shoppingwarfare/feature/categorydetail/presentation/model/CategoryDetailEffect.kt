package com.marinj.shoppingwarfare.feature.categorydetail.presentation.model

sealed class CategoryDetailEffect {
    data class Error(val errorMessage: String) : CategoryDetailEffect()
    data class ProductCreated(val productName: String) : CategoryDetailEffect()
    data class ProductDeleted(val productName: String) : CategoryDetailEffect()
}
