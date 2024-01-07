package com.marinj.shoppingwarfare.feature.history.list.presentation.mapper

import com.marinj.shoppingwarfare.feature.history.buildHistoryCartItem
import com.marinj.shoppingwarfare.feature.history.buildHistoryItem
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

private const val ID = "id"
private const val RECEIPT_PATH = "receiptPath"
private const val TIMESTAMP = 1_239_123_981_290L
private const val CONVERTED_TIMESTAMP = "2009-04-07"
private const val CART_NAME = "cartName"

class HistoryItemToUiHistoryItemMapperTest {

    private val sut = HistoryItemToUiHistoryItemMapper()

    @Test
    fun `map SHOULD map id`() {
        val origin = buildHistoryItem(providedId = ID)

        val result = sut.map(origin)

        result.id shouldBe ID
    }

    @Test
    fun `map SHOULD map receiptPath`() {
        val origin = buildHistoryItem(providedReceiptPath = RECEIPT_PATH)

        val result = sut.map(origin)

        result.receiptPath shouldBe RECEIPT_PATH
    }

    @Test
    fun `map should map date`() {
        val origin = buildHistoryItem(providedTimeStamp = TIMESTAMP)

        val result = sut.map(origin)

        result.date shouldBe CONVERTED_TIMESTAMP
    }

    @Test
    fun `map should map cartName`() {
        val origin = buildHistoryItem(providedCartName = CART_NAME)

        val result = sut.map(origin)

        result.cartName shouldBe CART_NAME
    }

    @Test
    fun `map should map historyCartItems`() {
        val cartItems = listOf(buildHistoryCartItem())
        val origin = buildHistoryItem(providedHistoryCartItems = cartItems)

        val result = sut.map(origin)

        result.historyCartItems shouldBe cartItems
    }
}
