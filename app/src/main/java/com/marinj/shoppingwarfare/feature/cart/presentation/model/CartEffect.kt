package com.marinj.shoppingwarfare.feature.cart.presentation.model

sealed class CartEffect {
    object CartCheckoutCompleted : CartEffect()
    data class Error(val errorMessage: String) : CartEffect()
    data class CartItemDeleted(val cartItemName: String) : CartEffect()
}
