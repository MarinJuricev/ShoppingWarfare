package com.marinj.shoppingwarfare.feature.history.data.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.history.data.model.LocalHistoryItem
import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryCartItem
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

private const val ID = "id"
private const val RECEIPT_PATH = "receiptPath"
private const val TIMESTAMP = 5L

class LocalToDomainHistoryItemMapperTest {

    private lateinit var sut: LocalToDomainHistoryItemMapper

    @Before
    fun setUp() {
        sut = LocalToDomainHistoryItemMapper()
    }

    @Test
    fun `map should map id`() {
        val origin = mockk<LocalHistoryItem>(relaxed = true).apply {
            every { historyItemId } returns ID
        }

        val result = sut.map(origin)

        assertThat(result.id).isEqualTo(ID)
    }

    @Test
    fun `map should map receiptPath`() {
        val origin = mockk<LocalHistoryItem>(relaxed = true).apply {
            every { receiptPath } returns RECEIPT_PATH
        }

        val result = sut.map(origin)

        assertThat(result.receiptPath).isEqualTo(RECEIPT_PATH)
    }

    @Test
    fun `map should map timestamp`() {
        val origin = mockk<LocalHistoryItem>(relaxed = true).apply {
            every { timestamp } returns TIMESTAMP
        }

        val result = sut.map(origin)

        assertThat(result.timestamp).isEqualTo(TIMESTAMP)
    }

    @Test
    fun `map should map historyCartItems`() {
        val cartItems = mockk<List<HistoryCartItem>>()
        val origin = mockk<LocalHistoryItem>(relaxed = true).apply {
            every { historyCartItems } returns cartItems
        }

        val result = sut.map(origin)

        assertThat(result.historyCartItems).isEqualTo(cartItems)
    }
}