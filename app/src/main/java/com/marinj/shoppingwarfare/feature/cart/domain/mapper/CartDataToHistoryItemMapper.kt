package com.marinj.shoppingwarfare.feature.cart.domain.mapper

import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryCartItem
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryItem
import javax.inject.Inject

class CartDataToHistoryItemMapper @Inject constructor(
    private val uuidGenerator: () -> String,
    private val timeStampGenerator: () -> Long,
) {

    fun map(
        cartItems: List<CartItem>,
        cartName: String,
        receiptPath: String?,
    ): HistoryItem {
        return HistoryItem(
            id = uuidGenerator(),
            receiptPath = receiptPath,
            cartName = cartName,
            timestamp = timeStampGenerator(),
            historyCartItems = cartItems.map { cartItem ->
                with(cartItem) {
                    HistoryCartItem(
                        id = id.value,
                        categoryName = categoryName.value,
                        name = name.value,
                        quantity = quantity.toInt(),
                    )
                }
            },
        )
    }
}
