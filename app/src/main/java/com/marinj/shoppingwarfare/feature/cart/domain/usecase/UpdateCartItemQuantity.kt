package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import arrow.core.Either
import com.marinj.shoppingwarfare.core.result.Failure

interface UpdateCartItemQuantity {
    suspend operator fun invoke(
        cartItemId: String,
        newQuantity: Int,
    ): Either<Failure, Unit>
}