package com.marinj.shoppingwarfare.feature.history.detail.domain.usecase

import arrow.core.left
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.history.list.domain.repository.HistoryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

private const val HISTORY_ITEM_ID = "historyItemId"

class GetHistoryItemByIdTest {

    private val historyRepository: HistoryRepository = mockk()

    private lateinit var sut: GetHistoryItemById

    @Before
    fun setUp() {
        sut = GetHistoryItemById(historyRepository)
    }

    @Test
    fun `invoke should return result from historyItemRepository getHistoryItemId`() =
        runTest {
            val repositoryResult = Failure.Unknown.left()
            coEvery {
                historyRepository.getHistoryItemById(HISTORY_ITEM_ID)
            } coAnswers { repositoryResult }

            val result = sut(HISTORY_ITEM_ID)

            assertThat(result).isEqualTo(repositoryResult)
        }
}
