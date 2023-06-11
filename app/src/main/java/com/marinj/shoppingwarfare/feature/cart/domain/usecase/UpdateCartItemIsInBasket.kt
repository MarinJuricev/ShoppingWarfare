package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import arrow.core.Either
import com.marinj.shoppingwarfare.core.result.Failure

interface UpdateCartItemIsInBasket {
    suspend operator fun invoke(
        cartItemId: String,
        updatedIsInBasket: Boolean,
    ): Either<Failure, Unit>
}
