package com.marinj.shoppingwarfare.feature.history.detail.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.theme.textColor
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryCartItem
import com.marinj.shoppingwarfare.ui.TextBodyLarge
import com.marinj.shoppingwarfare.ui.TextBodyMedium

@Composable
fun HistoryDetailQuantity(historyCartItem: HistoryCartItem) {
    Column {
        TextBodyMedium(
            text = stringResource(
                id = R.string.history_detail_quantity,
            ),
            color = MaterialTheme.textColor(),
        )
        TextBodyLarge(
            text = historyCartItem.quantity.toString(),
        )
    }
}
