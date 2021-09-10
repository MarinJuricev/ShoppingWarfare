package com.marinj.shoppingwarfare.feature.categorydetail.domain.usecase

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.categorydetail.domain.repository.CategoryDetailRepository
import javax.inject.Inject

class DeleteProduct @Inject constructor(
    private val categoryDetailRepository: CategoryDetailRepository,
) {

    suspend operator fun invoke(
        productId: String,
    ): Either<Failure, Unit> = categoryDetailRepository.deleteCategoryProductById(productId)
}