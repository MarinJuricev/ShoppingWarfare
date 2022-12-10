package com.marinj.shoppingwarfare.feature.category.list.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.list.data.datasource.local.CategoryDao
import com.marinj.shoppingwarfare.feature.category.list.data.datasource.network.CategoryApi
import com.marinj.shoppingwarfare.feature.category.list.data.model.RemoteCategoryItem
import com.marinj.shoppingwarfare.feature.category.list.data.model.toRemote
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val categoryApi: CategoryApi,
) : CategoryRepository {

    init {
        syncApiWithLocal()
    }

    private fun syncApiWithLocal() = GlobalScope.launch {
        categoryApi.observeCategoryItems()
            .collectLatest { remoteCategories ->
                remoteCategories.map { remoteCategory ->
                    categoryDao.upsertCategory(remoteCategory.toLocal())
                }
            }
    }

    override fun observeCategories(): Flow<List<Category>> {
        return categoryDao.observeCategories().map { localCategoryList ->
            localCategoryList.map { it.toDomain() }
        }
    }

    override suspend fun upsertCategory(category: Category): Either<Failure, Unit> =
        category.toRemote().let { remoteCategory ->
            categoryApi.addCategoryItem(remoteCategory)
        }

    override suspend fun deleteCategoryById(id: String): Either<Failure, Unit> =
        categoryApi.deleteCategoryItemById(id)
}
