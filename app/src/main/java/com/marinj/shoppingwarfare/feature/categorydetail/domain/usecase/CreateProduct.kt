package com.marinj.shoppingwarfare.feature.categorydetail.domain.usecase

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Either.Left
import com.marinj.shoppingwarfare.core.result.Either.Right
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.Product
import com.marinj.shoppingwarfare.feature.categorydetail.domain.repository.CategoryDetailRepository
import javax.inject.Inject

class CreateProduct @Inject constructor(
    private val validateProduct: ValidateProduct,
    private val uuidGenerator: () -> String,
    private val categoryDetailRepository: CategoryDetailRepository,
) {

    suspend operator fun invoke(
        categoryId: String,
        categoryName: String,
        productName: String?,
    ): Either<Failure, Unit> {
        return when (val result = validateProduct(productName)) {
            is Left -> result
            is Right -> categoryDetailRepository.upsertCategoryProduct(
                Product(
                    id = uuidGenerator(),
                    categoryId = categoryId,
                    categoryName = categoryName,
                    name = productName!!
                )
            )
        }
    }
}
