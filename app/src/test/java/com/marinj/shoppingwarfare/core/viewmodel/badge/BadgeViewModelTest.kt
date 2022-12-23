package com.marinj.shoppingwarfare.core.viewmodel.badge

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.core.viewmodel.badge.BadgeEvent.StartObservingBadgesCount
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.ObserveCartItemsCount
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val NUMBER_OF_CART_ITEMS = 5

class BadgeViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val observeCartItemsCount: ObserveCartItemsCount = mockk()

    private lateinit var sut: BadgeViewModel

    @Before
    fun setUp() {
        sut = BadgeViewModel(
            observeCartItemsCount,
        )
    }

    @Test
    fun `onEvent SHOULD update cartBadgeCount when StartObservingBadgesCount is provided`() =
        runTest {
            val cartItemsCountFlow = flow {
                emit(NUMBER_OF_CART_ITEMS)
            }
            coEvery {
                observeCartItemsCount()
            } coAnswers { cartItemsCountFlow }

            sut.onEvent(StartObservingBadgesCount)
            val expectedResult = BadgeViewState(cartBadgeCount = NUMBER_OF_CART_ITEMS)

            sut.viewState.test {
                assertThat(awaitItem()).isEqualTo(expectedResult)
            }
        }
}
