package com.marinj.shoppingwarfare.feature.history.presentation.model

sealed interface HistoryViewEffect {
    data class Error(val errorMessage: String) : HistoryViewEffect
}
