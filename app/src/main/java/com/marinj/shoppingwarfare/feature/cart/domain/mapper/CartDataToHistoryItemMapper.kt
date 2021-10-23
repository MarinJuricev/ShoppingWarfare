package com.marinj.shoppingwarfare.feature.cart.domain.mapper

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryItem
import javax.inject.Inject

class CartDataToHistoryItemMapper @Inject constructor() :
    Mapper<HistoryItem, Map<String, List<CartItem>>> {

    override suspend fun map(origin: Map<String, List<CartItem>>): HistoryItem {
        TODO("Not yet implemented")
    }
}
