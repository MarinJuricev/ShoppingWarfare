package com.marinj.shoppingwarfare.feature.category.data.repository

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.data.datasource.CategoryDao
import com.marinj.shoppingwarfare.feature.category.data.model.LocalCategory
import com.marinj.shoppingwarfare.feature.category.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val localToDomainCategoryMapper: Mapper<Category, LocalCategory>,
    private val domainToLocalCategoryMapper: Mapper<LocalCategory, Category>,
) : CategoryRepository {
    override fun observeCategories(): Flow<List<Category>> =
        categoryDao.getCategories().map { localCategoryList ->
            localCategoryList.map { localToDomainCategoryMapper.map(it) }
        }

    override suspend fun upsertCategory(category: Category): Either<Failure, Unit> {
        val localCategory = domainToLocalCategoryMapper.map(category)
        return when (categoryDao.upsertCategory(localCategory)) {
            0L -> Failure.ErrorMessage("Error while adding new category").buildLeft()
            else -> Unit.buildRight()
        }
    }

    override suspend fun deleteCategoryById(id: Int): Either<Failure, Unit> =
        categoryDao.deleteCategoryById(id).buildRight()
}