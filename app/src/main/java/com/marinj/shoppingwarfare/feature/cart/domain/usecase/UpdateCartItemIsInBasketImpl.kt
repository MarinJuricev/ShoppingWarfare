package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import javax.inject.Inject

class UpdateCartItemIsInBasketImpl @Inject constructor(
    private val cartRepository: CartRepository,
) : UpdateCartItemIsInBasket {
    override suspend operator fun invoke(
        cartItemId: String,
        updatedIsInBasket: Boolean,
    ) = cartRepository.updateCartItemIsInBasket(cartItemId, updatedIsInBasket)
}
