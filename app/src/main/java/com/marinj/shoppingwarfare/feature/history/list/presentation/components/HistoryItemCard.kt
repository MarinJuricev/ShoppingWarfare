package com.marinj.shoppingwarfare.feature.history.list.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareSwipeToDismiss
import com.marinj.shoppingwarfare.feature.history.list.presentation.model.HistoryEvent
import com.marinj.shoppingwarfare.feature.history.list.presentation.model.HistoryEvent.OnHistoryItemClick
import com.marinj.shoppingwarfare.feature.history.list.presentation.model.UiHistoryItem

@Composable
fun HistoryItemCard(
    historyItem: UiHistoryItem,
    onHistoryEvent: (HistoryEvent) -> Unit,
) {
    ShoppingWarfareSwipeToDismiss(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { onHistoryEvent(OnHistoryItemClick(historyItem)) },
        onDismiss = { },
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                HistoryItemDescriptionText(description = stringResource(id = R.string.cart_name))
                HistoryItemBodyText(bodyText = historyItem.cartName)
            }
            Column {
                HistoryItemDescriptionText(description = stringResource(id = R.string.history_item_date))
                HistoryItemBodyText(bodyText = historyItem.date)
            }
        }
    }
}

@Composable
fun HistoryItemDescriptionText(description: String) {
    Text(
        text = description,
        style = MaterialTheme.typography.body2,
        color = if (MaterialTheme.colors.isLight) Color.LightGray else Color.White
    )
}

@Composable
fun HistoryItemBodyText(bodyText: String) {
    Text(
        text = bodyText,
        style = MaterialTheme.typography.body1,
    )
}
