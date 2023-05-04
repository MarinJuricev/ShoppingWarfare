package com.marinj.shoppingwarfare.feature.category.detail.domain.usecase

import arrow.core.raise.either
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product.Companion.Product
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import javax.inject.Inject

class CreateProductImpl @Inject constructor(
    private val uuidGenerator: () -> String,
    private val productRepository: ProductRepository,
) : CreateProduct {

    override suspend operator fun invoke(
        categoryId: String,
        categoryName: String,
        productName: String?,
    ) = either {
        val product = Product(
            id = uuidGenerator(),
            categoryId = categoryId,
            categoryName = categoryName,
            name = productName,
        ).bind()

        productRepository.upsertProduct(product).bind()
    }
}
