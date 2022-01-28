package com.marinj.shoppingwarfare.feature.cart.presentation.model

data class CartViewState(
    val isLoading: Boolean = true,
    val receiptStatus: ReceiptStatus = ReceiptStatus.Empty,
    val cartName: String = "",
    val isPremiumUser: Boolean = false,
    val uiCartItems: List<UiCartItem> = emptyList(),
)
