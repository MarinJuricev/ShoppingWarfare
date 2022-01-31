package com.marinj.shoppingwarfare.feature.history.list.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryItem
import com.marinj.shoppingwarfare.feature.history.list.domain.repository.HistoryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ObserveHistoryItemsTest {

    private val historyRepository: HistoryRepository = mockk()

    private lateinit var sut: ObserveHistoryItems

    @Before
    fun setUp() {
        sut = ObserveHistoryItems(
            historyRepository,
        )
    }

    @Test
    fun `invoke should return result from historyRepository observeHistoryItems`() =
        runTest {
            val historyItems = listOf(mockk<HistoryItem>())
            val repositoryFlow = flow { emit(historyItems) }
            coEvery {
                historyRepository.observeHistoryItems()
            } coAnswers { repositoryFlow }

            sut().test {
                assertThat(awaitItem()).isEqualTo(historyItems)
                awaitComplete()
            }
        }
}
