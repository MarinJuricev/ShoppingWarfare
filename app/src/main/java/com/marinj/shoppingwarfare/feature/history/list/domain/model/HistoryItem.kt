package com.marinj.shoppingwarfare.feature.history.list.domain.model

data class HistoryItem(
    val id: String,
    val receiptPath: String?,
    val timestamp: Long,
    val cartName: String,
    val historyCartItems: List<HistoryCartItem>,
)
