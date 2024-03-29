package com.marinj.shoppingwarfare.feature.cart.presentation.components

import CartItemCard
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.util.fastForEach
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartListEvent
import com.marinj.shoppingwarfare.feature.cart.presentation.model.UiCartItem

@Composable
fun CartItemList(
    uiCartItems: List<UiCartItem>,
    onCartEvent: (CartListEvent) -> Unit,
) {
    LazyColumn {
        uiCartItems.fastForEach { uiCartItem ->
            when (uiCartItem) {
                is UiCartItem.Header -> stickyHeader(
                    key = uiCartItem.categoryName,
                ) {
                    CartHeader(uiCartItem.categoryName)
                }
                is UiCartItem.Content -> item(
                    key = uiCartItem.id,
                ) {
                    CartItemCard(
                        uiCartItem,
                        onCartEvent,
                    )
                }
            }
        }
    }
}
