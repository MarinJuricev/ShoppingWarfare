package com.marinj.shoppingwarfare.feature.categorydetail.data.repository

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.categorydetail.data.datasource.CategoryDetailDao
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalCategoryItem
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.CategoryItem
import com.marinj.shoppingwarfare.feature.categorydetail.domain.repository.CategoryDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryDetailRepositoryImpl @Inject constructor(
    private val categoryDetailDao: CategoryDetailDao,
    private val domainToLocalCategoryItemMapper: Mapper<LocalCategoryItem, CategoryItem>,
    private val localToDomainCategoryItemMapper: Mapper<CategoryItem, LocalCategoryItem>,
) : CategoryDetailRepository {

    override fun observeCategoryItems(categoryId: String): Flow<List<CategoryItem>> =
        categoryDetailDao.observeCategoryItemsForGivenCategoryId().map { localCategoryItems ->
            localCategoryItems.flatMap {
                it.categoryItemList.map {
                    CategoryItem(
                        it.categoryItemId,
                        it.name,
                    )
                }
            }
        }

    override suspend fun upsertCategoryItem(categoryItem: CategoryItem): Either<Failure, Unit> {
        val localCategoryItem = domainToLocalCategoryItemMapper.map(categoryItem)
        return when (categoryDetailDao.upsertCategoryItem(localCategoryItem)) {
            0L -> Failure.ErrorMessage("Error while adding new category item").buildLeft()
            else -> Unit.buildRight()
        }
    }

    override suspend fun deleteCategoryItemById(categoryItemId: String): Either<Failure, Unit> {
        TODO("Not yet implemented")
    }
}
