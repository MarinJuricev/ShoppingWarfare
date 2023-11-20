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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.components.DottedLine
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartStatusEvent
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartStatusEvent.*
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartStatusState
import com.marinj.shoppingwarfare.feature.cart.presentation.model.UiCartItem
import com.marinj.shoppingwarfare.ui.PrimaryOutlinedButton
import com.marinj.shoppingwarfare.ui.PrimaryTextButton
import com.marinj.shoppingwarfare.ui.SWTextField

@Composable
fun CartCheckoutInfo(
    viewState: CartStatusState,
    uiCartItems: List<UiCartItem>,
    onReceiptClick: () -> Unit,
    onCartEvent: (CartStatusEvent) -> Unit,
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
                SWTextField(
                    label = { Text(stringResource(id = R.string.cart_name)) },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    value = viewState.cartName,
                    onValueChange = { updatedCartName ->
                        onCartEvent(CartNameUpdated(updatedCartName))
                    },
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    PrimaryOutlinedButton(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .weight(1f),
                        enabled = false,
                        onClick = { onCartEvent(CheckoutClicked(uiCartItems)) },
                    ) {
                        Text(text = stringResource(id = R.string.share_cart))
                    }
                    PrimaryTextButton(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .weight(1f),
                        enabled = uiCartItems.isNotEmpty(),
                        text = stringResource(id = R.string.checkout),
                        onClick = { onCartEvent(CheckoutClicked(uiCartItems)) },
                    )
                }
            }
            CartReceiptStatus(
                viewState = viewState,
                onReceiptClick = onReceiptClick,
            )
        }
    }
}
