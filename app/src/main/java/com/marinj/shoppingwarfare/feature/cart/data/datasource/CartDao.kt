package com.marinj.shoppingwarfare.feature.cart.data.datasource

import com.marinj.shoppingwarfare.db.LocalCartItem
import kotlinx.coroutines.flow.Flow

interface CartDao {

    fun observeCartItems(): Flow<List<LocalCartItem>>

    fun observeCartItemsCount(): Flow<Int?>

    suspend fun updateCartItemQuantity(id: String, newQuantity: Int)

    suspend fun updateCartItemIsInBasket(id: String, updatedIsInBasket: Boolean)

    suspend fun upsertCartItem(entity: LocalCartItem)

    suspend fun deleteCartItemById(id: String)

    suspend fun getCartItemById(id: String): LocalCartItem?

    suspend fun deleteCart()
}
