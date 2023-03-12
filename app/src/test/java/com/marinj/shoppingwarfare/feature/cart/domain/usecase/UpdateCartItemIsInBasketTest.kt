package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import arrow.core.right
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

private const val CART_ITEM_ID = "id"
private const val UPDATED_IS_IN_BASKET = true

class UpdateCartItemIsInBasketTest {

    private val cartRepository: CartRepository = mockk()

    private lateinit var sut: UpdateCartItemIsInBasket

    @Before
    fun setUp() {
        sut = UpdateCartItemIsInBasket(
            cartRepository,
        )
    }

    @Test
    fun `invoke should return result from cartRepository updateCartItemIsInBasket`() = runTest {
        val repositoryResult = Unit.right()
        coEvery {
            cartRepository.updateCartItemIsInBasket(CART_ITEM_ID, UPDATED_IS_IN_BASKET)
        } coAnswers { repositoryResult }

        val result = sut(CART_ITEM_ID, UPDATED_IS_IN_BASKET)

        assertThat(result).isEqualTo(repositoryResult)
    }
}
