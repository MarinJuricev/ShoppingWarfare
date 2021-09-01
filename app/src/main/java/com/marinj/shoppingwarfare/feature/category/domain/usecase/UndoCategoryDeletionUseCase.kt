package com.marinj.shoppingwarfare.feature.category.domain.usecase

import com.marinj.shoppingwarfare.feature.category.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.domain.repository.CategoryRepository
import javax.inject.Inject

class UndoCategoryDeletionUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {

    suspend operator fun invoke(category: Category) =
        categoryRepository.upsertCategory(category)
}
