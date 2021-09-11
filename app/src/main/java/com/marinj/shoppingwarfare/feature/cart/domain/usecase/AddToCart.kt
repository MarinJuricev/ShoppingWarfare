package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import javax.inject.Inject

class AddToCart @Inject constructor(
    private val cartRepository: CartRepository
) {

    suspend operator fun invoke(cartItem: CartItem): Either<Failure, Unit> =
        cartRepository.upsertCartItem(cartItem)
}
