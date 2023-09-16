package com.marinj.shoppingwarfare.feature.category.detail.data.datasource.network

import arrow.core.Either
import com.marinj.shoppingwarfare.core.exception.SHWException
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.detail.data.model.RemoteProduct
import kotlinx.coroutines.flow.Flow

interface ProductApi {

    @Throws(SHWException::class)
    fun observeProductsForGivenCategoryId(
        categoryId: String,
    ): Flow<List<RemoteProduct>>

    suspend fun addProduct(product: RemoteProduct): Either<Failure, Unit>

    suspend fun deleteProduct(product: RemoteProduct): Either<Failure, Unit>
}
