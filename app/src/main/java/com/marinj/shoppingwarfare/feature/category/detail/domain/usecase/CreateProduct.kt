package com.marinj.shoppingwarfare.feature.category.detail.domain.usecase

import arrow.core.Either
import arrow.core.continuations.either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import javax.inject.Inject

class CreateProduct @Inject constructor(
    private val uuidGenerator: () -> String,
    private val productRepository: ProductRepository,
) {

    suspend operator fun invoke(
        categoryId: String,
        categoryName: String,
        productName: String?,
    ): Either<Failure, Unit> = either {
        val product = Product.of(
            id = uuidGenerator(),
            categoryId = categoryId,
            categoryName = categoryName,
            name = productName,
        ).bind()

        productRepository.upsertProduct(product).bind()
    }
}
