package com.marinj.shoppingwarfare.core.model

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage

@JvmInline
value class NonEmptyString private constructor(val value: String) {
    companion object {
        fun NonEmptyString(
            valueToValidate: String?,
            tag: String? = null,
        ): Either<Failure, NonEmptyString> = when {
            valueToValidate.isNullOrBlank() -> ErrorMessage("${tagOrDefault(tag)} can not be null or empty").left()
            else -> NonEmptyString(value = valueToValidate).right()
        }

        private fun tagOrDefault(tag: String?): String = if (tag.isNullOrBlank()) "NonEmptyString" else tag
    }
}
