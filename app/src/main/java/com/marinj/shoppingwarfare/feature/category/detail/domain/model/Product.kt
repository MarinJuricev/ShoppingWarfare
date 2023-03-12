package com.marinj.shoppingwarfare.feature.category.detail.domain.model

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage

data class Product(
    val id: String,
    val categoryId: String,
    val categoryName: String,
    val name: String,
) {
    companion object {
        fun of(
            id: String,
            categoryId: String,
            categoryName: String,
            name: String?,
        ): Either<Failure, Product> {
            return when {
                name.isNullOrBlank() -> ErrorMessage("Name can not be empty got: $name").left()
                categoryName.isBlank() -> ErrorMessage("CategoryName can not be empty got: $categoryName").left()
                categoryId.isBlank() -> ErrorMessage("CategoryId can not be empty got: $categoryId").left()
                else -> Product(
                    id = id,
                    categoryId = categoryId,
                    categoryName = categoryName,
                    name = name,
                ).right()
            }
        }
    }
}
