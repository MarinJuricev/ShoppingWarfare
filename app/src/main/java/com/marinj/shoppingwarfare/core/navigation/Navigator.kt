package com.marinj.shoppingwarfare.core.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import javax.inject.Inject

class Navigator @Inject constructor() {

    private val _navigationAction = Channel<NavigationAction>()
    val navigationAction = _navigationAction.consumeAsFlow()

    fun emitAction(navigationAction: NavigationAction) {
        _navigationAction.trySend(navigationAction)
    }
}