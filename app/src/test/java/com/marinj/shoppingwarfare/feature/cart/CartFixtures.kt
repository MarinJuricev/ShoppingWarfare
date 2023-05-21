package com.marinj.shoppingwarfare.feature.cart

import com.marinj.shoppingwarfare.feature.cart.data.datasource.CartDao
import com.marinj.shoppingwarfare.feature.cart.data.model.LocalCartItem
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem.Companion.CartItem
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
)

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
    quantity = providedQuantity,
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

private const val ID = "ID"
private const val CATEGORY_NAME = "CATEGORY_NAME"
private const val NAME = "NAME"
private const val QUANTITY = 1u
private const val IS_IN_BASKET = false
