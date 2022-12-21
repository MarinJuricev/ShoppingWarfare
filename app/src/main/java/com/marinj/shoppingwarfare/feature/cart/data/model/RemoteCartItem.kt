package com.marinj.shoppingwarfare.feature.cart.data.model

import androidx.annotation.Keep

@Keep
data class RemoteCartItem(
    val cartItemId: String,
    val categoryName: String,
    val name: String,
    val quantity: Int,
    val isInBasket: Boolean,
)
