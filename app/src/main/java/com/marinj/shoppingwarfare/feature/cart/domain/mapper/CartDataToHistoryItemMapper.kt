package com.marinj.shoppingwarfare.feature.cart.domain.mapper

import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryCartItem
import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryItem
import javax.inject.Inject

class CartDataToHistoryItemMapper @Inject constructor(
    private val uuidGenerator: () -> String,
    private val timeStampGenerator: () -> Long,
) {

    fun map(
        cartData: Map<String, List<CartItem>>,
        receiptPath: String?,
    ): HistoryItem {
        return HistoryItem(
            id = uuidGenerator(),
            receiptPath,
            timestamp = timeStampGenerator(),
            historyCartItems = cartData.values.flatMap { cartItems ->
                cartItems.map {
                    HistoryCartItem(
                        id = it.id,
                        categoryName = it.categoryName,
                        name = it.name,
                        quantity = it.quantity
                    )
                }
            }
        )
    }
}
