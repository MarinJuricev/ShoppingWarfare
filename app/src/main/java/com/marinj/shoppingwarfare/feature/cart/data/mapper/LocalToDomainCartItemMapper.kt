package com.marinj.shoppingwarfare.feature.cart.data.mapper

import com.marinj.shoppingwarfare.feature.cart.data.model.LocalCartItem
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import javax.inject.Inject

class LocalToDomainCartItemMapper @Inject constructor() {

    fun map(origin: LocalCartItem): CartItem {
        return with(origin) {
            CartItem(
                id = cartItemId,
                categoryName = categoryName,
                name = name,
                quantity = quantity,
                isInBasket = isInBasket,
            )
        }
    }
}
