package com.marinj.shoppingwarfare.feature.category.list.data.repository

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.list.data.datasource.local.CategoryDao
import com.marinj.shoppingwarfare.feature.category.list.data.datasource.network.CategoryApi
import com.marinj.shoppingwarfare.feature.category.list.data.model.toRemote
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val categoryApi: CategoryApi,
) : CategoryRepository {

    override fun observeCategories(): Flow<List<Category>> =
        syncApiToLocal().flatMapLatest { categoriesFromLocal() }

    private fun syncApiToLocal() = categoryApi.observeCategoryItems()
        .onEach { remoteCategories ->
            remoteCategories.map { remoteCategory ->
                categoryDao.upsertCategory(remoteCategory.toLocal())
            }
        }

    private fun categoriesFromLocal() = categoryDao.observeCategories()
        .map { localCategoryList ->
            localCategoryList.mapNotNull { it.toDomain() }
        }

    override suspend fun upsertCategory(category: Category): Either<Failure, Unit> =
        category.toRemote().let { remoteCategory ->
            categoryApi.addCategoryItem(remoteCategory)
        }

    override suspend fun deleteCategoryById(id: String): Either<Failure, Unit> =
        categoryApi.deleteCategoryItemById(id).also {
            categoryDao.deleteCategoryById(id)
        }
}
