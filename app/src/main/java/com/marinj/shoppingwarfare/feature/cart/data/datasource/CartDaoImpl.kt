package com.marinj.shoppingwarfare.feature.cart.data.datasource

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.marinj.shoppingwarfare.core.dispatcher.DispatcherProvider
import com.marinj.shoppingwarfare.db.Database
import com.marinj.shoppingwarfare.db.LocalCartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartDaoImpl @Inject constructor(
    private val database: Database,
    private val dispatcherProvider: DispatcherProvider,
) : CartDao {
    override fun observeCartItems(): Flow<List<LocalCartItem>> = database
        .localCartItemQueries
        .observeCartItems()
        .asFlow()
        .mapToList(dispatcherProvider.io)

    override fun observeCartItemsCount(): Flow<Int?> = database
        .localCartItemQueries
        .observeCartItemsCount()
        .asFlow()
        .map { it.executeAsOneOrNull()?.SUM?.toInt() ?: 0 }

    override suspend fun updateCartItemQuantity(id: String, newQuantity: Int) = database
        .localCartItemQueries
        .updateCartItemQuantity(
            id = id,
            quantity = newQuantity.toLong(),
        )

    override suspend fun updateCartItemIsInBasket(id: String, updatedIsInBasket: Boolean) = database
        .localCartItemQueries
        .updateCartItemIsInBasket(
            id = id,
            isInBasket = updatedIsInBasket,
        )

    override suspend fun upsertCartItem(entity: LocalCartItem): Unit = database
        .localCartItemQueries
        .updateCartItem(
            id = entity.id,
            name = entity.name,
            quantity = entity.quantity,
            isInBasket = entity.isInBasket,
            categoryName = entity.categoryName,
        )

    override suspend fun deleteCartItemById(id: String) = database
        .localCartItemQueries
        .deleteCartItemById(id)

    override suspend fun getCartItemById(id: String): LocalCartItem? = database
        .localCartItemQueries
        .getCartItemById(id)
        .executeAsOneOrNull()

    override suspend fun deleteCart() = database
        .localCartItemQueries
        .deleteCart()
}
