package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import arrow.core.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import javax.inject.Inject

class AddToCart @Inject constructor(
    private val cartRepository: CartRepository,
) {

    suspend operator fun invoke(cartItem: CartItem) = cartRepository.getCartItemById(cartItem.id).fold(
        ifLeft = { cartRepository.upsertCartItem(cartItem)},
        ifRight = { increaseQuantityByOneForExistingCartItem(it) },
    )

    private suspend fun increaseQuantityByOneForExistingCartItem(
        existingCartItem: CartItem,
    ): Either<Failure, Unit> {
        val updatedCartItem = existingCartItem.copy(quantity = existingCartItem.quantity.inc())

        return cartRepository.upsertCartItem(updatedCartItem)
    }
}
