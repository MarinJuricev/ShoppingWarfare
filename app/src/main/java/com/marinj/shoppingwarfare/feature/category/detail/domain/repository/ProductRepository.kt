package com.marinj.shoppingwarfare.feature.category.detail.domain.repository

import arrow.core.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun observeProducts(productId: String): Flow<List<Product>>
    suspend fun upsertProduct(product: Product): Either<Failure, Unit>
    suspend fun deleteProduct(product: Product): Either<Failure, Unit>
}
