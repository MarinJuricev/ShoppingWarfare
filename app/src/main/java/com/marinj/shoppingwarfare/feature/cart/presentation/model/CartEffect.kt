package com.marinj.shoppingwarfare.feature.cart.presentation.model

sealed class CartEffect {
    data class Error(val errorMessage: String) : CartEffect()
}
