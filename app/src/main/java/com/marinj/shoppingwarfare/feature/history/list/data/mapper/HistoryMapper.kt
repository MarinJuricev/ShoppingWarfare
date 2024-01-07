package com.marinj.shoppingwarfare.feature.history.list.data.mapper

import com.marinj.shoppingwarfare.db.Database
import com.marinj.shoppingwarfare.db.LocalHistoryCartItem
import com.marinj.shoppingwarfare.feature.history.list.data.model.LocalHistoryItem
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryCartItem

fun LocalHistoryCartItem.toDomain(): HistoryCartItem = HistoryCartItem(
    id = historyItemId,
    categoryName = categoryName,
    name = name,
    quantity = quantity.toInt(),
)

context(Database)
fun toLocalHistoryItem(historyItem: List<com.marinj.shoppingwarfare.db.LocalHistoryItem>) =
    historyItem.map {
        val cartItems = historyCartItemQueries
            .selectCartItemsForHistoryItem(it.id)
            .executeAsList()
            .map(LocalHistoryCartItem::toDomain)

        LocalHistoryItem(
            historyItemId = it.id,
            receiptPath = it.receiptPath,
            timestamp = it.timestamp,
            cartName = it.cartName,
            historyCartItems = cartItems,
        )
    }