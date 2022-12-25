package com.marinj.shoppingwarfare.feature.category.list.domain.model

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight

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
            title.isNullOrEmpty() -> ErrorMessage("Title can not be empty or null got: $title").buildLeft()
            backgroundColor == null -> ErrorMessage("BackgroundColor can not be null").buildLeft()
            titleColor == null -> ErrorMessage("TitleColor can not be null").buildLeft()
            else -> Category(
                id,
                title,
                backgroundColor,
                titleColor,
            ).buildRight()
        }
    }
}
