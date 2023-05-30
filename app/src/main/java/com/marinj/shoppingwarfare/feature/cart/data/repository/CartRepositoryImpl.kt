package com.marinj.shoppingwarfare.feature.cart.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.feature.cart.data.datasource.CartDao
import com.marinj.shoppingwarfare.feature.cart.data.model.toLocal
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
) : CartRepository {

    override fun observeCartItems(): Flow<List<CartItem>> =
        cartDao.observeCartItems().map { localCartItems ->
            localCartItems.mapNotNull { it.toDomain().getOrNull() }
        }

    override fun observeCartItemsCount(): Flow<Int?> =
        cartDao.observeCartItemsCount()

    override suspend fun updateCartItemQuantity(
        cartItemId: String,
        newQuantity: Int,
    ): Either<Failure, Unit> = cartDao.updateCartItemQuantity(cartItemId, newQuantity).right()

    override suspend fun updateCartItemIsInBasket(
        cartItemId: String,
        updatedIsInBasket: Boolean,
    ): Either<Failure, Unit> = cartDao.updateCartItemIsInBasket(cartItemId, updatedIsInBasket).right()

    override suspend fun upsertCartItem(cartItem: CartItem): Either<Failure, Unit> {
        val localCartItem = cartItem.toLocal()
        return when (cartDao.upsertCartItem(localCartItem)) {
            0L -> ErrorMessage("Error while adding ${localCartItem.name}").left()
            else -> Unit.right()
        }
    }

    override suspend fun deleteCartItemById(id: String): Either<Failure, Unit> =
        cartDao.deleteCartItemById(id).right()

    override suspend fun getCartItemById(id: String): Either<Failure, CartItem> {
        return when (val result = cartDao.getCartItemById(id)) {
            null -> ErrorMessage("No cartItem present with the id: $id").left()
            else -> result.toDomain()
        }
    }

    override suspend fun dropCurrentCart(): Either<Failure, Unit> =
        cartDao.deleteCart().right()
}
