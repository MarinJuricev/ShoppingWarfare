package com.marinj.shoppingwarfare.feature.cart.presentation.components

import CartItemCard
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CartItemList(
    cartItems: List<CartItem>,
    onCartEvent: (CartEvent) -> Unit,
) {
    // TODO expose the viewState as a map, don't do it in the composable
    val categoryNames: Map<String, List<CartItem>> = cartItems.groupBy { it.categoryName }

    LazyColumn {
        categoryNames.forEach { (categoryNames, cartGroupedItems) ->
            stickyHeader {
                Text(categoryNames)
            }
            items(cartGroupedItems) { cartItem ->
                CartItemCard(
                    cartItem,
                    onCartEvent,
                )
            }
        }
    }
}
