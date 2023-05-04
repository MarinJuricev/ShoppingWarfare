package com.marinj.shoppingwarfare.feature.category.detail.domain.model

import arrow.core.Either
import arrow.core.raise.either
import com.marinj.shoppingwarfare.core.model.NonEmptyString
import com.marinj.shoppingwarfare.core.model.NonEmptyString.Companion.NonEmptyString
import com.marinj.shoppingwarfare.core.result.Failure

data class Product private constructor(
    val id: NonEmptyString,
    val categoryId: NonEmptyString,
    val categoryName: NonEmptyString,
    val name: NonEmptyString,
) {
    companion object {
        fun Product(
            id: String,
            categoryId: String,
            categoryName: String,
            name: String?,
        ): Either<Failure, Product> = either {
            val mappedId = NonEmptyString(valueToValidate = id, tag = "id").bind()
            val mappedCategoryId = NonEmptyString(valueToValidate = categoryId, tag = "categoryId").bind()
            val mappedCategoryName = NonEmptyString(valueToValidate = categoryName, tag = "categoryName").bind()
            val mappedName = NonEmptyString(valueToValidate = name, tag = "name").bind()

            Product(
                id = mappedId,
                categoryId = mappedCategoryId,
                categoryName = mappedCategoryName,
                name = mappedName,
            )
        }
    }
}
