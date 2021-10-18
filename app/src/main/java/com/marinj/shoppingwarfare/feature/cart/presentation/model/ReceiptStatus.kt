package com.marinj.shoppingwarfare.feature.cart.presentation.model

sealed class ReceiptStatus {
    object Empty : ReceiptStatus()
    object Error : ReceiptStatus()
    data class Taken(val receiptPath: String) : ReceiptStatus()
}
