package com.marinj.shoppingwarfare.feature.cart.presentation.model

import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem

sealed class CartEvent {
    object OnGetCartItems : CartEvent()
    data class CartItemQuantityChanged(
        val cartItemToUpdate: CartItem,
        val newQuantity: Int,
    ) : CartEvent()
    data class DeleteCartItem(val cartItemId: String) : CartEvent()
}
