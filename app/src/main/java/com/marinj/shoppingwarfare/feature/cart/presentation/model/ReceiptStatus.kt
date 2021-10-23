package com.marinj.shoppingwarfare.feature.cart.presentation.model

sealed class ReceiptStatus(val receiptPath: String? = null) {
    object Empty : ReceiptStatus()
    object Error : ReceiptStatus()
    data class Taken(val receipt: String) : ReceiptStatus(receipt)
}
