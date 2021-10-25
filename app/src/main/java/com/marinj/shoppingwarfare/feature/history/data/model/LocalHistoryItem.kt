package com.marinj.shoppingwarfare.feature.history.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryCartItem

@Entity(tableName = "localHistoryItem")
data class LocalHistoryItem(
    @PrimaryKey
    val historyItemId: String,
    val receiptPath: String?,
    val timestamp: Long,
    val historyCartItems: List<HistoryCartItem>
)
