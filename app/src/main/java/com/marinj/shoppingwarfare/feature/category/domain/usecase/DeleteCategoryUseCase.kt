package com.marinj.shoppingwarfare.feature.category.domain.usecase

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.domain.repository.CategoryRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {

    suspend operator fun invoke(categoryId: String): Either<Failure, Unit> =
        categoryRepository.deleteCategoryById(categoryId)
}
