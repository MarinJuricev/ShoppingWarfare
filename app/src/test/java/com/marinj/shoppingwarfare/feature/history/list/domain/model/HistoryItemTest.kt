package com.marinj.shoppingwarfare.feature.history.list.domain.model

import arrow.core.left
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryItem.Companion.HistoryItem
import org.junit.Test

class HistoryItemTest {

    @Test
    fun `HistoryItem SHOULD return Left WHEN id is empty`() {
        val expectedResult = ErrorMessage("id can not be null or empty").left()

        val result = HistoryItem(
            id = "",
            receiptPath = RECEIPT_PATH,
            timestamp = TIMESTAMP,
            cartName = CART_NAME,
            historyCartItems = emptyList(),
        )

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `HistoryItem SHOULD return Left WHEN receiptPath is empty`() {
        val expectedResult = ErrorMessage("receiptPath can not be null or empty").left()

        val result = HistoryItem(
            id = ID,
            receiptPath = null,
            timestamp = TIMESTAMP,
            cartName = CART_NAME,
            historyCartItems = emptyList(),
        )

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `HistoryItem SHOULD return Left WHEN cartName is empty`() {
        val expectedResult = ErrorMessage("cartName can not be null or empty").left()

        val result = HistoryItem(
            id = ID,
            receiptPath = RECEIPT_PATH,
            timestamp = TIMESTAMP,
            cartName = "",
            historyCartItems = emptyList(),
        )

        assertThat(result).isEqualTo(expectedResult)
    }
}

private const val ID = "id"
private const val RECEIPT_PATH = "receiptPath"
private const val TIMESTAMP = 0L
private const val CART_NAME = "cartName"
