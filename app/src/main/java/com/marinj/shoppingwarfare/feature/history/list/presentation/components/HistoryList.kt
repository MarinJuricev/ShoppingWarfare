package com.marinj.shoppingwarfare.feature.history.list.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.marinj.shoppingwarfare.feature.history.list.presentation.model.HistoryEvent
import com.marinj.shoppingwarfare.feature.history.list.presentation.model.UiHistoryItem

@Composable
fun HistoryList(
    historyItems: List<UiHistoryItem>,
    onHistoryEvent: (HistoryEvent) -> Unit
) {
    LazyColumn {
        items(
            items = historyItems,
            key = { it.id },
        ) { historyItem ->
            HistoryItemCard(
                historyItem = historyItem,
                onHistoryEvent = onHistoryEvent
            )
        }
    }
}
