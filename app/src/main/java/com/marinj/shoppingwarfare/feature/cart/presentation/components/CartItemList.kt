package com.marinj.shoppingwarfare.feature.cart.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.R
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

@Composable
fun CartItemCard(
    cartItem: CartItem,
    onCartEvent: (CartEvent) -> Unit,
) {
    Card {
        Row {
            Text(text = cartItem.name)
            QuantityPicker(cartItem.quantity, onCartEvent)
        }
    }
}

@Composable
fun QuantityPicker(
    quantity: Int,
    onCartEvent: (CartEvent) -> Unit
) {
    Row {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                modifier = Modifier
                    .size(16.dp),
                painter = painterResource(id = R.drawable.minus_icon),
                tint = if (MaterialTheme.colors.isLight) Color.LightGray else Color.White,
                contentDescription = stringResource(R.string.decrease_quantity)
            )
        }
        Text(quantity.toString())
        IconButton(onClick = { /*TODO*/ }) {
            Icons.Default.Add
            Icon(
                modifier = Modifier
                    .size(16.dp),
                painter = painterResource(id = R.drawable.add_icon),
                tint = if (MaterialTheme.colors.isLight) Color.LightGray else Color.White,
                contentDescription = stringResource(R.string.increase_quantity)
            )
        }
    }
}
