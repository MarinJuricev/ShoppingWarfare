package com.marinj.shoppingwarfare.feature.history.list.data.repository

import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.feature.history.FakeFailureHistoryDao
import com.marinj.shoppingwarfare.feature.history.FakeSuccessHistoryDao
import com.marinj.shoppingwarfare.feature.history.buildHistoryItem
import com.marinj.shoppingwarfare.feature.history.buildLocalHistoryItem
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

private const val HISTORY_ITEM_ID = "historyItemId"

class HistoryRepositoryImplTest {

    @Test
    fun `observeHistoryItems SHOULD return historyItems`() = runTest {
        val historyItems = listOf(buildLocalHistoryItem())
        val sut = HistoryRepositoryImpl(
            historyDao = FakeSuccessHistoryDao(historyItems),
        )

        sut.observeHistoryItems().test {
            awaitItem() shouldBe historyItems.mapNotNull { it.toDomain().getOrNull() }
            awaitComplete()
        }
    }

    @Test
    fun `upsertHistoryItem SHOULD return Right Unit`() = runTest {
        val historyItem = buildHistoryItem()
        val sut = HistoryRepositoryImpl(
            historyDao = FakeSuccessHistoryDao(),
        )
        val expectedResult = Unit.right()

        val actualResult = sut.upsertHistoryItem(historyItem)

        actualResult shouldBe expectedResult
    }

    @Test
    fun `dropHistory should return Right Unit WHEN historyDao returns LocalHistoryItem`() =
        runTest {
            val sut = HistoryRepositoryImpl(
                historyDao = FakeSuccessHistoryDao(),
            )
            val expectedResult = Unit.right()

            val actualResult = sut.dropHistory()

            actualResult shouldBe expectedResult
        }

    @Test
    fun `getHistoryItemById should return Left WHEN historyDao getHistoryItemById returns null`() =
        runTest {
            val sut = HistoryRepositoryImpl(
                historyDao = FakeFailureHistoryDao,
            )
            val expectedResult = ErrorMessage("No historyItem present with the id: historyItemId").left()

            val actualResult = sut.getHistoryItemById(HISTORY_ITEM_ID)

            actualResult shouldBe expectedResult
        }

    @Test
    fun `getHistoryItemById should return Right HistoryItem WHEN historyDao getHistoryItemById returns localHistoryItem`() =
        runTest {
            val sut = HistoryRepositoryImpl(
                historyDao = FakeSuccessHistoryDao(),
            )
            val actualResult = sut.getHistoryItemById(HISTORY_ITEM_ID)
            val expectedResult = buildHistoryItem().right()

            actualResult shouldBe expectedResult
        }
}
