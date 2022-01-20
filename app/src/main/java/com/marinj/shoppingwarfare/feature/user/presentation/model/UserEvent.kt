package com.marinj.shoppingwarfare.feature.user.presentation.model

sealed interface UserEvent {
    object Start : UserEvent
    object End : UserEvent
}
