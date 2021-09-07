package com.marinj.shoppingwarfare.feature.categorydetail.presentation.model

sealed class CategoryDetailEvent {
    data class OnGetCategoryProducts(val categoryId: String) : CategoryDetailEvent()
    data class OnCreateCategoryProduct(val categoryId: String, val categoryItemName: String) :
        CategoryDetailEvent()
}
