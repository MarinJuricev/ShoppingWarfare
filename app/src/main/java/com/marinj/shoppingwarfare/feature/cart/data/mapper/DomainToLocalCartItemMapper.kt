package com.marinj.shoppingwarfare.feature.cart.data.mapper

import com.marinj.shoppingwarfare.feature.cart.data.model.LocalCartItem
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import javax.inject.Inject

class DomainToLocalCartItemMapper @Inject constructor() {

    fun map(origin: CartItem): LocalCartItem {
        return with(origin) {
            LocalCartItem(
                cartItemId = id,
                categoryName = categoryName,
                name = name,
                quantity = quantity,
            )
        }
    }
}
