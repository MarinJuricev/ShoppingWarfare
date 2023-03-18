package com.marinj.shoppingwarfare.feature.category.detail.domain.usecase

import arrow.core.Either
import com.marinj.shoppingwarfare.core.result.Failure

interface DeleteProduct {

    suspend operator fun invoke(
        productId: String,
    ): Either<Failure, Unit>
}