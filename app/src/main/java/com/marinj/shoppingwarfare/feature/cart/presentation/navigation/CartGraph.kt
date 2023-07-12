package com.marinj.shoppingwarfare.feature.cart.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.feature.cart.presentation.CartScreen

const val CART_ROOT = "cartRoot"

fun NavGraphBuilder.buildCartGraph(
    sendTopBar: (TopBarEvent) -> Unit,
) {
    navigation(
        startDestination = CartDestination.route(),
        route = CART_ROOT,
    ) {
        composable(CartDestination.route()) {
            CartScreen(
                setupTopBar = sendTopBar,
            )
        }
    }
}
