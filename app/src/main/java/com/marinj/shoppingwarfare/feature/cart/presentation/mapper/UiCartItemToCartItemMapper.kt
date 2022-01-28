package com.marinj.shoppingwarfare.feature.cart.presentation.mapper

import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.presentation.model.UiCartItem
import javax.inject.Inject

class UiCartItemToCartItemMapper @Inject constructor() {

    fun map(origin: List<UiCartItem>): List<CartItem> {
        return origin
            .filterIsInstance(UiCartItem.Content::class.java)
            .map { uiCartItem ->
                with(uiCartItem) {
                    CartItem(
                        id = id,
                        categoryName = categoryName,
                        name = name,
                        quantity = quantity,
                        isInBasket = isInBasket,
                    )
                }
            }
    }
}
