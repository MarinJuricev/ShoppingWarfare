package com.marinj.shoppingwarfare.feature.cart.data.model

import com.marinj.shoppingwarfare.core.mapper.getOrEmpty
import com.marinj.shoppingwarfare.core.mapper.getOrFalse
import com.marinj.shoppingwarfare.core.mapper.getOrZero

data class RemoteCartItem(
    val cartItemId: String,
    val categoryName: String,
    val name: String,
    val quantity: Int,
    val isInBasket: Boolean,
) {
    fun Map<String, Any>.toRemoteCartItem(): RemoteCartItem = RemoteCartItem(
        cartItemId = getOrEmpty("cartItemId"),
        categoryName = getOrEmpty("categoryName"),
        name = getOrEmpty("name"),
        quantity = getOrZero("quantity"),
        isInBasket = getOrFalse("isInBasket"),
    )
}