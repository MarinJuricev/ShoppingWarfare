package com.marinj.shoppingwarfare.core.model

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage

@JvmInline
value class NonEmptyString private constructor(val value: String) {
    companion object {
        fun of(value: String?): Either<Failure, NonEmptyString> = when {
            value.isNullOrBlank() -> ErrorMessage("Can not provide a value").left()
            else -> NonEmptyString(value).right()
        }
    }
}
