package com.marinj.shoppingwarfare.feature.history.list.presentation.viewmodel

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.core.fixture.FakeNavigator
import com.marinj.shoppingwarfare.core.navigation.NavigationEvent.Destination
import com.marinj.shoppingwarfare.feature.history.FakeFailureObserveHistoryItems
import com.marinj.shoppingwarfare.feature.history.FakeSuccessObserveHistoryItems
import com.marinj.shoppingwarfare.feature.history.buildHistoryItem
import com.marinj.shoppingwarfare.feature.history.buildUiHistoryItem
import com.marinj.shoppingwarfare.feature.history.detail.presentation.navigation.HistoryDetailNavigation
import com.marinj.shoppingwarfare.feature.history.list.domain.usecase.FilterHistoryItems
import com.marinj.shoppingwarfare.feature.history.list.domain.usecase.ObserveHistoryItems
import com.marinj.shoppingwarfare.feature.history.list.presentation.mapper.HistoryItemToUiHistoryItemMapper
import com.marinj.shoppingwarfare.feature.history.list.presentation.model.HistoryEvent.OnGetHistoryItems
import com.marinj.shoppingwarfare.feature.history.list.presentation.model.HistoryEvent.OnHistoryItemClick
import com.marinj.shoppingwarfare.feature.history.list.presentation.model.HistoryEvent.OnSearchTriggered
import com.marinj.shoppingwarfare.feature.history.list.presentation.model.HistoryEvent.OnSearchUpdated
import com.marinj.shoppingwarfare.feature.history.list.presentation.model.HistoryViewEffect.Error
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class HistoryViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val navigator = FakeNavigator

    @Test
    fun `onEvent SHOULD update historyItems WHEN OnGetHistoryItems is provided and emits historyItems`() =
        runTest {
            val historyItems = listOf(buildHistoryItem())
            val uiHistoryItems = listOf(buildUiHistoryItem(providedDate = "1970-01-01"))
            val sut = buildSut(
                observeHistoryItems = FakeSuccessObserveHistoryItems(historyItems),
            )

            sut.viewState.test {
                val initialViewState = awaitItem()
                assertThat(initialViewState.historyItems).isEmpty()
                assertThat(initialViewState.nonFilteredHistoryItems).isEmpty()
                assertThat(initialViewState.isLoading).isTrue()

                sut.onEvent(OnGetHistoryItems)

                val updatedViewState = awaitItem()
                assertThat(updatedViewState.historyItems).isEqualTo(uiHistoryItems)
                assertThat(updatedViewState.nonFilteredHistoryItems).isEqualTo(uiHistoryItems)
                assertThat(updatedViewState.isLoading).isFalse()
            }
        }

    @Test
    fun `should update viewEffect with Error when OnGetHistoryItems is provided and throws an exception`() =
        runTest {
            val sut = buildSut(
                observeHistoryItems = FakeFailureObserveHistoryItems,
            )

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
    fun `should update searchText when OnSearchUpdated is provided`() = runTest {
        val newSearchText = "newSearchText"
        val event = OnSearchUpdated(newSearchText)
        val sut = buildSut(
            observeHistoryItems = FakeSuccessObserveHistoryItems(),
        )
        sut.onEvent(event)

        sut.viewState.test {
            assertThat(awaitItem().searchText).isEqualTo(newSearchText)
        }
    }

    @Test
    fun `should update historyItems when OnSearchTriggered is provided`() = runTest {
        val event = OnSearchTriggered
        val uiHistoryItem = buildUiHistoryItem(providedDate = "1970-01-01")
        val filteredList = listOf(uiHistoryItem)
        val sut = buildSut(
            observeHistoryItems = FakeSuccessObserveHistoryItems(),
        )
        sut.onEvent(event)

        sut.viewState.test {
            assertThat(awaitItem().historyItems).isEqualTo(filteredList)
        }
    }

    @Test
    fun `should trigger navigator when OnHistoryItemClick is provided`() = runTest {
        val uiHistoryItem = buildUiHistoryItem(providedId = ID)
        val event = OnHistoryItemClick(uiHistoryItem)
        val sut = buildSut(
            observeHistoryItems = FakeSuccessObserveHistoryItems(),
        )
        sut.onEvent(event)

        val expectedDestination = Destination(
            HistoryDetailNavigation.run {
                createHistoryDetailRoute(historyItemId = ID)
            },
        )

        navigator.receivedEvents.test {
            assertThat(awaitItem()).isEqualTo(expectedDestination)
        }
    }

    private fun buildSut(
        observeHistoryItems: ObserveHistoryItems,
    ) =
        HistoryViewModel(
            observeHistoryItems = observeHistoryItems,
            filterHistoryItems = FilterHistoryItems(),
            historyItemToUiHistoryItemMapper = HistoryItemToUiHistoryItemMapper(),
            navigator = navigator,
        )
}

private const val ID = "id"
