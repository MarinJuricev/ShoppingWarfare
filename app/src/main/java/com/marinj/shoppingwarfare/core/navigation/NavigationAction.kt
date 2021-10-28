package com.marinj.shoppingwarfare.core.navigation

import androidx.navigation.NamedNavArgument

abstract class NavigationAction(
    val destination: String,
    val arguments: List<NamedNavArgument> = emptyList()
)
