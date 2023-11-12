package com.marinj.shoppingwarfare.feature.cart.presentation.model

sealed interface CartEvent {
    data class CartTabPositionUpdated(val updatedCartTabPosition: Int) : CartEvent
}
