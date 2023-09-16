package com.marinj.shoppingwarfare.feature.category.detail.data.repository

import arrow.core.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.detail.data.datasource.local.ProductDao
import com.marinj.shoppingwarfare.feature.category.detail.data.datasource.network.ProductApi
import com.marinj.shoppingwarfare.feature.category.detail.data.model.toRemote
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
    private val productDao: ProductDao,
) : ProductRepository {

    override fun observeProducts(productId: String) = combine(
        syncApiToLocal(productId),
        productsFromLocal(productId),
    ) { _, productsFromLocal ->
        productsFromLocal
    }

    private fun productsFromLocal(
        productId: String,
    ): Flow<List<Product>> =
        productDao.observeProductsForGivenCategoryId(productId).map { localCategoryList ->
            localCategoryList.flatMap { localCategoryProduct ->
                localCategoryProduct.toProductOrNull().filterNotNull()
            }
        }

    private fun syncApiToLocal(
        productId: String,
    ) = productApi.observeProductsForGivenCategoryId(productId)
        .map { remoteProducts ->
            remoteProducts.map { remoteProduct ->
                productDao.upsertProduct(remoteProduct.toLocal())
            }
        }

    override suspend fun upsertProduct(
        product: Product,
    ): Either<Failure, Unit> = product
        .toRemote()
        .let { productApi.addProduct(it) }

    override suspend fun deleteProduct(
        product: Product,
    ): Either<Failure, Unit> = coroutineScope {
        val apiDeferred: Deferred<Either<Failure, Unit>> = async { productApi.deleteProduct(product.toRemote()) }
        val localDeferred: Deferred<Unit> = async { productDao.deleteProductById(product.id.value) }

        localDeferred.await() // We ignore the  result of the local operation, we only care about the RemoteAPI result
        apiDeferred.await()
    }
}
