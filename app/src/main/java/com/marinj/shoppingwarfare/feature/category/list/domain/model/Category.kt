package com.marinj.shoppingwarfare.feature.category.list.domain.model

import arrow.core.Either
import arrow.core.raise.either
import com.marinj.shoppingwarfare.core.model.NonEmptyString
import com.marinj.shoppingwarfare.core.model.NonEmptyString.Companion.NonEmptyString
import com.marinj.shoppingwarfare.core.model.ResourceColor
import com.marinj.shoppingwarfare.core.model.ResourceColor.Companion.ResourceColor
import com.marinj.shoppingwarfare.core.result.Failure

data class Category private constructor(
    val id: NonEmptyString,
    val title: NonEmptyString,
    val backgroundColor: ResourceColor,
    val titleColor: ResourceColor,
) {
    companion object {
        fun Category(
            id: String,
            title: String?,
            backgroundColor: Int?,
            titleColor: Int?,
        ): Either<Failure, Category> = either {
            val mappedId = NonEmptyString(valueToValidate = id, tag = "id").bind()
            val mappedTitle = NonEmptyString(valueToValidate = title, "title").bind()
            val mappedBackgroundColor = ResourceColor(valueToValidate = backgroundColor).bind()
            val mappedTitleColor = ResourceColor(valueToValidate = titleColor).bind()

            Category(
                mappedId,
                mappedTitle,
                mappedBackgroundColor,
                mappedTitleColor,
            )
        }
    }
}
