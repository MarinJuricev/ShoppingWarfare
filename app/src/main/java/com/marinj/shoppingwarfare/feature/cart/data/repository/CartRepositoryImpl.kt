package com.marinj.shoppingwarfare.feature.cart.data.repository

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.cart.data.datasource.CartDao
import com.marinj.shoppingwarfare.feature.cart.data.model.LocalCartItem
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
    private val localToDomainCartItemMapper: Mapper<CartItem, LocalCartItem>,
    private val domainToLocalCartItemMapper: Mapper<LocalCartItem, CartItem>,
) : CartRepository {

    override fun observeCartItems(): Flow<List<CartItem>> =
        cartDao.observeCartItems().map { localCartItem ->
            localCartItem.map { localToDomainCartItemMapper.map(it) }
        }

    override suspend fun upsertCartItem(cartItem: CartItem): Either<Failure, Unit> {
        val localCartItem = domainToLocalCartItemMapper.map(cartItem)
        return when (cartDao.upsertCartItem(localCartItem)) {
            0L -> Failure.ErrorMessage("Error while adding ${localCartItem.name}").buildLeft()
            else -> Unit.buildRight()
        }
    }

    override suspend fun deleteCartItemById(id: String): Either<Failure, Unit> =
        cartDao.deleteCartItemById(id).buildRight()
}
