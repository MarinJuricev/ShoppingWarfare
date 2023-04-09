package com.marinj.shoppingwarfare.core.model

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage

@JvmInline
value class ResourceColor private constructor(val value: Int) {
    companion object {
        fun ResourceColor(valueToValidate: Int?): Either<Failure, ResourceColor> = when {
            valueToValidate == null -> ErrorMessage("ResourceColor can not be null").left()
            valueToValidate <= 0 -> ErrorMessage("ResourceColor value must be a positive number, got: $valueToValidate").left()
            else -> ResourceColor(value = valueToValidate).right()
        }
    }
}
