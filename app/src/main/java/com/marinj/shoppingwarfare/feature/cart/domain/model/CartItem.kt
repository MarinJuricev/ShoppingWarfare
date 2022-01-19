package com.marinj.shoppingwarfare.feature.cart.domain.model

data class CartItem(
    val id: String,
    val categoryName: String,
    val name: String,
    val quantity: Int,
) {
    companion object {
        const val DEFAULT_QUANTITY = 1
    }
}
