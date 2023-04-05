package com.marinj.shoppingwarfare.core.model

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage

@JvmInline
value class NonEmptyString private constructor(val value: String) {
    companion object {
        fun NonEmptyString(valueToValidate: String?): Either<Failure, NonEmptyString> = when {
            valueToValidate.isNullOrBlank() -> ErrorMessage("NonEmptyString can not be null or empty").left()
            else -> NonEmptyString(value = valueToValidate).right()
        }
    }
}
