package com.marinj.shoppingwarfare.feature.category.detail.domain.usecase

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import javax.inject.Inject

class DeleteProduct @Inject constructor(
    private val categoryDetailRepository: ProductRepository,
) {

    suspend operator fun invoke(
        productId: String,
    ): Either<Failure, Unit> = categoryDetailRepository.deleteProductById(productId)
}
