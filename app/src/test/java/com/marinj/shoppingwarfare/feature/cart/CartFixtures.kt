package com.marinj.shoppingwarfare.feature.cart

import arrow.core.Either
import arrow.core.right
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.cart.data.datasource.CartDao
import com.marinj.shoppingwarfare.feature.cart.data.model.LocalCartItem
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem.Companion.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

fun buildCartItem(
    providedId: String = ID,
    providedCategoryName: String = CATEGORY_NAME,
    providedName: String = NAME,
    providedQuantity: UInt = QUANTITY,
    providedIsInBasket: Boolean = IS_IN_BASKET,
) = CartItem(
    id = providedId,
    categoryName = providedCategoryName,
    name = providedName,
    quantity = providedQuantity,
    isInBasket = providedIsInBasket,
).getOrNull()!!

fun buildLocalCartItem(
    providedId: String = ID,
    providedCategoryName: String = CATEGORY_NAME,
    providedName: String = NAME,
    providedQuantity: UInt = QUANTITY,
    providedIsInBasket: Boolean = IS_IN_BASKET,
) = LocalCartItem(
    cartItemId = providedId,
    categoryName = providedCategoryName,
    name = providedName,
    quantity = providedQuantity.toInt(),
    isInBasket = providedIsInBasket,
)

class FakeSuccessCartDao(
    private val cartListToReturn: List<LocalCartItem> = listOf(buildLocalCartItem()),
) : CartDao {

    override fun observeCartItems(): Flow<List<LocalCartItem>> = flowOf(cartListToReturn)

    override fun observeCartItemsCount(): Flow<Int?> = flowOf(cartListToReturn.size)

    override suspend fun updateCartItemQuantity(id: String, newQuantity: Int) = Unit

    override suspend fun updateCartItemIsInBasket(id: String, updatedIsInBasket: Boolean) = Unit

    override suspend fun upsertCartItem(entity: LocalCartItem): Long = 1L

    override suspend fun deleteCartItemById(id: String) = Unit

    override suspend fun getCartItemById(id: String): LocalCartItem? = buildLocalCartItem()

    override suspend fun deleteCart() = Unit
}

object FakeFailureCartDao : CartDao {

    override fun observeCartItems(): Flow<List<LocalCartItem>> = flowOf(emptyList())

    override fun observeCartItemsCount(): Flow<Int?> = flowOf(0)

    override suspend fun updateCartItemQuantity(id: String, newQuantity: Int) = Unit

    override suspend fun updateCartItemIsInBasket(id: String, updatedIsInBasket: Boolean) = Unit

    override suspend fun upsertCartItem(entity: LocalCartItem): Long = 0L

    override suspend fun deleteCartItemById(id: String) = Unit

    override suspend fun getCartItemById(id: String): LocalCartItem? = null

    override suspend fun deleteCart() = Unit
}

class FakeSuccessCartRepository(
    private val cartListToReturn: List<CartItem> = listOf(buildCartItem()),
) : CartRepository {
    override fun observeCartItems(): Flow<List<CartItem>> = flowOf(cartListToReturn)

    override fun observeCartItemsCount(): Flow<Int?> = flowOf(cartListToReturn.size)

    override suspend fun updateCartItemQuantity(cartItemId: String, newQuantity: Int): Either<Failure, Unit> =
        Unit.right()

    override suspend fun updateCartItemIsInBasket(cartItemId: String, updatedIsInBasket: Boolean): Either<Failure, Unit> =
        Unit.right()

    override suspend fun upsertCartItem(cartItem: CartItem): Either<Failure, Unit> = Unit.right()

    override suspend fun deleteCartItemById(id: String): Either<Failure, Unit> = Unit.right()

    override suspend fun getCartItemById(id: String): Either<Failure, CartItem> = buildCartItem().right()

    override suspend fun dropCurrentCart(): Either<Failure, Unit> = Unit.right()

}

private const val ID = "ID"
private const val CATEGORY_NAME = "CATEGORY_NAME"
private const val NAME = "NAME"
private const val QUANTITY = 1u
private const val IS_IN_BASKET = false
