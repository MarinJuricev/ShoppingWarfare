package com.marinj.shoppingwarfare.core.viewmodel.badge

sealed interface BadgeEvent {
    object StartObservingBadgesCount : BadgeEvent
}
