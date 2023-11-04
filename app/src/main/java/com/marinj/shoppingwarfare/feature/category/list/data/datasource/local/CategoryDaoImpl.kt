package com.marinj.shoppingwarfare.feature.category.list.data.datasource.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.marinj.shoppingwarfare.core.dispatcher.DispatcherProvider
import com.marinj.shoppingwarfare.db.Database
import com.marinj.shoppingwarfare.db.LocalCategory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryDaoImpl @Inject constructor(
    private val database: Database,
    private val dispatcherProvider: DispatcherProvider,
) : CategoryDao {
    override fun observeCategories(): Flow<List<LocalCategory>> =
        database.categoryQueries
            .selectAllCategories()
            .asFlow()
            .mapToList(dispatcherProvider.io)

    override suspend fun upsertCategory(entity: LocalCategory) =
        database.categoryQueries
            .upsertCategory(
                id = entity.id,
                title = entity.title,
                backgroundColor = entity.backgroundColor,
                titleColor = entity.titleColor,
            )

    override suspend fun deleteCategoryById(id: String) =
        database.categoryQueries.deleteCategoryById(id)
}