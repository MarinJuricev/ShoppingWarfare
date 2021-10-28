package com.marinj.shoppingwarfare.feature.category.detail.presentation.model

import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product

data class CategoryDetailViewState(
    val isLoading: Boolean = true,
    val products: List<Product> = listOf(),
)
