package com.marinj.shoppingwarfare.feature.categorydetail.data.repository

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.categorydetail.data.datasource.ProductDao
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalCategoryProducts
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalProduct
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.Product
import com.marinj.shoppingwarfare.feature.categorydetail.domain.repository.CategoryDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryDetailRepositoryImpl @Inject constructor(
    private val productDao: ProductDao,
    private val domainToLocalProductMapper: Mapper<LocalProduct, Product>,
    private val localCategoryProductsListToDomainProductMapper: Mapper<List<Product>, List<LocalCategoryProducts>>,
) : CategoryDetailRepository {

    override fun observeCategoryProducts(categoryId: String): Flow<List<Product>> =
        productDao.observeProductsForGivenCategoryId(categoryId)
            .map { localCategoryProductsListToDomainProductMapper.map(it) }

    override suspend fun upsertCategoryProduct(product: Product): Either<Failure, Unit> {
        val localProduct = domainToLocalProductMapper.map(product)
        return when (productDao.upsertProduct(localProduct)) {
            0L -> Failure.ErrorMessage("Error while adding new category product").buildLeft()
            else -> Unit.buildRight()
        }
    }

    override suspend fun deleteCategoryProductById(productId: String): Either<Failure, Unit> =
        productDao.deleteProductById(productId).buildRight()
}
