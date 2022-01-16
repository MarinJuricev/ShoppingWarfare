package com.marinj.shoppingwarfare.core.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

// Heavily inspired from https://funkymuse.dev/posts/compose_hilt_mm/
class Navigator {

    private val _navigationEvent = Channel<NavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    suspend fun emitDestination(navigationEvent: NavigationEvent) {
        _navigationEvent.send(navigationEvent)
    }
}
