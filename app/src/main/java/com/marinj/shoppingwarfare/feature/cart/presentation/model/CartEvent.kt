package com.marinj.shoppingwarfare.feature.cart.presentation.model

sealed interface CartEvent {
    object OnGetCartItems : CartEvent
    object ReceiptCaptureError : CartEvent
    object CheckoutClicked : CartEvent
    data class ReceiptCaptureSuccess(val receiptPath: String?) : CartEvent
    data class CartItemQuantityChanged(
        val cartItemToUpdate: UiCartItem.Content,
        val newQuantity: Int,
    ) : CartEvent

    data class DeleteCartItem(val cartItem: UiCartItem.Content) : CartEvent
    data class CartNameUpdated(val updatedCartName: String) : CartEvent
}
