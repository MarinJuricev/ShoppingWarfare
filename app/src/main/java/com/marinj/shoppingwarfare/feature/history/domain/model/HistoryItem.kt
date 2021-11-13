package com.marinj.shoppingwarfare.feature.history.domain.model

data class HistoryItem(
    val id: String,
    val receiptPath: String?,
    val timestamp: Long,
    val cartName: String,
    val historyCartItems: List<HistoryCartItem>
)
