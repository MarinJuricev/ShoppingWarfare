package com.marinj.shoppingwarfare.feature.history.detail.presentation.model

import com.marinj.shoppingwarfare.feature.history.list.presentation.model.UiHistoryItem

data class HistoryDetailViewState(
    val isLoading: Boolean = true,
    val uiHistoryItem: UiHistoryItem? = null,
)
