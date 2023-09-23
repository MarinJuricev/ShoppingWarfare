package com.marinj.shoppingwarfare.feature.history.detail.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.feature.history.list.presentation.model.UiHistoryItem
import com.marinj.shoppingwarfare.ui.SWCard
import com.marinj.shoppingwarfare.ui.TextBodyLarge

@Composable
fun HistoryDetailCartInfo(uiHistoryItem: UiHistoryItem) {
    SWCard(
        containerColor = MaterialTheme.colors.primarySurface,
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextBodyLarge(
                text = stringResource(
                    R.string.history_detail_cart_name,
                    uiHistoryItem.cartName,
                ),
            )
            TextBodyLarge(
                text = stringResource(
                    R.string.history_detail_date,
                    uiHistoryItem.date,
                ),
            )
        }
    }
}
