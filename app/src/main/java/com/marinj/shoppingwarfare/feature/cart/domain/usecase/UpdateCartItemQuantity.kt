package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import javax.inject.Inject

class UpdateCartItemQuantity @Inject constructor(
    private val cartRepository: CartRepository,
) {
    suspend operator fun invoke(
        cartItemId: String,
        newQuantity: Int,
    ) = cartRepository.updateCartItemQuantity(
        cartItemId = cartItemId,
        newQuantity = newQuantity,
    )
}
