package com.marinj.shoppingwarfare.feature.history.list.data.model

import com.marinj.shoppingwarfare.feature.history.buildHistoryCartItem
import com.marinj.shoppingwarfare.feature.history.buildHistoryItem
import com.marinj.shoppingwarfare.feature.history.buildLocalHistoryItem
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class LocalHistoryItemTest {

    @Test
    fun `toDomain should map id`() {
        val sut = buildLocalHistoryItem(providedItemId = ID)

        val result = sut.toDomain()

        result.getOrNull()?.id?.value shouldBe ID
    }

    @Test
    fun `toDomain should map receiptPath`() {
        val sut = buildLocalHistoryItem(providedReceiptPath = RECEIPT_PATH)

        val result = sut.toDomain()

        result.getOrNull()?.receiptPath?.value shouldBe RECEIPT_PATH
    }

    @Test
    fun `toDomain should map cartName`() {
        val sut = buildLocalHistoryItem(providedCartName = CART_NAME)

        val result = sut.toDomain()

        result.getOrNull()?.cartName?.value shouldBe CART_NAME
    }

    @Test
    fun `toDomain should map timestamp`() {
        val sut = buildLocalHistoryItem(providedTimeStamp = TIMESTAMP)

        val result = sut.toDomain()

        result.getOrNull()?.timestamp shouldBe TIMESTAMP
    }

    @Test
    fun `toDomain should map historyCartItems`() {
        val historyCartItems = listOf(buildHistoryCartItem())
        val sut = buildLocalHistoryItem(providedHistoryCartItems = historyCartItems)

        val result = sut.toDomain()

        result.getOrNull()?.historyCartItems shouldBe historyCartItems
    }

    @Test
    fun `toLocal SHOULD map id`() {
        val sut = buildHistoryItem(providedId = ID)

        val result = sut.toLocal()

        result.historyItemId shouldBe ID
    }

    @Test
    fun `toLocal SHOULD map receiptPath`() {
        val sut = buildHistoryItem(providedReceiptPath = RECEIPT_PATH)

        val result = sut.toLocal()

        result.receiptPath shouldBe RECEIPT_PATH
    }

    @Test
    fun `map should map timestamp`() {
        val sut = buildHistoryItem(providedTimeStamp = TIMESTAMP)

        val result = sut.toLocal()

        result.timestamp shouldBe TIMESTAMP
    }

    @Test
    fun `map SHOULD map cartName`() {
        val sut = buildHistoryItem(providedCartName = CART_NAME)

        val result = sut.toLocal()

        result.cartName shouldBe CART_NAME
    }

    @Test
    fun `map should map historyCartItems`() {
        val historyCartItems = listOf(buildHistoryCartItem())
        val sut = buildHistoryItem(providedHistoryCartItems = historyCartItems)

        val result = sut.toLocal()

        result.historyCartItems shouldBe historyCartItems
    }
}

private const val RECEIPT_PATH = "receiptPath"
private const val CART_NAME = "cartName"
private const val TIMESTAMP = 5L
private const val ID = "id"
