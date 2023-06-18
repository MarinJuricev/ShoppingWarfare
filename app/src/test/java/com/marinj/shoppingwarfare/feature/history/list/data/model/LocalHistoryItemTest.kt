package com.marinj.shoppingwarfare.feature.history.list.data.model

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.history.buildHistoryCartItem
import com.marinj.shoppingwarfare.feature.history.buildLocalHistoryItem
import org.junit.Test

class LocalHistoryItemTest {

    @Test
    fun `toDomain should map id`() {
        val sut = buildLocalHistoryItem(providedItemId = ID)

        val result = sut.toDomain()

        assertThat(result.getOrNull()?.id).isEqualTo(ID)
    }

    @Test
    fun `toDomain should map receiptPath`() {
        val sut = buildLocalHistoryItem(providedReceiptPath = RECEIPT_PATH)

        val result = sut.toDomain()

        assertThat(result.getOrNull()?.receiptPath).isEqualTo(RECEIPT_PATH)
    }

    @Test
    fun `toDomain should map cartName`() {
        val sut = buildLocalHistoryItem(providedCartName = CART_NAME)

        val result = sut.toDomain()

        assertThat(result.getOrNull()?.cartName).isEqualTo(CART_NAME)
    }

    @Test
    fun `toDomain should map timestamp`() {
        val sut = buildLocalHistoryItem(providedTimeStamp = TIMESTAMP)

        val result = sut.toDomain()

        assertThat(result.getOrNull()?.timestamp).isEqualTo(TIMESTAMP)
    }

    @Test
    fun `toDomain should map historyCartItems`() {
        val historyCartItems = listOf(buildHistoryCartItem())
        val sut = buildLocalHistoryItem(providedHistoryCartItems = historyCartItems)

        val result = sut.toDomain()

        assertThat(result.getOrNull()?.historyCartItems).isEqualTo(historyCartItems)
    }
}

private const val RECEIPT_PATH = "receiptPath"
private const val CART_NAME = "cartName"
private const val TIMESTAMP = 5L
private const val ID = "id"