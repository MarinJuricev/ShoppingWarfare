package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import arrow.core.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem

interface CheckoutCart {
    suspend operator fun invoke(
        cartItems: List<CartItem>,
        cartName: String,
        receiptPath: String?,
    ): Either<Failure, Unit>
}