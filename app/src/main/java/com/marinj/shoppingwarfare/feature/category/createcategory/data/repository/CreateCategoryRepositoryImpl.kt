package com.marinj.shoppingwarfare.feature.category.createcategory.data.repository

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.createcategory.domain.repository.CreateCategoryRepository
import com.marinj.shoppingwarfare.feature.category.list.data.datasource.CategoryDao
import com.marinj.shoppingwarfare.feature.category.list.data.mapper.DomainToLocalCategoryMapper
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import javax.inject.Inject

class CreateCategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val domainToLocalCategoryMapper: DomainToLocalCategoryMapper,
) : CreateCategoryRepository {
    override suspend fun createCategory(category: Category): Either<Failure, Unit> {
        val localCategory = domainToLocalCategoryMapper.map(category)
        return when (categoryDao.upsertCategory(localCategory)) {
            0L -> Failure.ErrorMessage("Error while adding new category").buildLeft()
            else -> Unit.buildRight()
        }
    }
}
