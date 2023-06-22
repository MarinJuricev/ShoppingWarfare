package com.marinj.shoppingwarfare.feature.history.list.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.history.buildUiHistoryItem
import org.junit.Test

class FilterHistoryItemsTest {

    private val sut = FilterHistoryItems()

    @Test
    fun `invoke SHOULD return the provided list WHEN searchQuery is empty`() {
        val uiHistoryItem = buildUiHistoryItem()
        val uiHistoryItems = listOf(uiHistoryItem)

        val result = sut(
            listToFilter = uiHistoryItems,
            searchQuery = "",
        )

        assertThat(result).isEqualTo(uiHistoryItems)
    }

    @Test
    fun `invoke SHOULD return the provided list WHEN searchQuery is blank`() {
        val uiHistoryItem = buildUiHistoryItem()
        val uiHistoryItems = listOf(uiHistoryItem)

        val result = sut(
            listToFilter = uiHistoryItems,
            searchQuery = "     ",
        )

        assertThat(result).isEqualTo(uiHistoryItems)
    }

    @Test
    fun `invoke SHOULD return the filtered list WHEN searchQuery is not blank`() {
        val firstUiHistoryItem = buildUiHistoryItem(providedCartName = FIRST_CART_NAME)
        val secondUiHistoryItem = buildUiHistoryItem(providedCartName = SECOND_CART_NAME)
        val uiHistoryItems = listOf(firstUiHistoryItem, secondUiHistoryItem)

        val result = sut(
            listToFilter = uiHistoryItems,
            searchQuery = SEARCH_QUERY,
        )
        val expectedResult = listOf(firstUiHistoryItem)

        assertThat(result).isEqualTo(expectedResult)
    }
}

private const val SEARCH_QUERY = "first"
private const val FIRST_CART_NAME = "firstCartName"
private const val SECOND_CART_NAME = "secondCartName"
