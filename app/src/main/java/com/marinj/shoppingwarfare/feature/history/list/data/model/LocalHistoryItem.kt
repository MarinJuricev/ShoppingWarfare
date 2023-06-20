package com.marinj.shoppingwarfare.feature.history.list.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryCartItem
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryItem
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryItem.Companion.HistoryItem

@Entity(tableName = "localHistoryItem")
data class LocalHistoryItem(
    @PrimaryKey
    val historyItemId: String,
    val receiptPath: String?,
    val timestamp: Long,
    val cartName: String,
    val historyCartItems: List<HistoryCartItem>,
) {
    fun toDomain() = HistoryItem(
        id = historyItemId,
        receiptPath = receiptPath,
        timestamp = timestamp,
        cartName = cartName,
        historyCartItems = historyCartItems,
    )
}

fun HistoryItem.toLocal() = LocalHistoryItem(
    historyItemId = id.value,
    receiptPath = receiptPath?.value,
    timestamp = timestamp,
    cartName = cartName.value,
    historyCartItems = historyCartItems,
)