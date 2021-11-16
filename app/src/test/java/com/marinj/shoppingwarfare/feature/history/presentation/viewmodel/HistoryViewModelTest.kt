package com.marinj.shoppingwarfare.feature.history.presentation.viewmodel

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryItem
import com.marinj.shoppingwarfare.feature.history.domain.usecase.ObserveHistoryItems
import com.marinj.shoppingwarfare.feature.history.presentation.mapper.HistoryItemToUiHistoryItemMapper
import com.marinj.shoppingwarfare.feature.history.presentation.model.HistoryEvent
import com.marinj.shoppingwarfare.feature.history.presentation.model.HistoryEvent.OnGetHistoryItems
import com.marinj.shoppingwarfare.feature.history.presentation.model.HistoryViewEffect.Error
import com.marinj.shoppingwarfare.feature.history.presentation.model.UiHistoryItem
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
    private val historyItemToUiHistoryItemMapper: HistoryItemToUiHistoryItemMapper = mockk()

    private lateinit var sut: HistoryViewModel

    @Before
    fun setUp() {
        sut = HistoryViewModel(
            observeHistoryItems,
            historyItemToUiHistoryItemMapper
        )
    }

    @Test
    fun `should update historyItems when OnGetHistoryItems is provided and emits historyItems`() =
        runBlockingTest {
            val uiHistoryItem = mockk<UiHistoryItem>()
            val uiHistoryItems = listOf(uiHistoryItem)
            val historyItem = mockk<HistoryItem>()
            val historyItems = listOf(historyItem)
            val historyItemsFlow = flow {
                emit(historyItems)
            }
            coEvery {
                observeHistoryItems()
            } coAnswers { historyItemsFlow }
            coEvery {
                historyItemToUiHistoryItemMapper.map(historyItem)
            } coAnswers { uiHistoryItem }

            sut.viewState.test {
                val initialViewState = awaitItem()
                assertThat(initialViewState.historyItems).isEmpty()
                assertThat(initialViewState.isLoading).isTrue()

                sut.onEvent(OnGetHistoryItems)

                val updatedViewState = awaitItem()
                assertThat(updatedViewState.historyItems).isEqualTo(uiHistoryItems)
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

    @Test
    fun `should update searchText when OnSearchUpdated is provided`() = runBlockingTest {
        val newSearchText = "newSearchText"
        val event = HistoryEvent.OnSearchUpdated(newSearchText)

        sut.onEvent(event)

        sut.viewState.test {
            assertThat(awaitItem().searchText).isEqualTo(newSearchText)
        }
    }
}
