package com.marinj.shoppingwarfare.feature.category.detail.presentation.model

import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product

sealed class CategoryDetailEffect {
    data class Error(val errorMessage: String) : CategoryDetailEffect()
    data class ProductDeleted(val product: Product) : CategoryDetailEffect()
    data class AddedToCart(val product: Product) : CategoryDetailEffect()
}
