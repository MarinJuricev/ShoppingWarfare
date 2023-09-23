package com.marinj.shoppingwarfare.feature.cart.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.theme.textColor
import com.marinj.shoppingwarfare.ui.TextBodyLarge
import com.marinj.shoppingwarfare.ui.TextBodyMedium

@Composable
fun CartDescription(
    modifier: Modifier = Modifier,
    cartItemName: String,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        TextBodyMedium(
            text = stringResource(R.string.cart_product_name),
            color = MaterialTheme.textColor(),
        )
        TextBodyLarge(
            text = cartItemName,
            textAlign = TextAlign.Center,
        )
    }
}
