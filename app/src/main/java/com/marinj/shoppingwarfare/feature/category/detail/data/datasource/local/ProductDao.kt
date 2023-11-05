package com.marinj.shoppingwarfare.feature.category.detail.data.datasource.local

import kotlinx.coroutines.flow.Flow

interface ProductDao {

    fun observeProductsForGivenCategoryId(categoryId: String): Flow<List<com.marinj.shoppingwarfare.db.LocalProduct>>

    suspend fun upsertProduct(entity: com.marinj.shoppingwarfare.db.LocalProduct): Long

    suspend fun deleteProductById(productId: String)
}
