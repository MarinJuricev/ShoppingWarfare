package com.marinj.shoppingwarfare.feature.cart.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.components.DottedLine
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareIconButton
import com.marinj.shoppingwarfare.core.ext.expandOrCollapse
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewState
import com.marinj.shoppingwarfare.feature.cart.presentation.model.ReceiptStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartCheckoutInfo(
    viewState: CartViewState,
    coroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
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
            Button(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth(0.8f)
                    .padding(16.dp),
                enabled = viewState.cartData.isNotEmpty(),
                onClick = { /*TODO*/ }
            ) {
                Text(text = stringResource(id = R.string.checkout))
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
            ) {
                val receiptStatusColor by animateColorAsState(
                    targetValue = when (viewState.receiptStatus) {
                        ReceiptStatus.Empty -> Color.LightGray
                        ReceiptStatus.Error -> MaterialTheme.colors.error
                        is ReceiptStatus.Taken -> MaterialTheme.colors.primary
                    }
                )
                ShoppingWarfareIconButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    enabled = viewState.cartData.isNotEmpty() && viewState.receiptStatus == ReceiptStatus.Empty,
                    onClick = {
                        coroutineScope.launch {
                            bottomSheetScaffoldState.expandOrCollapse()
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.camera_icon),
                        tint = Color.White,
                        contentDescription = stringResource(R.string.camera_permission_denied)
                    )
                }
                Text(
                    text = when (viewState.receiptStatus) {
                        ReceiptStatus.Empty -> stringResource(R.string.no_receipt_added)
                        ReceiptStatus.Error -> stringResource(R.string.receipt_error)
                        is ReceiptStatus.Taken -> stringResource(R.string.receipt_taken)
                    },
                    textAlign = TextAlign.Center,
                    color = receiptStatusColor,
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }
}
