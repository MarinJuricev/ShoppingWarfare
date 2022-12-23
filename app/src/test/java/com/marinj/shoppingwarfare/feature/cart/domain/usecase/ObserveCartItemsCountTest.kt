package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

private const val CART_ITEMS_COUNT = 5

class ObserveCartItemsCountTest {

    private val cartRepository: CartRepository = mockk()

    private lateinit var sut: ObserveCartItemsCount

    @Before
    fun setUp() {
        sut = ObserveCartItemsCount(
            cartRepository,
        )
    }

    @Test
    fun `invoke SHOULD return result from cartRepository observeCartItemsCount`() =
        runTest {
            val repositoryResult = flow {
                emit(CART_ITEMS_COUNT)
            }

            coEvery {
                cartRepository.observeCartItemsCount()
            } coAnswers { repositoryResult }

            sut().test {
                assertThat(awaitItem()).isEqualTo(CART_ITEMS_COUNT)
                awaitComplete()
            }
        }
}
