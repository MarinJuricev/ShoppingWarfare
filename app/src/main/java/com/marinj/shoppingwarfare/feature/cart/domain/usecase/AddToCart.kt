package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import javax.inject.Inject

class AddToCart @Inject constructor(
    private val cartRepository: CartRepository,
) {

    suspend operator fun invoke(cartItem: CartItem): Either<Failure, Unit> {
        return when (val result = cartRepository.getCartItemById(cartItem.id)) {
            is Either.Right -> increaseQuantityByOneForExistingCartItem(result.value)
            is Either.Left -> cartRepository.upsertCartItem(cartItem)
        }
    }

    private suspend fun increaseQuantityByOneForExistingCartItem(
        existingCartItem: CartItem,
    ): Either<Failure, Unit> {
        val updatedCartItem = existingCartItem.copy(quantity = existingCartItem.quantity.inc())

        return cartRepository.upsertCartItem(updatedCartItem)
    }
}
