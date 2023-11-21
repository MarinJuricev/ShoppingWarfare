package com.marinj.shoppingwarfare.feature.cart.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareIconButton
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartStatusState
import com.marinj.shoppingwarfare.feature.cart.presentation.model.ReceiptStatus
import com.marinj.shoppingwarfare.feature.cart.presentation.model.UiCartItem
import com.marinj.shoppingwarfare.ui.TextBodyLarge

@ExperimentalMaterialApi
@Composable
fun CartReceiptStatus(
    viewState: CartStatusState,
    uiCartItems: List<UiCartItem>,
    onReceiptClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxHeight(),
    ) {
        val receiptStatusColor by animateColorAsState(
            targetValue = when (viewState.receiptStatus) {
                ReceiptStatus.Empty -> Color.LightGray
                ReceiptStatus.Error -> MaterialTheme.colors.error
                is ReceiptStatus.Taken -> MaterialTheme.colors.primary
            },
            label = "receiptStatusColor",
        )
        ShoppingWarfareIconButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            enabled = uiCartItems.isNotEmpty(),
            onClick = onReceiptClick,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.camera_icon),
                tint = Color.White,
                contentDescription = stringResource(R.string.camera_permission_denied),
            )
        }
        TextBodyLarge(
            text = when (viewState.receiptStatus) {
                ReceiptStatus.Empty -> stringResource(R.string.no_receipt_added)
                ReceiptStatus.Error -> stringResource(R.string.receipt_error)
                is ReceiptStatus.Taken -> stringResource(R.string.receipt_taken)
            },
            textAlign = TextAlign.Center,
            color = receiptStatusColor,
        )
    }
}
