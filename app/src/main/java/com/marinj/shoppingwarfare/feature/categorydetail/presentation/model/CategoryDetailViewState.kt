package com.marinj.shoppingwarfare.feature.categorydetail.presentation.model

import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.Product

data class CategoryDetailViewState(
    val isLoading: Boolean = true,
    val products: List<Product> = listOf(),
)
