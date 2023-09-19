package com.marinj.shoppingwarfare.feature.cart

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.Unknown
import com.marinj.shoppingwarfare.feature.cart.data.datasource.CartDao
import com.marinj.shoppingwarfare.feature.cart.data.model.LocalCartItem
import com.marinj.shoppingwarfare.feature.cart.data.model.RemoteCartItem
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem.Companion.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.CheckoutCart
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.DeleteCartItem
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.ObserveCartItems
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.ObserveCartItemsCount
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.UpdateCartItemIsInBasket
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.UpdateCartItemQuantity
import com.marinj.shoppingwarfare.feature.cart.presentation.model.UiCartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

fun buildRemoteCartItem(
    providedId: String = ID,
    providedCategoryName: String = CATEGORY_NAME,
    providedName: String = NAME,
    providedQuantity: UInt = QUANTITY,
    providedIsInBasket: Boolean = IS_IN_BASKET,
) = RemoteCartItem(
    cartItemId = providedId,
    categoryName = providedCategoryName,
    name = providedName,
    quantity = providedQuantity.toInt(),
    inBasket = providedIsInBasket,
)

fun buildUiCartItemHeader(
    providedId: String = CATEGORY_NAME,
    providedCategoryName: String = CATEGORY_NAME,
) = UiCartItem.Header(
    id = providedId,
    categoryName = providedCategoryName,
)

fun buildUiCartItemContent(
    providedId: String = ID,
    providedCategoryName: String = CATEGORY_NAME,
    providedName: String = NAME,
    providedQuantity: Int = QUANTITY.toInt(),
    providedIsInBasket: Boolean = IS_IN_BASKET,
) = UiCartItem.Content(
    id = providedId,
    name = providedName,
    categoryName = providedCategoryName,
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

    override suspend fun getCartItemById(id: String): LocalCartItem? = cartListToReturn.first()

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

class FakeSuccessObserveCartItemsCount(
    private val numberOfItems: Int = NUMBER_OF_CART_ITEMS,
) : ObserveCartItemsCount {
    override fun invoke(): Flow<Int?> = flowOf(numberOfItems)
}

class FakeSuccessObserveCartItems(
    private val cartListToReturn: List<CartItem> = listOf(buildCartItem()),
) : ObserveCartItems {
    override fun invoke(): Flow<List<CartItem>> = flowOf(cartListToReturn)
}

object FakeFailureObserveCartItems : ObserveCartItems {
    override fun invoke(): Flow<List<CartItem>> = flow { throw Throwable() }
}

object FakeSuccessDeleteCartItem : DeleteCartItem {
    override suspend fun invoke(cartItemId: String) = Unit.right()
}

object FakeFailureDeleteCartItem : DeleteCartItem {
    override suspend fun invoke(cartItemId: String) = Unknown.left()
}

object FakeSuccessUpdateCartItemQuantity : UpdateCartItemQuantity {
    override suspend fun invoke(cartItemId: String, newQuantity: Int) = Unit.right()
}

object FakeFailureUpdateCartItemQuantity : UpdateCartItemQuantity {
    override suspend fun invoke(cartItemId: String, newQuantity: Int) = Unknown.left()
}

object FakeSuccessUpdateCartItemIsInBasket : UpdateCartItemIsInBasket {
    override suspend fun invoke(cartItemId: String, updatedIsInBasket: Boolean) = Unit.right()
}

object FakeFailureUpdateCartItemIsInBasket : UpdateCartItemIsInBasket {
    override suspend fun invoke(cartItemId: String, updatedIsInBasket: Boolean) = Unknown.left()
}

object FakeSuccessCheckoutCart : CheckoutCart {
    override suspend fun invoke(
        cartItems: List<CartItem>,
        cartName: String,
        receiptPath: String?,
    ) = Unit.right()
}

object FakeFailureCheckoutCart : CheckoutCart {
    override suspend fun invoke(
        cartItems: List<CartItem>,
        cartName: String,
        receiptPath: String?,
    ) = Unknown.left()
}

private const val ID = "ID"
private const val CATEGORY_NAME = "CATEGORY_NAME"
private const val NAME = "NAME"
private const val QUANTITY = 1u
private const val IS_IN_BASKET = false
private const val NUMBER_OF_CART_ITEMS = 1
