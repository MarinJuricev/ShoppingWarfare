package com.marinj.shoppingwarfare.feature.categorydetail.presentation.model

import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.Product

sealed class CategoryDetailEvent {
    data class OnGetCategoryProducts(val categoryId: String) : CategoryDetailEvent()
    data class OnCreateCategoryProduct(val categoryId: String, val productName: String) :
        CategoryDetailEvent()

    data class OnProductClicked(val product: Product) : CategoryDetailEvent()
    data class OnProductDelete(val product: Product) : CategoryDetailEvent()
    data class RestoreProductDeletion(val product: Product) : CategoryDetailEvent()
}
