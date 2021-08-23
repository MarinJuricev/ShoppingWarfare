package com.marinj.shoppingwarfare.feature.category.presentation.model

sealed class CategoryEvent {
    object GetCategories : CategoryEvent()
}
