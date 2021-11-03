package com.marinj.shoppingwarfare.core.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow

// Heavily inspired from https://funkymuse.dev/posts/compose_hilt_mm/
class Navigator {

    private val _navigationEvent = Channel<NavigationEvent>()
    val navigationEvent = _navigationEvent.consumeAsFlow()

    suspend fun emitDestination(navigationEvent: NavigationEvent) {
        _navigationEvent.send(navigationEvent)
    }
}
