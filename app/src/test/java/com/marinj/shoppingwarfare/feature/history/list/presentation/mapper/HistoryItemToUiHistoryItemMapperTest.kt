package com.marinj.shoppingwarfare.feature.history.list.presentation.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryCartItem
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryItem
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

private const val ID = "id"
private const val RECEIPT_PATH = "receiptPath"
private const val TIMESTAMP = 1239123981290L
private const val CONVERTED_TIMESTAMP = "2009-04-07"
private const val CART_NAME = "cartName"

class HistoryItemToUiHistoryItemMapperTest {

    private lateinit var sut: HistoryItemToUiHistoryItemMapper

    @Before
    fun setUp() {
        sut = HistoryItemToUiHistoryItemMapper()
    }

    @Test
    fun `map should map id`() {
        val origin = mockk<HistoryItem>(relaxed = true).apply {
            every { id } returns ID
        }

        val result = sut.map(origin)

        assertThat(result.id).isEqualTo(ID)
    }

    @Test
    fun `map should map receiptPath`() {
        val origin = mockk<HistoryItem>(relaxed = true).apply {
            every { receiptPath } returns RECEIPT_PATH
        }

        val result = sut.map(origin)

        assertThat(result.receiptPath).isEqualTo(RECEIPT_PATH)
    }

    @Test
    fun `map should map date`() {
        val origin = mockk<HistoryItem>(relaxed = true).apply {
            every { timestamp } returns TIMESTAMP
        }

        val result = sut.map(origin)

        assertThat(result.date).isEqualTo(CONVERTED_TIMESTAMP)
    }

    @Test
    fun `map should map cartName`() {
        val origin = mockk<HistoryItem>(relaxed = true).apply {
            every { cartName } returns CART_NAME
        }

        val result = sut.map(origin)

        assertThat(result.cartName).isEqualTo(CART_NAME)
    }

    @Test
    fun `map should map historyCartItems`() {
        val cartItems = mockk<List<HistoryCartItem>>()
        val origin = mockk<HistoryItem>(relaxed = true).apply {
            every { historyCartItems } returns cartItems
        }

        val result = sut.map(origin)

        assertThat(result.historyCartItems).isEqualTo(cartItems)
    }
}
