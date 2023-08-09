package com.marinj.shoppingwarfare.feature.history.detail.presentation.model

sealed interface HistoryDetailEvent {
    data class OnGetHistoryDetail(val historyItemId: String) : HistoryDetailEvent

    object OnBackClicked: HistoryDetailEvent
}
