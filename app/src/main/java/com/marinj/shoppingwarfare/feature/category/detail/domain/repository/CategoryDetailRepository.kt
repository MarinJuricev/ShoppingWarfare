package com.marinj.shoppingwarfare.feature.category.detail.domain.repository

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CategoryDetailRepository {
    fun observeCategoryProducts(categoryId: String): Flow<List<Product>>
    suspend fun upsertCategoryProduct(product: Product): Either<Failure, Unit>
    suspend fun deleteCategoryProductById(productId: String): Either<Failure, Unit>
}
