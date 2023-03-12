package com.marinj.shoppingwarfare.feature.category.list.domain.repository

import arrow.core.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun observeCategories(): Flow<List<Category>>
    suspend fun upsertCategory(category: Category): Either<Failure, Unit>
    suspend fun deleteCategoryById(id: String): Either<Failure, Unit>
}
