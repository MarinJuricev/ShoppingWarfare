package com.marinj.shoppingwarfare.feature.cart.domain.mapper

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryItem
import javax.inject.Inject

class CartItemsToHistoryItemMapper @Inject constructor() : Mapper<HistoryItem, List<CartItem>> {

    override suspend fun map(origin: List<CartItem>): HistoryItem {
        return TODO()
    }
}
