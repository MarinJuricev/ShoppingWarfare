package com.marinj.shoppingwarfare.feature.category.detail.data.repository

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.detail.data.datasource.local.ProductDao
import com.marinj.shoppingwarfare.feature.category.detail.data.datasource.network.ProductApi
import com.marinj.shoppingwarfare.feature.category.detail.data.mapper.DomainToLocalCategoryItemMapper
import com.marinj.shoppingwarfare.feature.category.detail.data.mapper.LocalCategoryProductsListToDomainProductMapper
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
    private val productDao: ProductDao,
    private val domainToLocalCategoryItemMapper: DomainToLocalCategoryItemMapper,
    private val localCategoryProductsListToDomainProductMapper: LocalCategoryProductsListToDomainProductMapper,
) : ProductRepository {

    override fun observeProducts(productId: String): Flow<List<Product>> =
        productDao.observeProductsForGivenCategoryId(productId)
            .map { localCategoryProductsListToDomainProductMapper.map(it) }

    override suspend fun upsertProduct(product: Product): Either<Failure, Unit> {
        val localProduct = domainToLocalCategoryItemMapper.map(product)
        return when (productDao.upsertProduct(localProduct)) {
            0L -> Failure.ErrorMessage("Error while adding new category product").buildLeft()
            else -> Unit.buildRight()
        }
    }

    override suspend fun deleteProductById(productId: String): Either<Failure, Unit> =
        productDao.deleteProductById(productId).buildRight()
}
