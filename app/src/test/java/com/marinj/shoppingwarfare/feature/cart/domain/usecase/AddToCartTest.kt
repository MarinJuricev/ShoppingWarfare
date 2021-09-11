package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AddToCartTest {

    private val cartRepository: CartRepository = mockk()

    private lateinit var sut: AddToCart

    @Before
    fun setUp() {
        sut = AddToCart(
            cartRepository,
        )
    }

    @Test
    fun `invoke should return result from repository`() = runBlockingTest {
        val cartItem = mockk<CartItem>()
        val repositoryResult = Unit.buildRight()
        coEvery {
            cartRepository.upsertCartItem(cartItem)
        } coAnswers { repositoryResult }

        val actualResult = sut(cartItem)

        assertThat(actualResult).isEqualTo(repositoryResult)
    }
}
