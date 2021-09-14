package com.marinj.shoppingwarfare.feature.cart.presentation.model

import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem

data class CartViewState(
    val isLoading: Boolean = true,
    val cartItems: List<CartItem> = emptyList()
)
