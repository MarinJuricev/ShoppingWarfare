package com.marinj.shoppingwarfare.feature.category.presentation.model

import com.marinj.shoppingwarfare.feature.category.presentation.model.UiCategory

data class CategoryViewState(
    val isLoading: Boolean = true,
    val categories: List<UiCategory> = emptyList(),
)
