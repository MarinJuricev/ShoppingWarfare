package com.marinj.shoppingwarfare.feature.category

import com.marinj.shoppingwarfare.feature.category.model.UiCategory

data class CategoryViewState(
    val isLoading: Boolean = true,
    val errorMessage: String = "",
    val categories: List<UiCategory> = emptyList(),
)
