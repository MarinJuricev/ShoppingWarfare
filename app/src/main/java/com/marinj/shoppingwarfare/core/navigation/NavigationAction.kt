package com.marinj.shoppingwarfare.core.navigation

import androidx.navigation.NamedNavArgument

abstract class NavigationAction(
    val destination: String,
    val arguments: List<NamedNavArgument> = emptyList(),
) {
    abstract fun generateDestination(
        navigationArgument: NavigationArgument? = null
    ): String
}
