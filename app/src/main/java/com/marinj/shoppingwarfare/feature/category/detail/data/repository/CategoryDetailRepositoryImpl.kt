package com.marinj.shoppingwarfare.feature.category.detail.data.repository

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.detail.data.datasource.ProductDao
import com.marinj.shoppingwarfare.feature.category.detail.data.mapper.DomainToLocalCategoryItemMapper
import com.marinj.shoppingwarfare.feature.category.detail.data.mapper.LocalCategoryProductsListToDomainProductMapper
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.CategoryDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryDetailRepositoryImpl @Inject constructor(
    private val productDao: ProductDao,
    private val domainToLocalCategoryItemMapper: DomainToLocalCategoryItemMapper,
    private val localCategoryProductsListToDomainProductMapper: LocalCategoryProductsListToDomainProductMapper,
) : CategoryDetailRepository {

    override fun observeCategoryProducts(categoryId: String): Flow<List<Product>> =
        productDao.observeProductsForGivenCategoryId(categoryId)
            .map { localCategoryProductsListToDomainProductMapper.map(it) }

    override suspend fun upsertCategoryProduct(product: Product): Either<Failure, Unit> {
        val localProduct = domainToLocalCategoryItemMapper.map(product)
        return when (productDao.upsertProduct(localProduct)) {
            0L -> Failure.ErrorMessage("Error while adding new category product").buildLeft()
            else -> Unit.buildRight()
        }
    }

    override suspend fun deleteCategoryProductById(productId: String): Either<Failure, Unit> =
        productDao.deleteProductById(productId).buildRight()
}
