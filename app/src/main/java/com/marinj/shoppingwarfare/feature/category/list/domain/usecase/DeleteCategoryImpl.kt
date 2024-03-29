package com.marinj.shoppingwarfare.feature.category.list.domain.usecase

import arrow.core.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import javax.inject.Inject

class DeleteCategoryImpl @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : DeleteCategory {

    override suspend operator fun invoke(
        categoryId: String,
    ): Either<Failure, Unit> = categoryRepository.deleteCategoryById(categoryId)
}
