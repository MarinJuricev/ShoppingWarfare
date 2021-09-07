package com.marinj.shoppingwarfare.feature.categorydetail.data.repository

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.categorydetail.data.datasource.CategoryDetailDao
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalCategoryProduct
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.CategoryProduct
import com.marinj.shoppingwarfare.feature.categorydetail.domain.repository.CategoryDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryDetailRepositoryImpl @Inject constructor(
    private val categoryDetailDao: CategoryDetailDao,
    private val domainToLocalCategoryProductMapper: Mapper<LocalCategoryProduct, CategoryProduct>,
    private val localToDomainCategoryProductMapper: Mapper<CategoryProduct, LocalCategoryProduct>,
) : CategoryDetailRepository {

    override fun observeCategoryProducts(categoryId: String): Flow<List<CategoryProduct>> =
        categoryDetailDao.observeCategoryItemsForGivenCategoryId().map { localCategoryItems ->
            localCategoryItems.flatMap {
                it.categoryProductList.map {
                    CategoryProduct(
                        it.categoryProductId,
                        it.name,
                    )
                }
            }
        }

    override suspend fun upsertCategoryProduct(categoryProduct: CategoryProduct): Either<Failure, Unit> {
        val localCategoryItem = domainToLocalCategoryProductMapper.map(categoryProduct)
        return when (categoryDetailDao.upsertCategoryItem(localCategoryItem)) {
            0L -> Failure.ErrorMessage("Error while adding new category product").buildLeft()
            else -> Unit.buildRight()
        }
    }

    override suspend fun deleteCategoryProductById(categoryProductId: String): Either<Failure, Unit> {
        TODO("Not yet implemented")
    }
}
