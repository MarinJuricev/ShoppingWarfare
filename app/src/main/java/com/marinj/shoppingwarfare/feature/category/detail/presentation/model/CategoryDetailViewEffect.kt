package com.marinj.shoppingwarfare.feature.category.detail.presentation.model

import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product

sealed class CategoryDetailViewEffect {
    data class Error(val errorMessage: String) : CategoryDetailViewEffect()
    data class ProductDeleted(val product: Product) : CategoryDetailViewEffect()
    data class AddedToCart(val product: Product) : CategoryDetailViewEffect()
}
