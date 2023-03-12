package com.marinj.shoppingwarfare.feature.category.list.domain.model

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage

data class Category private constructor(
    val id: String,
    val title: String,
    val backgroundColor: Int,
    val titleColor: Int,
) {
    companion object {
        fun of(
            id: String,
            title: String?,
            backgroundColor: Int?,
            titleColor: Int?,
        ): Either<Failure, Category> = when {
            title.isNullOrBlank() -> ErrorMessage("Title can not be empty or null got: $title").left()
            backgroundColor == null -> ErrorMessage("BackgroundColor can not be null").left()
            titleColor == null -> ErrorMessage("TitleColor can not be null").left()
            else -> Category(
                id,
                title,
                backgroundColor,
                titleColor,
            ).right()
        }
    }
}
