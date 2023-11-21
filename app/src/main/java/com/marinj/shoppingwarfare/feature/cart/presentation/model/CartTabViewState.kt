package com.marinj.shoppingwarfare.feature.cart.presentation.model

data class CartTabViewState(
    val selectedIndex: Int = 0,
    val cartTabs: List<CartTab> = listOf(),
)
