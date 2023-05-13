package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import arrow.core.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem

interface AddToCart {
    suspend operator fun invoke(cartItem: CartItem): Either<Failure, Unit>
}