package com.marinj.shoppingwarfare.feature.cart.data.remote

import com.marinj.shoppingwarfare.feature.cart.data.model.RemoteCartItem
import kotlinx.coroutines.flow.Flow

interface CartApi {

    fun observeCartItems(): Flow<List<RemoteCartItem>>

    suspend fun addCartItem(cartItem: RemoteCartItem)
}