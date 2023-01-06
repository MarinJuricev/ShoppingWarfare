package com.marinj.shoppingwarfare.core.navigation

import kotlinx.coroutines.flow.Flow

// Heavily inspired from https://funkymuse.dev/posts/compose_hilt_mm/
interface Navigator {

    val navigationEvent: Flow<NavigationEvent>

    suspend fun emitDestination(navigationEvent: NavigationEvent)
}
