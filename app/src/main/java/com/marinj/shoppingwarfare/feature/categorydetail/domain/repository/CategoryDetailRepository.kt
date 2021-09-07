package com.marinj.shoppingwarfare.feature.categorydetail.domain.repository

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.CategoryProduct
import kotlinx.coroutines.flow.Flow

interface CategoryDetailRepository {
    fun observeCategoryProducts(categoryId: String): Flow<List<CategoryProduct>>
    suspend fun upsertCategoryProduct(categoryProduct: CategoryProduct): Either<Failure, Unit>
    suspend fun deleteCategoryProductById(categoryProductId: String): Either<Failure, Unit>
}
