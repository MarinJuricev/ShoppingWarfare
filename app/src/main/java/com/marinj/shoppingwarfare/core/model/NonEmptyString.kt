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
            value.isNullOrBlank() -> ErrorMessage("NonEmptyString can not be null or empty").left()
            else -> NonEmptyString(value).right()
        }
    }
}
