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
    cartData: Map<String, List<CartItem>>,
    onCartEvent: (CartEvent) -> Unit,
) {

    LazyColumn {
        cartData.forEach { (categoryNames, cartGroupedItems) ->
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
