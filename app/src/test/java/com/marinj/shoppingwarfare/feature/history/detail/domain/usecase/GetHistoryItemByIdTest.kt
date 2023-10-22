package com.marinj.shoppingwarfare.feature.history.detail.domain.usecase

import arrow.core.right
import com.marinj.shoppingwarfare.feature.history.FakeSuccessHistoryRepository
import com.marinj.shoppingwarfare.feature.history.buildHistoryItem
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetHistoryItemByIdTest {

    @Test
    fun `invoke should return result from historyItemRepository getHistoryItemId`() =
        runTest {
            val sut = GetHistoryItemByIdImpl(
                historyRepository = FakeSuccessHistoryRepository(),
            )
            val expectedResult = buildHistoryItem().right()

            val result = sut(HISTORY_ITEM_ID)

            result shouldBe expectedResult
        }
}

private const val HISTORY_ITEM_ID = "historyItemId"
