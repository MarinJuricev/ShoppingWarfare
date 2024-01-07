package com.marinj.shoppingwarfare.feature.cart.data.model

import androidx.annotation.Keep
import com.marinj.shoppingwarfare.db.LocalCartItem
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class RemoteCartItem(
    val cartItemId: String,
    val categoryName: String,
    val name: String,
    val quantity: Int,
    val inBasket: Boolean,
) {
    fun toLocal() = LocalCartItem(
        id = cartItemId,
        categoryName = categoryName,
        name = name,
        quantity = quantity.toLong(),
        isInBasket = inBasket,
    )
}

fun CartItem.toRemote() = RemoteCartItem(
    cartItemId = id.value,
    categoryName = categoryName.value,
    name = name.value,
    quantity = quantity.toInt(),
    inBasket = isInBasket,
)
