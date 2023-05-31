package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.cart.FakeSuccessCartRepository
import com.marinj.shoppingwarfare.feature.cart.buildCartItem
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ObserveCartItemsTest {

    @Test
    fun `invoke SHOULD return result from cartRepository observeCartItems`() = runTest {
        val cartItems = listOf(buildCartItem())
        val sut = ObserveCartItemsImpl(FakeSuccessCartRepository(cartItems))

        sut().test {
            assertThat(awaitItem()).isEqualTo(cartItems)
            awaitComplete()
        }
    }
}
