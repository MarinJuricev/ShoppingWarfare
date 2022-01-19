package com.marinj.shoppingwarfare.feature.history.detail.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.feature.history.list.domain.repository.HistoryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

private const val HISTORY_ITEM_ID = "historyItemId"

@ExperimentalCoroutinesApi
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
            val repositoryResult = Failure.Unknown.buildLeft()
            coEvery {
                historyRepository.getHistoryItemById(HISTORY_ITEM_ID)
            } coAnswers { repositoryResult }

            val result = sut(HISTORY_ITEM_ID)

            assertThat(result).isEqualTo(repositoryResult)
        }
}
