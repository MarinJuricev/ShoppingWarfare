package com.marinj.shoppingwarfare.feature.category.list.domain.usecase

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import javax.inject.Inject

class DeleteCategory @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {

    suspend operator fun invoke(categoryId: String): Either<Failure, Unit> =
        categoryRepository.deleteCategoryById(categoryId)
}
