package com.marinj.shoppingwarfare.feature.category.detail.data.repository

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.detail.data.datasource.local.ProductDao
import com.marinj.shoppingwarfare.feature.category.detail.data.datasource.network.ProductApi
import com.marinj.shoppingwarfare.feature.category.detail.data.model.toLocal
import com.marinj.shoppingwarfare.feature.category.detail.data.model.toRemote
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import com.marinj.shoppingwarfare.feature.category.list.data.model.toRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
    private val productDao: ProductDao,
) : ProductRepository {

    override fun observeProducts(productId: String) =
        productsFromLocal(productId)
            .onStart { syncApiToLocal(productId) }

    private fun syncApiToLocal(
        productId: String,
    ) = productApi.observeProductsForGivenCategoryId(productId)
        .onEach { remoteProducts ->
            remoteProducts.map { remoteProduct ->
                productDao.upsertProduct(remoteProduct.toLocal())
            }
        }

    private fun productsFromLocal(
        productId: String,
    ): Flow<List<Product>> =
        productDao.observeProductsForGivenCategoryId(productId).map { localCategoryList ->
            localCategoryList.flatMap { localCategoryProduct ->
                localCategoryProduct.toProductOrNull().filterNotNull()
            }
        }

    override suspend fun upsertProduct(product: Product): Either<Failure, Unit> =
        product.toRemote().let { remoteProduct ->
            productApi.addProduct(remoteProduct)
        }

    override suspend fun deleteProductById(
        productId: String,
    ): Either<Failure, Unit> = productApi.deleteProductById(productId).also {
        productDao.deleteProductById(productId)
    }
}
