package com.marinj.shoppingwarfare.feature.category.detail.domain.usecase

import arrow.core.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product

interface DeleteProduct {

    suspend operator fun invoke(
        product: Product,
    ): Either<Failure, Unit>
}
