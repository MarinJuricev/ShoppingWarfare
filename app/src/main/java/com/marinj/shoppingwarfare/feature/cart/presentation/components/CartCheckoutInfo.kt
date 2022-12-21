package com.marinj.shoppingwarfare.feature.cart.presentation.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.components.DottedLine
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.CartNameUpdated
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.CheckoutClicked
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewState

@Composable
fun CartCheckoutInfo(
    viewState: CartViewState,
    onReceiptClick: () -> Unit,
    onCartEvent: (CartEvent) -> Unit,
    scrollState: ScrollState = rememberScrollState(),
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        DottedLine(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth(),
            step = 10.dp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight()
                    .verticalScroll(scrollState),
            ) {
                OutlinedTextField(
                    label = { Text(stringResource(id = R.string.cart_name)) },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    value = viewState.cartName,
                    onValueChange = { updatedCartName ->
                        onCartEvent(CartNameUpdated(updatedCartName))
                    },
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Button(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .weight(1f),
                        enabled = viewState.isPremiumUser,
                        onClick = { onCartEvent(CheckoutClicked) },
                    ) {
                        Text(text = stringResource(id = R.string.share_cart))
                    }
                    Button(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .weight(1f),
                        enabled = viewState.uiCartItems.isNotEmpty(),
                        onClick = { onCartEvent(CheckoutClicked) },
                    ) {
                        Text(text = stringResource(id = R.string.checkout))
                    }
                }
            }
            CartReceiptStatus(
                viewState = viewState,
                onReceiptClick = onReceiptClick,
            )
        }
    }
}
