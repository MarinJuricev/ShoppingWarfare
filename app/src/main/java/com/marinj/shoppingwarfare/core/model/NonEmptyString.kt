package com.marinj.shoppingwarfare.core.model

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight

@JvmInline
value class NonEmptyString private constructor(val value: String) {
    companion object {
        fun of(value: String?): Either<Failure, NonEmptyString> = when {
            value.isNullOrBlank() -> ErrorMessage("Can not provide a value").left()
            else -> NonEmptyString(value).right()
        }
    }
}
