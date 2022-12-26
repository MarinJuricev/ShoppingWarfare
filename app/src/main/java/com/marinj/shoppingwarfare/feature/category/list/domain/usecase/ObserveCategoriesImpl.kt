package com.marinj.shoppingwarfare.feature.category.list.domain.usecase

import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCategoriesImpl @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : ObserveCategories {

    override operator fun invoke(): Flow<List<Category>> =
        categoryRepository.observeCategories()
}
