package com.marinj.shoppingwarfare.feature.history.list.presentation.model

sealed interface HistoryEvent {
    object OnGetHistoryItems : HistoryEvent
    object OnSearchTriggered : HistoryEvent
    data class OnSearchUpdated(val newSearch: String) : HistoryEvent
    data class OnHistoryItemClick(val uiHistoryItem: UiHistoryItem) : HistoryEvent
}
