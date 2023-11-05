package com.marinj.shoppingwarfare.feature.category.detail.data.datasource.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.marinj.shoppingwarfare.core.dispatcher.DispatcherProvider
import com.marinj.shoppingwarfare.db.Database
import com.marinj.shoppingwarfare.db.LocalProduct
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductDaoImpl @Inject constructor(
    private val database: Database,
    private val dispatcherProvider: DispatcherProvider,
) : ProductDao {
    override fun observeProductsForGivenCategoryId(
        categoryId: String,
    ): Flow<List<LocalProduct>> =
        database.productQueries
            .selectProductsForGivenCategoryId(categoryId)
            .asFlow()
            .mapToList(dispatcherProvider.io)

    override suspend fun upsertProduct(entity: LocalProduct): Long =
        database.productQueries.upsertProduct(
            id = entity.id,
            categoryId = entity.categoryId,
            title = entity.title,
            quantity = entity.quantity,
            isAddedToCart = entity.isAddedToCart,
        )

    override suspend fun deleteProductById(productId: String) {
        TODO("Not yet implemented")
    }
}