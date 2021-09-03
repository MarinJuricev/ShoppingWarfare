package com.marinj.shoppingwarfare.feature.categorydetail.presentation.model

sealed class CategoryDetailEvent {
    data class OnGetCategoryItems(val categoryId: String) : CategoryDetailEvent()
    data class OnCreateCategoryItem(val categoryItemName: String) : CategoryDetailEvent()
}
