package com.marinj.shoppingwarfare.feature.cart.presentation.navigation

import com.marinj.shoppingwarfare.core.navigation.NavigationDestination

object CartDestination : NavigationDestination {

    override fun route(): String = CART_ROUTE

    private const val CART_ROUTE = "cart"
}
