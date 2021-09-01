package com.marinj.shoppingwarfare.feature.categorydetail.presentation.model

sealed class CategoryDetailEvent {
    data class GetCategoryItems(val categoryId: String) : CategoryDetailEvent()
}
