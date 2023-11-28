package com.marinj.shoppingwarfare.feature.cart.presentation.model

sealed interface CartTabEvent {
    data class CartTabPositionUpdated(val updatedCartTabPosition: Int) : CartTabEvent
}
