package com.marinj.shoppingwarfare.feature.cart.presentation.components

import CartItemCard
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent

@Composable
fun CartItemList(
    cartItems: List<CartItem>,
    onCartEvent: (CartEvent) -> Unit,
) {
    LazyColumn {
        items(cartItems) { cartItem ->
            CartItemCard(
                cartItem,
                onCartEvent,
            )
        }
    }
}
