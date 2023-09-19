package com.marinj.shoppingwarfare.feature.cart.data.remote

import arrow.core.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.cart.data.model.RemoteCartItem
import kotlinx.coroutines.flow.Flow

interface CartApi {

    fun observeCartItems(): Flow<List<RemoteCartItem>>

    suspend fun addCartItem(cartItem: RemoteCartItem): Either<Failure, Unit>
    suspend fun updateCartItemQuantity(cartItemId: String, newQuantity: Int): Either<Failure, Unit>
    suspend fun updateCartItemIsInBasket(cartItemId: String, updatedIsInBasket: Boolean): Either<Failure, Unit>
    suspend fun deleteCartItemById(id: String): Either<Failure, Unit>
    suspend fun deleteCart(): Either<Failure, Unit>
}
