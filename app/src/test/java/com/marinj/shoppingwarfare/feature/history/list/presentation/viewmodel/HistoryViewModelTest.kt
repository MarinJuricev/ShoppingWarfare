package com.marinj.shoppingwarfare.feature.history.list.presentation.viewmodel

import app.cash.turbine.test
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
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineRule::class)
class HistoryViewModelTest {

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
                initialViewState.historyItems.shouldBeEmpty()
                initialViewState.nonFilteredHistoryItems.shouldBeEmpty()
                initialViewState.isLoading.shouldBeTrue()

                sut.onEvent(OnGetHistoryItems)

                val updatedViewState = awaitItem()
                updatedViewState.historyItems shouldBe uiHistoryItems
                updatedViewState.nonFilteredHistoryItems shouldBe uiHistoryItems
                updatedViewState.isLoading.shouldBeFalse()
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
                initialViewState.historyItems.shouldBeEmpty()
                initialViewState.isLoading.shouldBeTrue()

                sut.onEvent(OnGetHistoryItems)

                awaitItem().isLoading.shouldBeFalse()
            }

            sut.viewEffect.test {
                awaitItem() shouldBe Error("Failed to history items, please try again later.")
            }
        }

    @Test
    fun `should update searchText when OnSearchUpdated is provided`() = runTest {
        val newSearchText = "newSearchText"
        val event = OnSearchUpdated(newSearchText)
        val sut = buildSut(
            observeHistoryItems = FakeSuccessObserveHistoryItems(),
        )

        sut.viewState.test {
            awaitItem().searchText shouldBe ""
            sut.onEvent(event)
            awaitItem().searchText shouldBe newSearchText
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
        sut.onEvent(OnGetHistoryItems)

        sut.viewState.test {
            awaitItem().historyItems.shouldBeEmpty()
            sut.onEvent(event)
            awaitItem().historyItems shouldBe filteredList
        }
    }

    @Test
    fun `should trigger navigator when OnHistoryItemClick is provided`() = runTest {
        val uiHistoryItem = buildUiHistoryItem(providedId = ID)
        val event = OnHistoryItemClick(uiHistoryItem)
        val expectedDestination = Destination(
            HistoryDetailNavigation.run {
                createHistoryDetailRoute(historyItemId = ID)
            },
        )
        val sut = buildSut(
            observeHistoryItems = FakeSuccessObserveHistoryItems(),
        )

        navigator.receivedEvents.test {
            sut.onEvent(event)
            awaitItem() shouldBe expectedDestination
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
