@file:Suppress("ktlint:filename")

package com.marinj.shoppingwarfare.core.fixture

import com.marinj.shoppingwarfare.core.navigation.NavigationEvent
import com.marinj.shoppingwarfare.core.navigation.Navigator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object FakeNavigator : Navigator {

    val receivedEvents = MutableSharedFlow<NavigationEvent>()
    override val navigationEvent = receivedEvents.asSharedFlow()

    override suspend fun emitDestination(navigationEvent: NavigationEvent) {
        receivedEvents.emit(navigationEvent)
    }
}
