package com.marinj.shoppingwarfare.feature.cart.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.cart.data.datasource.CartDao
import com.marinj.shoppingwarfare.feature.cart.data.mapper.DomainToLocalCartItemMapper
import com.marinj.shoppingwarfare.feature.cart.data.mapper.LocalToDomainCartItemMapper
import com.marinj.shoppingwarfare.feature.cart.data.model.LocalCartItem
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

private const val CART_ITEM_NAME = "cartItemName"

@ExperimentalCoroutinesApi
class CartRepositoryImplTest {

    private val cartDao: CartDao = mockk()
    private val localToDomainCartItemMapper: LocalToDomainCartItemMapper = mockk()
    private val domainToLocalCartItemMapper: DomainToLocalCartItemMapper = mockk()

    private lateinit var sut: CartRepository

    @Before
    fun setUp() {
        sut = CartRepositoryImpl(
            cartDao,
            localToDomainCartItemMapper,
            domainToLocalCartItemMapper,
        )
    }

    @Test
    fun `observeCartItems should return cartItems`() = runTest {
        val cartItem = mockk<CartItem>()
        val cartItemList = listOf(cartItem)
        val localCartItem = mockk<LocalCartItem>()
        val localCartItemList = listOf(localCartItem)
        coEvery {
            localToDomainCartItemMapper.map(localCartItem)
        } coAnswers { cartItem }
        coEvery {
            cartDao.observeCartItems()
        } coAnswers { flow { emit(localCartItemList) } }

        sut.observeCartItems().test {
            assertThat(awaitItem()).isEqualTo(cartItemList)
            awaitComplete()
        }
    }

    @Test
    fun `observeCartItemsCount should return number of cartItems`() = runTest {
        val numberOfCartItems = 5
        coEvery {
            cartDao.observeCartItemsCount()
        } coAnswers { flow { emit(numberOfCartItems) } }

        sut.observeCartItemsCount().test {
            assertThat(awaitItem()).isEqualTo(numberOfCartItems)
            awaitComplete()
        }
    }

    @Test
    fun `upsertCartItem should return LeftFailure when cartDao returns 0L`() = runTest {
        val cartItem = mockk<CartItem>()
        val localCartItem = mockk<LocalCartItem>().apply {
            every { name } returns CART_ITEM_NAME
        }
        val daoResult = 0L
        coEvery {
            domainToLocalCartItemMapper.map(cartItem)
        } coAnswers { localCartItem }
        coEvery {
            cartDao.upsertCartItem(localCartItem)
        } coAnswers { daoResult }

        val actualResult = sut.upsertCartItem(cartItem)
        val expectedResult = ErrorMessage("Error while adding $CART_ITEM_NAME").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `upsertCartItem should return RightUnit when cartDao returns everything but 0L`() =
        runTest {
            val cartItem = mockk<CartItem>()
            val localCartItem = mockk<LocalCartItem>()
            val daoResult = 1L
            coEvery {
                domainToLocalCartItemMapper.map(cartItem)
            } coAnswers { localCartItem }
            coEvery {
                cartDao.upsertCartItem(localCartItem)
            } coAnswers { daoResult }

            val actualResult = sut.upsertCartItem(cartItem)
            val expectedResult = Unit.buildRight()

            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Test
    fun `deleteCartItemById should return RightUnit`() =
        runTest {
            val cartItemId = "1"
            coEvery {
                cartDao.deleteCartItemById(cartItemId)
            } coAnswers { Unit }

            val actualResult = sut.deleteCartItemById(cartItemId)
            val expectedResult = Unit.buildRight()

            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Test
    fun `getCartItemById should return LeftFailure when cartDao returns null`() =
        runTest {
            val cartItemId = "1"
            coEvery {
                cartDao.getCartItemById(cartItemId)
            } coAnswers { null }

            val actualResult = sut.getCartItemById(cartItemId)
            val expectedResult =
                ErrorMessage("No cartItem present with the id: $cartItemId").buildLeft()

            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Test
    fun `getCartItemById should return RightCartItem when cartDao returns LocalCartItem`() =
        runTest {
            val cartItemId = "1"
            val localCartItem = mockk<LocalCartItem>()
            val cartItem = mockk<CartItem>()
            coEvery {
                cartDao.getCartItemById(cartItemId)
            } coAnswers { localCartItem }
            coEvery {
                localToDomainCartItemMapper.map(localCartItem)
            } coAnswers { cartItem }

            val actualResult = sut.getCartItemById(cartItemId)
            val expectedResult = cartItem.buildRight()

            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Suppress("UNUSED_EXPRESSION")
    @Test
    fun `dropCurrentCart should return RightUnit when cartDao returns LocalCartItem`() =
        runTest {
            val daoResult = Unit
            coEvery {
                cartDao.deleteCart()
            } coAnswers { daoResult }

            val actualResult = sut.dropCurrentCart()
            val expectedResult = daoResult.buildRight()

            assertThat(actualResult).isEqualTo(expectedResult)
        }
}
