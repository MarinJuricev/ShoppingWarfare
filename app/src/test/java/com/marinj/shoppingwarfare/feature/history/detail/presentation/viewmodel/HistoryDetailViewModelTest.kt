package com.marinj.shoppingwarfare.feature.history.detail.presentation.viewmodel

import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.core.mapper.FailureToStringMapper
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.history.detail.domain.usecase.GetHistoryItemByIdImpl
import com.marinj.shoppingwarfare.feature.history.detail.presentation.model.HistoryDetailEvent.OnGetHistoryDetail
import com.marinj.shoppingwarfare.feature.history.detail.presentation.model.HistoryDetailViewEffect.Error
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryItem
import com.marinj.shoppingwarfare.feature.history.list.presentation.mapper.HistoryItemToUiHistoryItemMapper
import com.marinj.shoppingwarfare.feature.history.list.presentation.model.UiHistoryItem
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val HISTORY_ITEM_ID = "historyItemId"

class HistoryDetailViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val getHistoryItemById: GetHistoryItemByIdImpl = mockk()
    private val historyItemToUiHistoryItemMapper: HistoryItemToUiHistoryItemMapper = mockk()
    private val failureToStringMapper: FailureToStringMapper = mockk()

    private lateinit var sut: HistoryDetailViewModel

    @Before
    fun setUp() {
        sut = HistoryDetailViewModel(
            getHistoryItemById = getHistoryItemById,
            historyItemToUiHistoryItemMapper = historyItemToUiHistoryItemMapper,
            failureToStringMapper = failureToStringMapper,
        )
    }

    @Test
    fun `should update uiHistoryItem when OnGetHistoryDetail is provided and getHistoryItemId returns Right`() =
        runTest {
            val event = OnGetHistoryDetail(HISTORY_ITEM_ID)
            val useCaseResult = mockk<HistoryItem>()
            val useCaseResultRight = useCaseResult.right()
            val mapperResult = mockk<UiHistoryItem>()
            coEvery {
                getHistoryItemById(HISTORY_ITEM_ID)
            } coAnswers { useCaseResultRight }
            coEvery {
                historyItemToUiHistoryItemMapper.map(useCaseResult)
            } coAnswers { mapperResult }

            sut.onEvent(event)

            sut.viewState.test {
                val updatedViewState = awaitItem()

                assertThat(updatedViewState.isLoading).isFalse()
                assertThat(updatedViewState.uiHistoryItem).isEqualTo(mapperResult)
            }
        }

    @Test
    fun `should update viewEffect when OnGetHistoryDetail is provided and getHistoryItemId returns Left`() =
        runTest {
            val event = OnGetHistoryDetail(HISTORY_ITEM_ID)
            val useCaseResult = Failure.Unknown
            val useCaseResultRight = useCaseResult.left()
            val mapperResult = "ErrorMessage"
            coEvery {
                getHistoryItemById(HISTORY_ITEM_ID)
            } coAnswers { useCaseResultRight }
            coEvery {
                failureToStringMapper.map(useCaseResult)
            } coAnswers { mapperResult }

            sut.onEvent(event)

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(Error(mapperResult))
            }
        }
}
