package com.marinj.shoppingwarfare.feature.createcategory.data.repository

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.data.datasource.CategoryDao
import com.marinj.shoppingwarfare.feature.category.data.model.LocalCategory
import com.marinj.shoppingwarfare.feature.category.domain.model.Category
import com.marinj.shoppingwarfare.feature.createcategory.domain.repository.CreateCategoryRepository
import javax.inject.Inject

class CreateCategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val domainToLocalCategoryMapper: Mapper<LocalCategory, Category>,
) : CreateCategoryRepository {
    override suspend fun createCategory(category: Category): Either<Failure, Unit> {
        val localCategory = domainToLocalCategoryMapper.map(category)
        return when (categoryDao.upsertCategory(localCategory)) {
            0L -> Failure.ErrorMessage("Error while adding new category").buildLeft()
            else -> Unit.buildRight()
        }
    }
}
