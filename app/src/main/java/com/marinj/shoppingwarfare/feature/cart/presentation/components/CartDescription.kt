package com.marinj.shoppingwarfare.feature.cart.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.marinj.shoppingwarfare.R

@Composable
fun CartDescription(
    modifier: Modifier = Modifier,
    cartItemName: String,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.cart_name),
            style = MaterialTheme.typography.body2,
            color = if (MaterialTheme.colors.isLight) Color.LightGray else Color.White
        )
        Text(
            text = cartItemName,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
        )
    }
}
