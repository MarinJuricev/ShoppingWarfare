package com.marinj.shoppingwarfare.feature.cart.presentation.model

data class CartStatusState(
    val receiptStatus: ReceiptStatus = ReceiptStatus.Empty,
    val cartName: String = "",
)
