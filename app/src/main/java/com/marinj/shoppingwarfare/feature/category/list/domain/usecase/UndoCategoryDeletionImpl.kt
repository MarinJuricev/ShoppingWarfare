package com.marinj.shoppingwarfare.feature.category.list.domain.usecase

import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import javax.inject.Inject

class UndoCategoryDeletionImpl @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : UndoCategoryDeletion {

    override suspend operator fun invoke(category: Category) =
        categoryRepository.upsertCategory(category)
}
