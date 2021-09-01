package com.marinj.shoppingwarfare.feature.categorydetail.presentation.model

import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.CategoryItem

data class CategoryDetailViewState(
    val isLoading: Boolean = true,
    val categoryItems: List<CategoryItem> = listOf(),
)
