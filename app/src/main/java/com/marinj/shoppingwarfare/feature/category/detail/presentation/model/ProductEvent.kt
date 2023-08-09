package com.marinj.shoppingwarfare.feature.category.detail.presentation.model

import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product

sealed interface ProductEvent {
    data class OnGetProducts(val categoryId: String) : ProductEvent

    data class OnCreateProduct(
        val categoryId: String,
        val categoryName: String,
        val productName: String,
    ) : ProductEvent

    data class OnProductClicked(val product: Product) : ProductEvent
    data class OnProductDelete(val product: Product) : ProductEvent
    data class RestoreProductDeletion(val product: Product) : ProductEvent

    object OnBackClicked : ProductEvent
}
