package com.marinj.shoppingwarfare.feature.cart.presentation.model

import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem

data class CartViewState(
    val isLoading: Boolean = true,
    val receiptStatus: ReceiptStatus = ReceiptStatus.Empty,
    val cartName: String = "",
    val isPremiumUser: Boolean = false,
    val cartData: Map<String, List<CartItem>> = emptyMap(),
    val cartItems: List<CartItem> = emptyList(),
)
