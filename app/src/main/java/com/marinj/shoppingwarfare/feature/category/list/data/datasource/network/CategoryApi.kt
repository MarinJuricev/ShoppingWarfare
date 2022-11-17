package com.marinj.shoppingwarfare.feature.category.list.data.datasource.network

import com.marinj.shoppingwarfare.feature.cart.data.model.RemoteCartItem
import kotlinx.coroutines.flow.Flow

interface CategoryApi {

    fun observeCartItems(): Flow<List<RemoteCartItem>>

    suspend fun addCartItem(cartItem: RemoteCartItem)
}