package com.marinj.shoppingwarfare.feature.category.list.data.repository

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.list.data.datasource.local.CategoryDao
import com.marinj.shoppingwarfare.feature.category.list.data.datasource.network.CategoryApi
import com.marinj.shoppingwarfare.feature.category.list.data.mapper.DomainToLocalCategoryMapper
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val categoryApi: CategoryApi,
    private val domainToLocalCategoryMapper: DomainToLocalCategoryMapper,
) : CategoryRepository {
    override fun observeCategories(): Flow<List<Category>> =
        categoryDao.observeCategories().map { localCategoryList ->
            localCategoryList.map { it.toDomain() }
        }

    override suspend fun upsertCategory(category: Category): Either<Failure, Unit> {
        val localCategory = domainToLocalCategoryMapper.map(category)
        return when (categoryDao.upsertCategory(localCategory)) {
            // TODO Insert the category name instead of default new category category.name
            0L -> Failure.ErrorMessage("Error while adding new category").buildLeft()
            else -> Unit.buildRight()
        }
    }

    override suspend fun deleteCategoryById(id: String): Either<Failure, Unit> =
        categoryDao.deleteCategoryById(id).buildRight()
}
