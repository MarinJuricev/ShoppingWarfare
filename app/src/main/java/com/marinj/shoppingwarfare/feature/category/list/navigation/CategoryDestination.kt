package com.marinj.shoppingwarfare.feature.category.list.navigation

import com.marinj.shoppingwarfare.core.navigation.NavigationDestination

object CategoryDestination : NavigationDestination {

    override fun route(): String = CATEGORY_ROUTE

    private const val CATEGORY_ROUTE = "category"
}
