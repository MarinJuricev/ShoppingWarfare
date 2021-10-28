package com.marinj.shoppingwarfare.core.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow

class Navigator {

    private val _navigationAction = Channel<NavigationAction>()
    val navigationAction = _navigationAction.consumeAsFlow()

    fun emitAction(navigationAction: NavigationAction) {
        _navigationAction.trySend(navigationAction)
    }
}
