package com.marinj.shoppingwarfare.feature.category.presentation.model

data class CategoryViewState(
    val isLoading: Boolean = true,
    val categories: List<UiCategory> = emptyList(),
)
