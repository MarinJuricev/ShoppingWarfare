package com.marinj.shoppingwarfare.feature.categorydetail.domain.usecase

import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.CategoryItem
import com.marinj.shoppingwarfare.feature.categorydetail.domain.repository.CategoryDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoryItems @Inject constructor(
    private val categoryDetailRepository: CategoryDetailRepository,
) {

    operator fun invoke(categoryId: String): Flow<List<CategoryItem>> =
        categoryDetailRepository.observeCategoryItems(categoryId)
}
