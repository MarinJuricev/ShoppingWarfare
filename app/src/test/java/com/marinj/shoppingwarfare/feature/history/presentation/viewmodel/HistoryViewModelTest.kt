package com.marinj.shoppingwarfare.feature.history.presentation.viewmodel

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryItem
import com.marinj.shoppingwarfare.feature.history.domain.usecase.ObserveHistoryItems
import com.marinj.shoppingwarfare.feature.history.presentation.model.HistoryEvent.OnGetHistoryItems
import com.marinj.shoppingwarfare.feature.history.presentation.model.HistoryViewEffect.Error
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class HistoryViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val observeHistoryItems: ObserveHistoryItems = mockk()

    private lateinit var sut: HistoryViewModel

    @Before
    fun setUp() {
        sut = HistoryViewModel(
            observeHistoryItems,
        )
    }

    @Test
    fun `should update historyItems when OnGetHistoryItems is provided and emits historyItems`() =
        runBlockingTest {
            val historyItem = mockk<HistoryItem>()
            val historyItems = listOf(historyItem)
            val historyItemsFlow = flow {
                emit(historyItems)
            }
            coEvery {
                observeHistoryItems()
            } coAnswers { historyItemsFlow }

            sut.viewState.test {
                val initialViewState = awaitItem()
                assertThat(initialViewState.historyItems).isEmpty()
                assertThat(initialViewState.isLoading).isTrue()

                sut.onEvent(OnGetHistoryItems)

                val updatedViewState = awaitItem()
                assertThat(updatedViewState.historyItems).isEqualTo(historyItems)
                assertThat(updatedViewState.isLoading).isFalse()
            }
        }

    @Test
    fun `should update viewEffect with Error when OnGetHistoryItems is provided and throws an exception`() =
        runBlockingTest {
            val historyItemsFlow = flow<List<HistoryItem>> {
                throw Exception()
            }
            coEvery {
                observeHistoryItems()
            } coAnswers { historyItemsFlow }

            sut.viewState.test {
                val initialViewState = awaitItem()
                assertThat(initialViewState.historyItems).isEmpty()
                assertThat(initialViewState.isLoading).isTrue()

                sut.onEvent(OnGetHistoryItems)

                assertThat(awaitItem().isLoading).isFalse()
            }

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(Error("Failed to history items, please try again later."))
            }
        }
}
