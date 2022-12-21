package com.marinj.shoppingwarfare.feature.history.list.presentation.model

import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryCartItem

data class UiHistoryItem(
    val id: String,
    val receiptPath: String?,
    val date: String,
    val cartName: String,
    val historyCartItems: List<HistoryCartItem>,
) {
    companion object {
        val DEFAULT = UiHistoryItem(
            id = "",
            receiptPath = null,
            date = "",
            cartName = "",
            historyCartItems = emptyList(),
        )
    }
}
