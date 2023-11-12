package com.marinj.shoppingwarfare.feature.cart.presentation.model

sealed interface CartListEvent : CartEvent {
    object OnGetCartItems : CartListEvent
    data class DeleteCartItem(val uiCartItem: UiCartItem.Content) : CartListEvent
    data class CartItemQuantityChanged(
        val cartItemToUpdate: UiCartItem.Content,
        val newQuantity: Int,
    ) : CartListEvent

    data class ItemAddedToBasket(val cartItem: UiCartItem.Content) : CartListEvent
}
