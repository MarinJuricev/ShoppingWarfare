package com.marinj.shoppingwarfare.feature.category.list.data.repository

import com.marinj.shoppingwarfare.feature.category.list.data.datasource.local.CategoryDao
import com.marinj.shoppingwarfare.feature.category.list.data.datasource.network.CategoryApi
import com.marinj.shoppingwarfare.feature.category.list.data.model.toRemote
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val categoryApi: CategoryApi,
) : CategoryRepository {

    override fun observeCategories(): Flow<List<Category>> =
        categoriesFromLocal()
            .onStart { syncApiToLocal() }

    private fun categoriesFromLocal(): Flow<List<Category>> =
        categoryDao.observeCategories().map { localCategoryList ->
            localCategoryList.mapNotNull { it.toDomain().getOrNull() }
        }

    private fun syncApiToLocal() = categoryApi.observeCategories()
        .map { remoteCategories ->
            remoteCategories.map { remoteCategory ->
                categoryDao.upsertCategory(remoteCategory.toLocal())
            }
        }

    override suspend fun upsertCategory(category: Category) =
        category.toRemote().let { remoteCategory ->
            categoryApi.addCategoryItem(remoteCategory)
        }

    override suspend fun deleteCategoryById(id: String) =
        categoryApi.deleteCategoryItemById(id).also {
            categoryDao.deleteCategoryById(id)
        }
}
