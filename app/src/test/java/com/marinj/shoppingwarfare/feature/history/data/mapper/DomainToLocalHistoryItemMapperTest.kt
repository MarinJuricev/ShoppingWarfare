package com.marinj.shoppingwarfare.feature.history.data.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryCartItem
import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryItem
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

private const val ID = "id"
private const val RECEIPT_PATH = "receiptPath"
private const val TIME_STAMP = 0L

class DomainToLocalHistoryItemMapperTest {

    private lateinit var sut: DomainToLocalHistoryItemMapper

    @Before
    fun setUp() {
        sut = DomainToLocalHistoryItemMapper()
    }

    @Test
    fun `map should map id`() {
        val origin = mockk<HistoryItem>(relaxed = true).apply {
            every { id } returns ID
        }

        val result = sut.map(origin)

        assertThat(result.historyItemId).isEqualTo(ID)
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
    fun `map should map timestamp`() {
        val origin = mockk<HistoryItem>(relaxed = true).apply {
            every { timestamp } returns TIME_STAMP
        }

        val result = sut.map(origin)

        assertThat(result.timestamp).isEqualTo(TIME_STAMP)
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
