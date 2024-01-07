package com.marinj.shoppingwarfare.core.viewmodel.badge

import app.cash.turbine.test
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.core.viewmodel.badge.BadgeEvent.StartObservingBadgesCount
import com.marinj.shoppingwarfare.feature.cart.FakeSuccessObserveCartItemsCount
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineRule::class)
class BadgeViewModelTest {

    @Test
    fun `onEvent SHOULD update cartBadgeCount when StartObservingBadgesCount is provided`() =
        runTest {
            val sut = BadgeViewModel(
                observeCartItemsCount = FakeSuccessObserveCartItemsCount(NUMBER_OF_CART_ITEMS),
            )
            val expectedResult = BadgeViewState(cartBadgeCount = NUMBER_OF_CART_ITEMS)

            sut.viewState.test {
                awaitItem() shouldBe BadgeViewState()
                sut.onEvent(StartObservingBadgesCount)
                awaitItem() shouldBe expectedResult
            }
        }
}

private const val NUMBER_OF_CART_ITEMS = 5
