package com.marinj.shoppingwarfare.feature.cart.presentation.model

sealed interface CartStatusEvent {
    object ReceiptCaptureError : CartStatusEvent
    data class CheckoutClicked(val uiCartItems: List<UiCartItem>) : CartStatusEvent
    data class ReceiptCaptureSuccess(val receiptPath: String?) : CartStatusEvent
    data class CartNameUpdated(val updatedCartName: String) : CartStatusEvent
}
