package com.marinj.shoppingwarfare.feature.cart.presentation.components

import CartItemCard
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CartItemList(
    cartData: Map<String, List<CartItem>>,
    onCartEvent: (CartEvent) -> Unit,
) {

    LazyColumn {
        cartData.forEach { (categoryName, cartItems) ->
            stickyHeader {
                CartHeader(categoryName)
            }
            items(cartItems) { cartItem ->
                CartItemCard(
                    cartItem,
                    onCartEvent,
                )
            }
        }
    }
}

@Composable
fun CartHeader(categoryName: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primaryVariant)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = stringResource(R.string.cart_category_name, categoryName),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.surface,
        )
    }
}
