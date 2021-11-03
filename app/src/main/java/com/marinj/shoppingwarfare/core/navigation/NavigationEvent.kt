package com.marinj.shoppingwarfare.core.navigation

import androidx.navigation.NavOptionsBuilder

sealed class NavigationEvent {
    object NavigateUp : NavigationEvent()
    object NavigateBack : NavigationEvent()
    class Destination(
        val destinations: String,
        val builder: NavOptionsBuilder.() -> Unit = {}
    ) : NavigationEvent()
}
