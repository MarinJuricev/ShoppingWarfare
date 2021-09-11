package com.marinj.shoppingwarfare.feature.cart.domain.repository

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun observeCartItems(): Flow<List<CartItem>>
    suspend fun upsertCartItem(cartItem: CartItem): Either<Failure, Unit>
    suspend fun deleteCartItemById(cartItemId: String): Either<Failure, Unit>
}
