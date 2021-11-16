package com.marinj.shoppingwarfare.feature.history.presentation.model

import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryCartItem

data class UiHistoryItem(
    val id: String,
    val receiptPath: String?,
    val date: String,
    val cartName: String,
    val historyCartItems: List<HistoryCartItem>
)
