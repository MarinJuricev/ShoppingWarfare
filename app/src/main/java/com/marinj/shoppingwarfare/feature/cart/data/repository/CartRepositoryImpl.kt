package com.marinj.shoppingwarfare.feature.cart.data.repository

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.cart.data.datasource.CartDao
import com.marinj.shoppingwarfare.feature.cart.data.mapper.DomainToLocalCartItemMapper
import com.marinj.shoppingwarfare.feature.cart.data.mapper.LocalToDomainCartItemMapper
import com.marinj.shoppingwarfare.feature.cart.data.remote.CartApi
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartApi: CartApi,
    private val cartDao: CartDao,
    private val localToDomainCartItemMapper: LocalToDomainCartItemMapper,
    private val domainToLocalCartItemMapper: DomainToLocalCartItemMapper,
) : CartRepository {

    override fun observeCartItems(): Flow<List<CartItem>> =
        cartDao.observeCartItems().map { localCartItems ->
            localCartItems.map { localCartItem ->
                localToDomainCartItemMapper.map(localCartItem)
            }
        }

    override fun observeCartItemsCount(): Flow<Int?> =
        cartDao.observeCartItemsCount()

    override suspend fun updateCartItemQuantity(
        cartItemId: String,
        newQuantity: Int
    ): Either<Failure, Unit> =
        cartDao.updateCartItemQuantity(cartItemId, newQuantity).buildRight()

    override suspend fun updateCartItemIsInBasket(
        cartItemId: String,
        updatedIsInBasket: Boolean,
    ): Either<Failure, Unit> =
        cartDao.updateCartItemIsInBasket(cartItemId, updatedIsInBasket).buildRight()

    override suspend fun upsertCartItem(cartItem: CartItem): Either<Failure, Unit> {
        val localCartItem = domainToLocalCartItemMapper.map(cartItem)
        return when (cartDao.upsertCartItem(localCartItem)) {
            0L -> ErrorMessage("Error while adding ${localCartItem.name}").buildLeft()
            else -> Unit.buildRight()
        }
    }

    override suspend fun deleteCartItemById(id: String): Either<Failure, Unit> =
        cartDao.deleteCartItemById(id).buildRight()

    override suspend fun getCartItemById(id: String): Either<Failure, CartItem> {
        return when (val result = cartDao.getCartItemById(id)) {
            null -> ErrorMessage("No cartItem present with the id: $id").buildLeft()
            else -> localToDomainCartItemMapper.map(result).buildRight()
        }
    }

    override suspend fun dropCurrentCart(): Either<Failure, Unit> =
        cartDao.deleteCart().buildRight()
}
