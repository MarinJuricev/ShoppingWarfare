package com.marinj.shoppingwarfare.feature.history.presentation.model

import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryItem

data class HistoryViewState(
    val isLoading: Boolean = true,
    val searchText: String = "",
    val historyItems: List<HistoryItem> = emptyList(),
)
