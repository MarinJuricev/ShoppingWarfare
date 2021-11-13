package com.marinj.shoppingwarfare.feature.history.presentation.model

sealed interface HistoryEvent {
    object OnGetHistoryItems : HistoryEvent
    data class OnSearchUpdated(val newSearch: String) : HistoryEvent
}
