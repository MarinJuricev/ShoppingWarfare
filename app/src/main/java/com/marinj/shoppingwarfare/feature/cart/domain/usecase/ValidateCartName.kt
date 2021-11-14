package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import javax.inject.Inject

class ValidateCartName @Inject constructor() {

    operator fun invoke(
        cartName: String,
    ): Either<Failure, Unit> =
        when {
            cartName.isBlank() -> ErrorMessage("CartName can't be empty").buildLeft()
            else -> Unit.buildRight()
        }
}
