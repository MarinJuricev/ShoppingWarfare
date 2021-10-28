package com.marinj.shoppingwarfare.core.navigation

import androidx.navigation.NamedNavArgument

abstract class NavigationAction(
    val arguments: List<NamedNavArgument> = emptyList()
) {
    abstract val route: String
    abstract val destination: String
}
