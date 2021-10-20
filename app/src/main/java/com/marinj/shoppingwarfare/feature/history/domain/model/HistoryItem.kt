package com.marinj.shoppingwarfare.feature.history.domain.model

data class HistoryItem(
    val id: String,
    val timestamp: Long,
    val historyCartItems: List<HistoryCartItem>
)
