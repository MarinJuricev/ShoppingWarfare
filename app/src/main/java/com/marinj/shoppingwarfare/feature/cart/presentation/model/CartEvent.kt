package com.marinj.shoppingwarfare.feature.cart.presentation.model

import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem

sealed class CartEvent {
    object OnGetCartItems : CartEvent()
    object ReceiptCaptureError : CartEvent()
    object CheckoutClicked : CartEvent()
    data class ReceiptCaptureSuccess(val receiptPath: String) : CartEvent()
    data class CartItemQuantityChanged(
        val cartItemToUpdate: CartItem,
        val newQuantity: Int,
    ) : CartEvent()

    data class DeleteCartItem(val cartItem: CartItem) : CartEvent()
}
