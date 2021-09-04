package com.marinj.shoppingwarfare.feature.categorydetail.domain.usecase

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.CategoryItem
import com.marinj.shoppingwarfare.feature.categorydetail.domain.repository.CategoryDetailRepository
import javax.inject.Inject

class CreateCategoryItem @Inject constructor(
    private val validateCategoryItem: ValidateCategoryItem,
    private val categoryDetailRepository: CategoryDetailRepository
) {

    suspend operator fun invoke(
        categoryItem: CategoryItem,
    ): Either<Failure, Unit> {

        return categoryDetailRepository.upsertCategoryItem(categoryItem)
    }
}
