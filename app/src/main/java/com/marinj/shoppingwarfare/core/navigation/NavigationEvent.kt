package com.marinj.shoppingwarfare.core.navigation

import androidx.navigation.NavOptionsBuilder

sealed interface NavigationEvent {
    object NavigateUp : NavigationEvent
    object NavigateBack : NavigationEvent
    data class Destination(
        val destination: String,
        val builder: NavOptionsBuilder.() -> Unit = {},
    ) : NavigationEvent
}
