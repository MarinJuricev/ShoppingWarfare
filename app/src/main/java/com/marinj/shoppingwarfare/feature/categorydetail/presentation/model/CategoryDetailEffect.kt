package com.marinj.shoppingwarfare.feature.categorydetail.presentation.model

import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.Product

sealed class CategoryDetailEffect {
    data class Error(val errorMessage: String) : CategoryDetailEffect()
    data class ProductDeleted(val product: Product) : CategoryDetailEffect()
}
