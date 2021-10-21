package com.marinj.shoppingwarfare.feature.cart.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.marinj.shoppingwarfare.core.components.BottomNavigationItem
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.feature.cart.presentation.CartPage

const val CART_ROOT = "cartRoot"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.buildCartGraph(
    sendTopBar: (TopBarEvent) -> Unit,
) {
    navigation(
        startDestination = BottomNavigationItem.Cart.route,
        route = CART_ROOT,
    ) {
        composable(BottomNavigationItem.Cart.route) {
            CartPage(
                setupTopBar = sendTopBar,
            )
        }
    }
}
