package com.marinj.shoppingwarfare.feature.history.detail.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.theme.ShoppingWarfareTextColors
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryCartItem

@Composable
fun HistoryDetailQuantity(historyCartItem: HistoryCartItem) {
    Column {
        Text(
            text = stringResource(
                id = R.string.history_detail_quantity,
            ),
            style = MaterialTheme.typography.body2,
            color = ShoppingWarfareTextColors(),
        )
        Text(
            text = historyCartItem.quantity.toString(),
            style = MaterialTheme.typography.body1,
        )
    }
}
