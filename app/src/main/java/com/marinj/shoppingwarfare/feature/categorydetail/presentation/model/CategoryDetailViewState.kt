package com.marinj.shoppingwarfare.feature.categorydetail.presentation.model

import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.CategoryProduct

data class CategoryDetailViewState(
    val isLoading: Boolean = true,
    val categoryProducts: List<CategoryProduct> = listOf(),
)
