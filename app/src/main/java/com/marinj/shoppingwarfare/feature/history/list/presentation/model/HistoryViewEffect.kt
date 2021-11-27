package com.marinj.shoppingwarfare.feature.history.list.presentation.model

sealed interface HistoryViewEffect {
    data class Error(val errorMessage: String) : HistoryViewEffect
}
