package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import arrow.core.Either
import com.marinj.shoppingwarfare.core.result.Failure

interface DeleteCartItem {
    suspend operator fun invoke(cartItemId: String): Either<Failure, Unit>
}
