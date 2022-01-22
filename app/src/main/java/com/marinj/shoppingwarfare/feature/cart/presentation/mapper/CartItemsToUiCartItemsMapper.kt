package com.marinj.shoppingwarfare.feature.cart.presentation.mapper

import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.presentation.model.UiCartItem
import javax.inject.Inject

class CartItemsToUiCartItemsMapper @Inject constructor() {

    fun map(origin: List<CartItem>): List<UiCartItem> =
        //TODO: Placeholder
        origin.groupBy { cartItem -> cartItem.categoryName }.map {
            UiCartItem.Header(it.key, it.key)
        }
}
