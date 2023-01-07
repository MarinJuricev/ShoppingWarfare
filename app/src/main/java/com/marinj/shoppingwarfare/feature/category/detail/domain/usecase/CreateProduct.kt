package com.marinj.shoppingwarfare.feature.category.detail.domain.usecase

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Either.Left
import com.marinj.shoppingwarfare.core.result.Either.Right
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import javax.inject.Inject

class CreateProduct @Inject constructor(
    private val validateProduct: ValidateProduct,
    private val uuidGenerator: () -> String,
    private val productRepository: ProductRepository,
) {

    suspend operator fun invoke(
        categoryId: String,
        categoryName: String,
        productName: String?,
    ): Either<Failure, Unit> {
        return when (val result = validateProduct(productName)) {
            is Left -> result
            is Right -> productRepository.upsertProduct(
                Product(
                    id = uuidGenerator(),
                    categoryId = categoryId,
                    categoryName = categoryName,
                    name = productName!!,
                ),
            )
        }
    }
}
