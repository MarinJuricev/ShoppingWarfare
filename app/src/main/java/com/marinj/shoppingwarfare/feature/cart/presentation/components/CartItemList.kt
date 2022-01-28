package com.marinj.shoppingwarfare.feature.cart.presentation.components

import CartItemCard
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.util.fastForEach
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent
import com.marinj.shoppingwarfare.feature.cart.presentation.model.UiCartItem

@Composable
fun CartItemList(
    uiCartItems: List<UiCartItem>,
    onCartEvent: (CartEvent) -> Unit,
) {
    LazyColumn {
        uiCartItems.fastForEach { uiCartItem ->
            when (uiCartItem) {
                is UiCartItem.Header -> stickyHeader {
                    CartHeader(uiCartItem.categoryName)
                }
                is UiCartItem.Content -> item(uiCartItem.id) {
                    CartItemCard(
                        uiCartItem,
                        onCartEvent,
                    )
                }
            }
        }
    }
}
