package com.marinj.shoppingwarfare.feature.cart.data.repository

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor() : CartRepository {

    override fun observeCartItems(): Flow<List<CartItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertCartItem(cartItem: CartItem): Either<Failure, Unit> {
        return Unit.buildRight()
    }

    override suspend fun deleteCartItemById(cartItemId: String): Either<Failure, Unit> {
        TODO("Not yet implemented")
    }
}
