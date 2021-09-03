package com.marinj.shoppingwarfare.feature.categorydetail.domain.repository

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.CategoryItem
import kotlinx.coroutines.flow.Flow

interface CategoryDetailRepository {
    fun observeCategoryItems(categoryId: String): Flow<List<CategoryItem>>
    suspend fun upsertCategoryItem(categoryItem: CategoryItem): Either<Failure, Unit>
    suspend fun deleteCategoryItemById(categoryItemId: String): Either<Failure, Unit>
}
