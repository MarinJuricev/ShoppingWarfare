package com.marinj.shoppingwarfare.feature.cart.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem.Companion.CartItem

@Entity(tableName = "localCartItem")
data class LocalCartItem(
    @PrimaryKey
    val cartItemId: String,
    val categoryName: String,
    val name: String,
    val quantity: UInt,
    val isInBasket: Boolean,
) {
    fun toDomain() = CartItem(
        id = cartItemId,
        categoryName = categoryName,
        name = name,
        quantity = quantity,
        isInBasket = isInBasket,
    )
}

fun CartItem.toLocal() = LocalCartItem(
    cartItemId = id.value,
    categoryName = categoryName.value,
    name = name.value,
    quantity = quantity,
    isInBasket = isInBasket,
)