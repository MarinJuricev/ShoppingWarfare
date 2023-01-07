package com.marinj.shoppingwarfare.feature.category.detail.presentation.model

import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product

sealed interface ProductViewEffect {
    data class Error(val errorMessage: String) : ProductViewEffect
    data class ProductDeleted(val product: Product) : ProductViewEffect
    data class AddedToCart(val product: Product) : ProductViewEffect
}
