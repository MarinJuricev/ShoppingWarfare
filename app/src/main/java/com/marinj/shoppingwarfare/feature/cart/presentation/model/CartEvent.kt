package com.marinj.shoppingwarfare.feature.cart.presentation.model

sealed class CartEvent {
    object OnGetCartItems : CartEvent()
}
