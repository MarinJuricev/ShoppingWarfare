package com.marinj.shoppingwarfare.feature.category.list.data.datasource.local

import com.marinj.shoppingwarfare.db.LocalCategory
import kotlinx.coroutines.flow.Flow

interface CategoryDao {

    fun observeCategories(): Flow<List<LocalCategory>>

    suspend fun upsertCategory(entity: LocalCategory)

    suspend fun deleteCategoryById(id: String)
}
