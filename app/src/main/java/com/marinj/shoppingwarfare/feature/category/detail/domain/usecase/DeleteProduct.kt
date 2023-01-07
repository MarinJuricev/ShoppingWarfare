package com.marinj.shoppingwarfare.feature.category.detail.domain.usecase

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import javax.inject.Inject

class DeleteProduct @Inject constructor(
    private val productRepository: ProductRepository,
) {

    suspend operator fun invoke(
        productId: String,
    ): Either<Failure, Unit> = productRepository.deleteProductById(productId)
}
