package com.marinj.shoppingwarfare.feature.cart.presentation.model

sealed interface CartViewEffect {
    object CartViewCheckoutCompleted : CartViewEffect
    data class Error(val errorMessage: String) : CartViewEffect
    data class CartViewItemDeleted(val cartItemName: String) : CartViewEffect
}
