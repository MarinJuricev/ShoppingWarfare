package com.marinj.shoppingwarfare.feature.category.domain.usecase

import com.marinj.shoppingwarfare.feature.category.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategories @Inject constructor(
    private val categoryRepository: CategoryRepository
) {

    operator fun invoke(): Flow<List<Category>> =
        categoryRepository.observeCategories()
}