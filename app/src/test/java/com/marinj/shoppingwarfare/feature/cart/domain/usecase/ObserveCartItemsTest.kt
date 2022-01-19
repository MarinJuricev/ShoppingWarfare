package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ObserveCartItemsTest {

    private val cartRepository: CartRepository = mockk()

    private lateinit var sut: ObserveCartItems

    @Before
    fun setUp() {
        sut = ObserveCartItems(
            cartRepository,
        )
    }

    @Test
    fun `invoke should return result from cartRepository observeCartItems`() = runTest {
        val cartItems = listOf(mockk<CartItem>())
        val repositoryFlow = flow { emit(cartItems) }
        coEvery {
            cartRepository.observeCartItems()
        } coAnswers { repositoryFlow }

        sut().test {
            assertThat(awaitItem()).isEqualTo(cartItems)
            awaitComplete()
        }
    }
}
