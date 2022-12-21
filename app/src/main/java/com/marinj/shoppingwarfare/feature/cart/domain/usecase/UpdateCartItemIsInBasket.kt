package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import javax.inject.Inject

class UpdateCartItemIsInBasket @Inject constructor(
    private val cartRepository: CartRepository,
) {
    suspend operator fun invoke(
        cartItemId: String,
        updatedIsInBasket: Boolean,
    ): Either<Failure, Unit> =
        cartRepository.updateCartItemIsInBasket(cartItemId, updatedIsInBasket)
}
