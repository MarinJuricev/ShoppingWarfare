package com.marinj.shoppingwarfare.feature.cart.data.model

import androidx.annotation.Keep
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class RemoteCartItem(
    val cartItemId: String,
    val categoryName: String,
    val name: String,
    val quantity: Int,
    val isInBasket: Boolean,
) {
    fun toLocal() = LocalCartItem(
        cartItemId = cartItemId,
        categoryName = categoryName,
        name = name,
        quantity = quantity,
        isInBasket = isInBasket,
    )
}

fun CartItem.toRemote() = RemoteCartItem(
    cartItemId = id.value,
    categoryName = categoryName.value,
    name = name.value,
    quantity = quantity.toInt(),
    isInBasket = isInBasket,
)