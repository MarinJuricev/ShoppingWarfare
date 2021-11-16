package com.marinj.shoppingwarfare.feature.history.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.feature.history.presentation.model.UiHistoryItem

@Composable
fun HistoryItemCard(historyItem: UiHistoryItem) {
    Column {
        Text(text = historyItem.cartName)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = historyItem.date)
    }
}
