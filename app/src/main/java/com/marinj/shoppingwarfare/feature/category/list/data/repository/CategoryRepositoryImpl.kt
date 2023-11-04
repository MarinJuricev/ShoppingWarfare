package com.marinj.shoppingwarfare.feature.category.list.data.repository

import com.marinj.shoppingwarfare.feature.category.list.data.datasource.local.CategoryDao
import com.marinj.shoppingwarfare.feature.category.list.data.datasource.network.CategoryApi
import com.marinj.shoppingwarfare.feature.category.list.data.mapper.toDomain
import com.marinj.shoppingwarfare.feature.category.list.data.model.toRemote
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val categoryApi: CategoryApi,
) : CategoryRepository {

    //TODO: Improve this, combine is not the operator that we want, atleast with a implementation like this,
    // we don't want to wait for both of them to complete, what we want is to get it from the local source
    // as fast as possible and eventually sync the remote source with the local source
    override fun observeCategories(): Flow<List<Category>> = combine(
        syncApiToLocal(),
        categoriesFromLocal(),
    ) { _, categoriesFromLocal ->
        categoriesFromLocal
    }

    private fun syncApiToLocal() = categoryApi.observeCategories()
        .map { remoteCategories ->
            remoteCategories.map { remoteCategory ->
                categoryDao.upsertCategory(remoteCategory.toLocal())
            }
        }

    private fun categoriesFromLocal(): Flow<List<Category>> =
        categoryDao.observeCategories().map { localCategoryList ->
            localCategoryList.mapNotNull { it.toDomain().getOrNull() }
        }

    override suspend fun upsertCategory(category: Category) =
        categoryApi.addCategoryItem(category.toRemote())

    override suspend fun deleteCategoryById(id: String) =
        categoryApi.deleteCategoryItemById(id).onRight {
            categoryDao.deleteCategoryById(id)
        }
}
