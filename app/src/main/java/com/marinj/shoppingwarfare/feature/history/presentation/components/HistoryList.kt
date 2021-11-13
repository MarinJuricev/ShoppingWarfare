package com.marinj.shoppingwarfare.feature.history.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryItem

@Composable
fun HistoryList(historyItems: List<HistoryItem>) {
    LazyColumn {
        items(
            items = historyItems,
            key = { it.id },
        ) { historyItem ->
            HistoryItemCard(historyItem)
        }
    }
}
