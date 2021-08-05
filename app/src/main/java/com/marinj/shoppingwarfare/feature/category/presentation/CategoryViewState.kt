package com.marinj.shoppingwarfare.feature.category.presentation

import com.marinj.shoppingwarfare.feature.category.presentation.model.UiCategory

data class CategoryViewState(
    val isLoading: Boolean = true,
    val errorMessage: String = "",
    val categories: List<UiCategory> = emptyList(),
)
