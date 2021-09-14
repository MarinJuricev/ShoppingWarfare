package com.marinj.shoppingwarfare.feature.cart.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.components.DottedLine
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareEmptyScreen
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareLoadingIndicator
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareTopBar

@Composable
fun CartPage(
    cartViewModel: CartViewModel = hiltViewModel(),
) {

    val viewState by cartViewModel.viewState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ShoppingWarfareTopBar()
        }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .fillMaxWidth()
            ) {
                when {
                    viewState.isLoading -> ShoppingWarfareLoadingIndicator()
                    viewState.cartItems.isEmpty() -> ShoppingWarfareEmptyScreen(
                        message = stringResource(
                            R.string.empty_cart_message
                        )
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.2f)
                    .fillMaxWidth()
            ) {
                DottedLine(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth(),
                    step = 10.dp,
                )
            }
        }
    }
}
