package com.marinj.shoppingwarfare.feature.history.presentation.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryItem

@Composable
fun HistoryItemCard(historyItem: HistoryItem) {
    Text(text = historyItem.cartName)
}
