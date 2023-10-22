package com.marinj.shoppingwarfare.feature.history.detail.presentation.viewmodel

import app.cash.turbine.test
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.core.fixture.FakeNavigator
import com.marinj.shoppingwarfare.core.mapper.FailureToStringMapper
import com.marinj.shoppingwarfare.core.navigation.NavigationEvent.NavigateUp
import com.marinj.shoppingwarfare.feature.history.FakeFailureGetHistoryItemById
import com.marinj.shoppingwarfare.feature.history.FakeSuccessGetHistoryItemById
import com.marinj.shoppingwarfare.feature.history.buildUiHistoryItem
import com.marinj.shoppingwarfare.feature.history.detail.presentation.model.HistoryDetailEvent
import com.marinj.shoppingwarfare.feature.history.detail.presentation.model.HistoryDetailEvent.OnGetHistoryDetail
import com.marinj.shoppingwarfare.feature.history.detail.presentation.model.HistoryDetailViewEffect.Error
import com.marinj.shoppingwarfare.feature.history.list.presentation.mapper.HistoryItemToUiHistoryItemMapper
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class HistoryDetailViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val historyItemToUiHistoryItemMapper = HistoryItemToUiHistoryItemMapper()
    private val failureToStringMapper = FailureToStringMapper()
    private val navigator = FakeNavigator

    @Test
    fun `onEvent SHOULD update uiHistoryItem when OnGetHistoryDetail is provided and getHistoryItemId returns Right`() =
        runTest {
            val event = OnGetHistoryDetail(HISTORY_ITEM_ID)
            val sut = HistoryDetailViewModel(
                getHistoryItemById = FakeSuccessGetHistoryItemById(),
                historyItemToUiHistoryItemMapper = historyItemToUiHistoryItemMapper,
                failureToStringMapper = failureToStringMapper,
                navigator = navigator,
            )

            sut.onEvent(event)

            sut.viewState.test {
                val updatedViewState = awaitItem()

                updatedViewState.isLoading.shouldBeFalse()
                updatedViewState.uiHistoryItem shouldBe
                    buildUiHistoryItem(
                        providedDate = "1970-01-01",
                    )
            }
        }

    @Test
    fun `should update viewEffect when OnGetHistoryDetail is provided and getHistoryItemId returns Left`() =
        runTest {
            val event = OnGetHistoryDetail(HISTORY_ITEM_ID)
            val sut = HistoryDetailViewModel(
                getHistoryItemById = FakeFailureGetHistoryItemById,
                historyItemToUiHistoryItemMapper = historyItemToUiHistoryItemMapper,
                failureToStringMapper = failureToStringMapper,
                navigator = navigator,
            )

            sut.onEvent(event)

            sut.viewEffect.test {
                awaitItem() shouldBe Error("Unknown Error Occurred, please try again later")
            }
        }

    @Test
    fun `SHOULD emit navigateUp WHEN OnBackClicked is provided`() = runTest {
        val event = HistoryDetailEvent.OnBackClicked
        val sut = HistoryDetailViewModel(
            getHistoryItemById = FakeFailureGetHistoryItemById,
            historyItemToUiHistoryItemMapper = historyItemToUiHistoryItemMapper,
            failureToStringMapper = failureToStringMapper,
            navigator = navigator,
        )

        navigator.receivedEvents.test {
            sut.onEvent(event)
            awaitItem() shouldBe NavigateUp
        }
    }
}

private const val HISTORY_ITEM_ID = "historyItemId"
