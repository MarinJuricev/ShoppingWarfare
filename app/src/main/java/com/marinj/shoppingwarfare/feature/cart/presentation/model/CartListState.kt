package com.marinj.shoppingwarfare.feature.cart.presentation.model

data class CartListState(
    val isLoading: Boolean = false,
    val uiCartItems: List<UiCartItem> = emptyList(),
)
