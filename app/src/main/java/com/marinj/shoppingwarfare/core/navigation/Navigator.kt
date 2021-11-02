package com.marinj.shoppingwarfare.core.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow

class Navigator {

    private val _navigationEvent = Channel<NavigationEvent>()
    val navigationEvent = _navigationEvent.consumeAsFlow()

    suspend fun emitDestination(navigationEvent: NavigationEvent) {
        _navigationEvent.send(navigationEvent)
    }
}
