package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import javax.inject.Inject

class UpdateCartItemQuantityImpl @Inject constructor(
    private val cartRepository: CartRepository,
) : UpdateCartItemQuantity {
    override suspend operator fun invoke(
        cartItemId: String,
        newQuantity: Int,
    ) = cartRepository.updateCartItemQuantity(
        cartItemId = cartItemId,
        newQuantity = newQuantity,
    )
}
