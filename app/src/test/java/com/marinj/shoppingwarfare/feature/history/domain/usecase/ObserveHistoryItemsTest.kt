package com.marinj.shoppingwarfare.feature.history.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryItem
import com.marinj.shoppingwarfare.feature.history.domain.repository.HistoryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
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
        runBlockingTest {
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
