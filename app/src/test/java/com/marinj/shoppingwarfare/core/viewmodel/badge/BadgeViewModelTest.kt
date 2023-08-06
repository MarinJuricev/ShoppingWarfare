package com.marinj.shoppingwarfare.core.viewmodel.badge

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.core.viewmodel.badge.BadgeEvent.StartObservingBadgesCount
import com.marinj.shoppingwarfare.feature.cart.FakeSuccessObserveCartItemsCount
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class BadgeViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Test
    fun `onEvent SHOULD update cartBadgeCount when StartObservingBadgesCount is provided`() =
        runTest {
            val sut = BadgeViewModel(
                observeCartItemsCount = FakeSuccessObserveCartItemsCount(NUMBER_OF_CART_ITEMS),
            )
            val expectedResult = BadgeViewState(cartBadgeCount = NUMBER_OF_CART_ITEMS)

            sut.onEvent(StartObservingBadgesCount)

            sut.viewState.test {
                assertThat(awaitItem()).isEqualTo(expectedResult)
            }
        }
}

private const val NUMBER_OF_CART_ITEMS = 5
