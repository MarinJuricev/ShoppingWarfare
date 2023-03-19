package com.marinj.shoppingwarfare.feature.cart.domain.model

data class CartItem(
    val id: String,
    val categoryName: String,
    val name: String,
    val quantity: Int = DEFAULT_QUANTITY,
    val isInBasket: Boolean = DEFAULT_IS_IN_BASKET,
)

private const val DEFAULT_QUANTITY = 1
private const val DEFAULT_IS_IN_BASKET = false
