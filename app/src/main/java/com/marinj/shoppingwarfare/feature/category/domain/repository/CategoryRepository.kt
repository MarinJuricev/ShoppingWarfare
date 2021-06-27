package com.marinj.shoppingwarfare.feature.category.domain.repository

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun observeCategories(): Flow<List<Category>>
    suspend fun upsertCategory(category: Category): Either<Failure, Unit>
    suspend fun deleteCategoryById(id: Int): Either<Failure, Unit>
}
