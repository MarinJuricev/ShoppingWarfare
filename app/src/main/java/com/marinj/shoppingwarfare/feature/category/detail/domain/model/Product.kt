package com.marinj.shoppingwarfare.feature.category.detail.domain.model

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight

data class Product private constructor(
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
            name: String,
        ): Either<Failure, Product> = when {
            name.isBlank() -> ErrorMessage("Name can not be empty got: $name").buildLeft()
            else -> Product(
                id = id,
                categoryId = categoryId,
                categoryName = categoryName,
                name = name,
            ).buildRight()
        }
    }
}
