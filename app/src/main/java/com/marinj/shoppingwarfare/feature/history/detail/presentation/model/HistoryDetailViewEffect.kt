package com.marinj.shoppingwarfare.feature.history.detail.presentation.model

sealed interface HistoryDetailViewEffect {
    data class Error(val errorMessage: String) : HistoryDetailViewEffect
}
