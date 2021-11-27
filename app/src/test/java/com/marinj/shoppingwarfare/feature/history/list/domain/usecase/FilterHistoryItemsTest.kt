package com.marinj.shoppingwarfare.feature.history.list.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.history.list.presentation.model.UiHistoryItem
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

private const val SEARCH_QUERY = "first"
private const val FIRST_CART_NAME = "firstCartName"
private const val SECOND_CART_NAME = "secondCartName"

class FilterHistoryItemsTest {

    private lateinit var sut: FilterHistoryItems

    @Before
    fun setUp() {
        sut = FilterHistoryItems()
    }

    @Test
    fun `invoke should return the provided list when searchQuery is empty`() {
        val uiHistoryItem = mockk<UiHistoryItem>()
        val uiHistoryItems = listOf(uiHistoryItem)

        val result = sut(
            listToFilter = uiHistoryItems,
            searchQuery = "",
        )

        assertThat(result).isEqualTo(uiHistoryItems)
    }

    @Test
    fun `invoke should return the provided list when searchQuery is blank`() {
        val uiHistoryItem = mockk<UiHistoryItem>()
        val uiHistoryItems = listOf(uiHistoryItem)

        val result = sut(
            listToFilter = uiHistoryItems,
            searchQuery = "     ",
        )

        assertThat(result).isEqualTo(uiHistoryItems)
    }

    @Test
    fun `invoke should return the filtered list when searchQuery is not blank`() {
        val firstUiHistoryItem = mockk<UiHistoryItem>().apply {
            every { cartName } returns FIRST_CART_NAME
        }
        val secondUiHistoryItem = mockk<UiHistoryItem>().apply {
            every { cartName } returns SECOND_CART_NAME
        }
        val uiHistoryItems = listOf(firstUiHistoryItem, secondUiHistoryItem)

        val result = sut(
            listToFilter = uiHistoryItems,
            searchQuery = SEARCH_QUERY,
        )
        val expectedResult = listOf(firstUiHistoryItem)

        assertThat(result).isEqualTo(expectedResult)
    }
}
