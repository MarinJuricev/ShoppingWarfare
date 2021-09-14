package com.marinj.shoppingwarfare.feature.cart.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
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
        when {
            viewState.isLoading -> ShoppingWarfareLoadingIndicator()
        }
    }
}
