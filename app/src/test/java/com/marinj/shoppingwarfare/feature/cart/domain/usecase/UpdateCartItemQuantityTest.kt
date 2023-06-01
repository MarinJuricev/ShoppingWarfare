package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import arrow.core.right
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.cart.FakeSuccessCartRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test


class UpdateCartItemQuantityTest {

    @Test
    fun `invoke SHOULD trigger updateCartItemQuantity and return result from cartRepository`() =
        runTest {
            val sut = UpdateCartItemQuantityImpl(FakeSuccessCartRepository())
            val expectedResult = Unit.right()

            val actualResult = sut(ID, UPDATED_QUANTITY)

            assertThat(actualResult).isEqualTo(expectedResult)
        }
}

private const val ID = "id"
private const val UPDATED_QUANTITY = 5
