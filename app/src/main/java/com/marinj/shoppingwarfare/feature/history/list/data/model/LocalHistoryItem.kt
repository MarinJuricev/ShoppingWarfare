package com.marinj.shoppingwarfare.feature.history.list.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryCartItem

@Entity(tableName = "localHistoryItem")
data class LocalHistoryItem(
    @PrimaryKey
    val historyItemId: String,
    val receiptPath: String?,
    val timestamp: Long,
    val cartName: String,
    val historyCartItems: List<HistoryCartItem>,
)
