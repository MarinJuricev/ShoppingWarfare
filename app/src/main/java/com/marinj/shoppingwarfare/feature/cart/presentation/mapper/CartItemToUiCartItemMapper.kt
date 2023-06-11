package com.marinj.shoppingwarfare.feature.cart.presentation.mapper

import com.marinj.shoppingwarfare.core.model.NonEmptyString
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.presentation.model.UiCartItem
import com.marinj.shoppingwarfare.feature.cart.presentation.model.UiCartItem.Content
import com.marinj.shoppingwarfare.feature.cart.presentation.model.UiCartItem.Header
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CartItemToUiCartItemMapper @Inject constructor() {

    suspend fun map(
        origin: List<CartItem>,
    ): List<UiCartItem> = withContext(Dispatchers.Default) {
        origin.groupBy { cartItem -> cartItem.categoryName }.flatMap { cartMap: Map.Entry<NonEmptyString, List<CartItem>> ->
            val header = Header(cartMap.key.value, cartMap.key.value)
            val content: List<UiCartItem> = cartMap.value.map { cartItem ->
                Content(
                    id = cartItem.id.value,
                    categoryName = cartItem.categoryName.value,
                    name = cartItem.name.value,
                    quantity = cartItem.quantity.toInt(),
                    isInBasket = cartItem.isInBasket,
                )
            }

            buildList {
                add(header)
                addAll(content)
            }
        }
    }
}
