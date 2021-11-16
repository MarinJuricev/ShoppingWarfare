package com.marinj.shoppingwarfare.feature.history.presentation.model

data class HistoryViewState(
    val isLoading: Boolean = true,
    val searchText: String = "",
    val historyItems: List<UiHistoryItem> = emptyList(),
)
