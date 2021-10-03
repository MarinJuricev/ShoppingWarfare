package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import javax.inject.Inject

class UpdateCartItemQuantity @Inject constructor(
    private val cartRepository: CartRepository,
) {
    suspend operator fun invoke(
        cartItemToUpdate: CartItem,
        newQuantity: Int,
    ): Either<Failure, Unit> {
        val updatedCartItem = cartItemToUpdate.copy(
            quantity = newQuantity,
        )

        return cartRepository.upsertCartItem(updatedCartItem)
    }
}
