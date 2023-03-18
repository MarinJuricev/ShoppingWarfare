package com.marinj.shoppingwarfare.feature.category.detail.domain.usecase

import arrow.core.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import javax.inject.Inject

class DeleteProductImpl @Inject constructor(
    private val productRepository: ProductRepository,
): DeleteProduct {

    override suspend operator fun invoke(
        productId: String,
    ): Either<Failure, Unit> = productRepository.deleteProductById(productId)
}
