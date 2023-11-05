package com.marinj.shoppingwarfare.feature.category.detail.data.datasource.local

import com.marinj.shoppingwarfare.db.LocalProduct
import kotlinx.coroutines.flow.Flow

interface ProductDao {

    fun observeProductsForGivenCategoryId(categoryId: String): Flow<List<LocalProduct>>

    suspend fun upsertProduct(entity: LocalProduct)

    suspend fun deleteProductById(productId: String)
}
