package com.marinj.shoppingwarfare.feature.category.list.presentation.model

data class CategoryViewState(
    val isLoading: Boolean = true,
    val categories: List<UiCategory> = emptyList(),
)
