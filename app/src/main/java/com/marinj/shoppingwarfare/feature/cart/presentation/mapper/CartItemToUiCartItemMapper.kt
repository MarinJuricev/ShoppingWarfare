package com.marinj.shoppingwarfare.feature.cart.presentation.mapper

import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.presentation.model.UiCartItem
import javax.inject.Inject

class CartItemToUiCartItemMapper @Inject constructor() {

    fun map(origin: CartItem): UiCartItem {
        return with(origin) {
            UiCartItem(
                id = id,
                categoryName = categoryName,
                name = name,
                quantity = quantity,
                addedToBasket = false,
            )
        }
    }
}
