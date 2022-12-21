package com.marinj.shoppingwarfare.feature.history.detail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.feature.cart.presentation.components.CartDescription
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryCartItem

@Composable
fun HistoryDetailCard(historyCartItem: HistoryCartItem) {
    Card(
        modifier = Modifier.padding(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CartDescription(cartItemName = historyCartItem.name)
            HistoryDetailQuantity(historyCartItem)
        }
    }
}
