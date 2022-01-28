package com.marinj.shoppingwarfare.feature.cart.domain.model

data class CartItem(
    val id: String,
    val categoryName: String,
    val name: String,
    val quantity: Int,
    val isInBasket: Boolean,
) {
    companion object {
        const val DEFAULT_QUANTITY = 1
        const val DEFAULT_IS_IN_BASKET = false
    }
}
