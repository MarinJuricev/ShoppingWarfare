package com.marinj.shoppingwarfare.feature.cart.presentation.mapper

import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.presentation.model.UiCartItem
import javax.inject.Inject

class CartItemToUiCartItemMapper @Inject constructor() {

    fun map(origin: List<CartItem>): List<UiCartItem> =
        origin.groupBy { cartItem -> cartItem.categoryName }.flatMap { cartMap ->
            val header = UiCartItem.Header(cartMap.key, cartMap.key)
            val content: List<UiCartItem> = cartMap.value.map { cartItem ->
                UiCartItem.Content(
                    id = cartItem.id,
                    categoryName = cartItem.categoryName,
                    name = cartItem.name,
                    quantity = cartItem.quantity,
                    isInBasket = cartItem.isInBasket,
                )
            }

            buildList {
                add(header)
                addAll(content)
            }
        }
}
