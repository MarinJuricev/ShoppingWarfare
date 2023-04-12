package com.marinj.shoppingwarfare.core.model

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage

@JvmInline
value class ResourceColor private constructor(val value: Int) {
    companion object {
        fun ResourceColor(
            valueToValidate: Int?,
            tag: String? = null,
        ): Either<Failure, ResourceColor> = when {
            valueToValidate == null -> ErrorMessage("${tagOrDefault(tag)} can not be null").left()
            valueToValidate <= 0 -> ErrorMessage(
                "${tagOrDefault(tag)} value must be a positive number, got: $valueToValidate",
            ).left()
            else -> ResourceColor(value = valueToValidate).right()
        }

        private fun tagOrDefault(tag: String?): String = if (tag.isNullOrBlank()) "NonEmptyString" else tag
    }
}
