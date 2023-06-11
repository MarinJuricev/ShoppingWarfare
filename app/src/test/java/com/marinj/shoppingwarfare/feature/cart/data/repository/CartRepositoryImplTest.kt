package com.marinj.shoppingwarfare.feature.cart.data.repository

import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.feature.cart.FakeFailureCartDao
import com.marinj.shoppingwarfare.feature.cart.FakeSuccessCartDao
import com.marinj.shoppingwarfare.feature.cart.buildCartItem
import com.marinj.shoppingwarfare.feature.cart.buildLocalCartItem
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CartRepositoryImplTest {

    @Test
    fun `observeCartItems SHOULD return cartItems`() = runTest {
        val cartItem = buildCartItem()
        val cartItemList = listOf(cartItem)
        val localCartItems = listOf(buildLocalCartItem())
        val sut = CartRepositoryImpl(cartDao = FakeSuccessCartDao(cartListToReturn = localCartItems))

        sut.observeCartItems().test {
            assertThat(awaitItem()).isEqualTo(cartItemList)
            awaitComplete()
        }
    }

    @Test
    fun `observeCartItemsCount SHOULD return number of cartItems`() = runTest {
        val localCartItems = listOf(buildLocalCartItem(), buildLocalCartItem())
        val sut = CartRepositoryImpl(cartDao = FakeSuccessCartDao(cartListToReturn = localCartItems))

        sut.observeCartItemsCount().test {
            assertThat(awaitItem()).isEqualTo(localCartItems.size)
            awaitComplete()
        }
    }

    @Test
    fun `upsertCartItem SHOULD return LeftFailure when cartDao returns 0L`() = runTest {
        val cartItem = buildCartItem(providedName = CART_ITEM_NAME)
        val sut = CartRepositoryImpl(cartDao = FakeFailureCartDao)

        val actualResult = sut.upsertCartItem(cartItem)
        val expectedResult = ErrorMessage("Error while adding $CART_ITEM_NAME").left()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `upsertCartItem SHOULD return RightUnit when cartDao returns everything but 0L`() =
        runTest {
            val cartItem = buildCartItem(providedCategoryName = CART_ITEM_NAME)
            val sut = CartRepositoryImpl(cartDao = FakeSuccessCartDao())
            val expectedResult = Unit.right()

            val actualResult = sut.upsertCartItem(cartItem)

            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Test
    fun `deleteCartItemById SHOULD return RightUnit`() =
        runTest {
            val sut = CartRepositoryImpl(cartDao = FakeSuccessCartDao())

            val actualResult = sut.deleteCartItemById(CART_ID)
            val expectedResult = Unit.right()

            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Test
    fun `getCartItemById SHOULD return LeftFailure when cartDao returns null`() =
        runTest {
            val sut = CartRepositoryImpl(cartDao = FakeFailureCartDao)
            val actualResult = sut.getCartItemById(CART_ID)
            val expectedResult =
                ErrorMessage("No cartItem present with the id: $CART_ID").left()

            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Test
    fun `getCartItemById should return RightCartItem when cartDao returns LocalCartItem`() =
        runTest {
            val localCartItem = buildLocalCartItem(providedId = CART_ID)
            val cartItem = buildCartItem(providedId = CART_ID)
            val sut = CartRepositoryImpl(cartDao = FakeSuccessCartDao(cartListToReturn = listOf(localCartItem)))
            val expectedResult = cartItem.right()

            val actualResult = sut.getCartItemById(CART_ID)

            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Test
    fun `dropCurrentCart should return RightUnit when cartDao returns LocalCartItem`() =
        runTest {
            val expectedResult = Unit.right()
            val sut = CartRepositoryImpl(cartDao = FakeSuccessCartDao())

            val actualResult = sut.dropCurrentCart()

            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Test
    fun `updateCartItemQuantity should return result from categoryDao updateCartItemQuantity`() =
        runTest {
            val expectedResult = Unit.right()
            val sut = CartRepositoryImpl(cartDao = FakeSuccessCartDao())

            val result = sut.updateCartItemQuantity(CART_ID, NEW_QUANTITY)

            assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun `updateCartItemIsInBasket should return result from categoryDao updateCartItemIsInBasket`() =
        runTest {
            val expectedResult = Unit.right()
            val sut = CartRepositoryImpl(cartDao = FakeSuccessCartDao())
            val result = sut.updateCartItemIsInBasket(CART_ID, NEW_UPDATED_IN_BASKET)

            assertThat(result).isEqualTo(expectedResult)
        }
}

private const val CART_ITEM_NAME = "cartItemName"
private const val CART_ID = "cartId"
private const val NEW_QUANTITY = 5
private const val NEW_UPDATED_IN_BASKET = true