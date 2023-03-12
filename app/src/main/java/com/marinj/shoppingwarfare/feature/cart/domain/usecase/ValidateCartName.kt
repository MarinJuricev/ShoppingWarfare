package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import javax.inject.Inject

class ValidateCartName @Inject constructor() {

    operator fun invoke(
        cartName: String,
    ): Either<Failure, Unit> = when {
        cartName.isBlank() -> ErrorMessage("CartName can't be empty").left()
        else -> Unit.right()
    }
}
