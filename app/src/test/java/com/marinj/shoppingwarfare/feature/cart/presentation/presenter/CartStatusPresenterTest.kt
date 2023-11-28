package com.marinj.shoppingwarfare.feature.cart.presentation.presenter

import app.cash.turbine.test
import com.marinj.shoppingwarfare.core.mapper.FailureToStringMapper
import com.marinj.shoppingwarfare.feature.cart.FakeSuccessCheckoutCart
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.CheckoutCart
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.ValidateReceiptPath
import com.marinj.shoppingwarfare.feature.cart.presentation.mapper.UiCartItemToCartItemMapper
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartStatusEvent.ReceiptCaptureError
import com.marinj.shoppingwarfare.feature.cart.presentation.model.ReceiptStatus
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class CartStatusPresenterTest {

    @Test
    fun `SHOULD update state receiptStatus to Error WHEN ReceiptCaptureError is provided`() =
        runTest {
            val sut = buildSut()
            sut.onEvent(ReceiptCaptureError)

            sut.state.test {
                awaitItem().receiptStatus shouldBe ReceiptStatus.Error
            }
        }

    private fun buildSut(
        checkoutCart: CheckoutCart = FakeSuccessCheckoutCart,
    ) = CartStatusPresenter(
        coroutineScope = TestScope(),
        checkoutCart = checkoutCart,
        validateReceiptPath = ValidateReceiptPath(),
        failureToStringMapper = FailureToStringMapper(),
        uiCartItemToCartItemMapper = UiCartItemToCartItemMapper(),
    )
}