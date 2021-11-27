package com.marinj.shoppingwarfare.feature.history.list.presentation.model

data class HistoryViewState(
    val isLoading: Boolean = true,
    val searchText: String = "",
    val historyItems: List<UiHistoryItem> = emptyList(),
    val nonFilteredHistoryItems: List<UiHistoryItem> = emptyList(),
)
