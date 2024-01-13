package com.marinj.shoppingwarfare.feature.cart.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.feature.cart.data.datasource.CartDao
import com.marinj.shoppingwarfare.feature.cart.data.mapper.toDomain
import com.marinj.shoppingwarfare.feature.cart.data.mapper.toLocal
import com.marinj.shoppingwarfare.feature.cart.data.model.toRemote
import com.marinj.shoppingwarfare.feature.cart.data.remote.CartApi
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
    private val cartApi: CartApi,
) : CartRepository {

    override fun observeCartItems(): Flow<List<CartItem>> = flowOf(Unit)
        .flatMapLatest {
            syncApiToLocal()
            cartFromLocal()
        }

    private fun syncApiToLocal() = cartApi.observeCartItems()
        .map { remoteCategories ->
            remoteCategories.map { remoteCategory ->
                cartDao.upsertCartItem(remoteCategory.toLocal())
            }
        }

    private fun cartFromLocal(): Flow<List<CartItem>> =
        cartDao.observeCartItems().map { localCategoryList ->
            localCategoryList.mapNotNull { it.toDomain().getOrNull() }
        }

    override fun observeCartItemsCount(): Flow<Int?> =
        cartDao.observeCartItemsCount()

    override suspend fun updateCartItemQuantity(
        cartItemId: String,
        newQuantity: Int,
    ): Either<Failure, Unit> = cartApi.updateCartItemQuantity(cartItemId, newQuantity)
        .onRight { cartDao.updateCartItemQuantity(cartItemId, newQuantity).right() }

    override suspend fun updateCartItemIsInBasket(
        cartItemId: String,
        updatedIsInBasket: Boolean,
    ): Either<Failure, Unit> =
        cartApi.updateCartItemIsInBasket(cartItemId, updatedIsInBasket)
            .onRight { cartDao.updateCartItemIsInBasket(cartItemId, updatedIsInBasket).right() }

    override suspend fun upsertCartItem(cartItem: CartItem): Either<Failure, Unit> =
        cartApi.addCartItem(cartItem.toRemote())
            .onRight { cartDao.upsertCartItem(cartItem.toLocal()) }

    override suspend fun deleteCartItemById(id: String): Either<Failure, Unit> =
        cartApi.deleteCartItemById(id)
            .onRight { cartDao.deleteCartItemById(id) }

    override suspend fun getCartItemById(id: String): Either<Failure, CartItem> {
        return when (val result = cartDao.getCartItemById(id)) {
            null -> ErrorMessage("No cartItem present with the id: $id").left()
            else -> result.toDomain()
        }
    }

    override suspend fun dropCurrentCart(): Either<Failure, Unit> =
        cartApi.deleteCart()
            .onRight { cartDao.deleteCart() }
}
