package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import arrow.core.left
import arrow.core.right
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.feature.cart.FakeSuccessCartRepository
import com.marinj.shoppingwarfare.feature.cart.buildCartItem
import com.marinj.shoppingwarfare.feature.cart.domain.mapper.CartDataToHistoryItemMapper
import com.marinj.shoppingwarfare.feature.history.FakeSuccessHistoryRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test

private const val RECEIPT_PATH = "receiptPath"
private const val CART_NAME = "cartName"

class CheckoutCartTest {

    @Test
    fun `invoke SHOULD return result validateCartName when validateCartName returns Left`() =
        runTest {
            val cartItems = listOf(buildCartItem())
            val sut = buildSut()
            val expectedResult = ErrorMessage("Placeholder").left()

            val result = sut(cartItems, "", RECEIPT_PATH)

            assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun `invoke SHOULD return result from historyRepository updateHistoryItem when validateCartName returns Right`() =
        runTest {
            val cartItems = listOf(buildCartItem())
            val sut = buildSut()
            val expectedResult = Unit.right()

            val result = sut(cartItems, CART_NAME, RECEIPT_PATH)

            assertThat(result).isEqualTo(expectedResult)
        }

    private fun buildSut() = CheckoutCartImpl(
        FakeSuccessCartRepository(),
        FakeSuccessHistoryRepository(),
        CartDataToHistoryItemMapper(
            uuidGenerator = { UUID },
            timeStampGenerator = { TIMESTAMP },
        ),
    )
}

private const val UUID = "uuid"
private const val TIMESTAMP = 1L