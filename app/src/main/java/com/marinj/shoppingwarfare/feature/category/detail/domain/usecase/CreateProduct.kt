package com.marinj.shoppingwarfare.feature.category.detail.domain.usecase

import arrow.core.Either
import com.marinj.shoppingwarfare.core.result.Failure

interface CreateProduct {
    suspend operator fun invoke(
        categoryId: String,
        categoryName: String,
        productName: String?,
    ): Either<Failure, Unit>
}
