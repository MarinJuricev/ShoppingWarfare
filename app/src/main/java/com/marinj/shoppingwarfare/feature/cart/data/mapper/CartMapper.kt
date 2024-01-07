package com.marinj.shoppingwarfare.feature.cart.data.mapper

import com.marinj.shoppingwarfare.db.LocalCartItem
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem.Companion.CartItem

fun LocalCartItem.toDomain() = CartItem(
    id = id,
    categoryName = categoryName,
    name = name,
    quantity = quantity.toUInt(),
    isInBasket = isInBasket,
)

fun CartItem.toLocal() = LocalCartItem(
    id = id.value,
    categoryName = categoryName.value,
    name = name.value,
    quantity = quantity.toLong(),
    isInBasket = isInBasket,
)
