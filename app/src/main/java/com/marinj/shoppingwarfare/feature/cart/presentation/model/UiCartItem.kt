package com.marinj.shoppingwarfare.feature.cart.presentation.model

sealed class UiCartItem(open val id: String) {
    data class Header(
        override val id: String,
        val categoryName: String,
    ) : UiCartItem(id)

    data class Content(
        override val id: String,
        val name: String,
        val quantity: Int,
        val addedToBasket: Boolean,
    ) : UiCartItem(id)
}