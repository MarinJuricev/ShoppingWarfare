package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import arrow.core.right
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.cart.FakeSuccessCartRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UpdateCartItemIsInBasketTest {

    @Test
    fun `invoke should return result from cartRepository updateCartItemIsInBasket`() = runTest {
        val sut = UpdateCartItemIsInBasketImpl(FakeSuccessCartRepository())
        val expectedResult = Unit.right()

        val result = sut(CART_ITEM_ID, UPDATED_IS_IN_BASKET)

        assertThat(result).isEqualTo(expectedResult)
    }
}

private const val CART_ITEM_ID = "id"
private const val UPDATED_IS_IN_BASKET = true
