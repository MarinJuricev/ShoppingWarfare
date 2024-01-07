package com.marinj.shoppingwarfare.feature.cart.presentation.components

import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.util.fastForEachIndexed
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartTab
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartTabEvent
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartTabEvent.CartTabPositionUpdated

@Composable
fun CartTabs(
    selectedTabPosition: Int,
    cartTabs: List<CartTab>,
    onCartEvent: (CartTabEvent) -> Unit,
) {
    TabRow(selectedTabIndex = selectedTabPosition) {
        cartTabs.fastForEachIndexed { index, cartTab ->
            Tab(
                selected = index == selectedTabPosition,
                onClick = { onCartEvent(CartTabPositionUpdated(index)) },
                text = { Text(stringResource(id = cartTab.name)) },
            )
        }
    }
}
