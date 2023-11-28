package com.marinj.shoppingwarfare.feature.cart.presentation.presenter

import app.cash.turbine.test
import com.marinj.shoppingwarfare.feature.cart.presentation.mapper.UiCartTabItemsMapper
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartTabEvent.CartTabPositionUpdated
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class CartTabPresenterTest {
    @Test
    fun `SHOULD update viewState when CartTabPositionUpdated is provided`() = runTest {
        val newCartTabPosition = 1
        val sut = buildSut()

        sut.onEvent(CartTabPositionUpdated(newCartTabPosition))

        sut.state.test {
            awaitItem().selectedIndex shouldBe newCartTabPosition
        }
    }

    private fun buildSut(): CartTabPresenter = CartTabPresenter(
        coroutineScope = TestScope(),
        cartTabItemsMapper = UiCartTabItemsMapper(),
    )

}