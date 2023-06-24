package com.marinj.shoppingwarfare.feature.history.detail.presentation.viewmodel

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.core.mapper.FailureToStringMapper
import com.marinj.shoppingwarfare.feature.history.FakeFailureGetHistoryItemById
import com.marinj.shoppingwarfare.feature.history.FakeSuccessGetHistoryItemById
import com.marinj.shoppingwarfare.feature.history.buildUiHistoryItem
import com.marinj.shoppingwarfare.feature.history.detail.presentation.model.HistoryDetailEvent.OnGetHistoryDetail
import com.marinj.shoppingwarfare.feature.history.detail.presentation.model.HistoryDetailViewEffect.Error
import com.marinj.shoppingwarfare.feature.history.list.presentation.mapper.HistoryItemToUiHistoryItemMapper
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class HistoryDetailViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val historyItemToUiHistoryItemMapper = HistoryItemToUiHistoryItemMapper()
    private val failureToStringMapper = FailureToStringMapper()

    @Test
    fun `onEvent SHOULD update uiHistoryItem when OnGetHistoryDetail is provided and getHistoryItemId returns Right`() =
        runTest {
            val event = OnGetHistoryDetail(HISTORY_ITEM_ID)
            val sut = HistoryDetailViewModel(
                getHistoryItemById = FakeSuccessGetHistoryItemById(),
                historyItemToUiHistoryItemMapper = historyItemToUiHistoryItemMapper,
                failureToStringMapper = failureToStringMapper,
            )

            sut.onEvent(event)

            sut.viewState.test {
                val updatedViewState = awaitItem()

                assertThat(updatedViewState.isLoading).isFalse()
                assertThat(updatedViewState.uiHistoryItem).isEqualTo(
                    buildUiHistoryItem(
                        providedDate = "1970-01-01",
                    ),
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
            )

            sut.onEvent(event)

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(Error("Unknown Error Occurred, please try again later"))
            }
        }
}

private const val HISTORY_ITEM_ID = "historyItemId"
