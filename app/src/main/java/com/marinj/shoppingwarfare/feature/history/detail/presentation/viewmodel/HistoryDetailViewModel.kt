package com.marinj.shoppingwarfare.feature.history.detail.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.base.TIMEOUT_DELAY
import com.marinj.shoppingwarfare.core.ext.safeUpdate
import com.marinj.shoppingwarfare.core.mapper.FailureToStringMapper
import com.marinj.shoppingwarfare.core.result.Either.Left
import com.marinj.shoppingwarfare.core.result.Either.Right
import com.marinj.shoppingwarfare.feature.history.detail.domain.usecase.GetHistoryItemById
import com.marinj.shoppingwarfare.feature.history.detail.presentation.model.HistoryDetailEvent
import com.marinj.shoppingwarfare.feature.history.detail.presentation.model.HistoryDetailEvent.OnGetHistoryDetail
import com.marinj.shoppingwarfare.feature.history.detail.presentation.model.HistoryDetailViewEffect
import com.marinj.shoppingwarfare.feature.history.detail.presentation.model.HistoryDetailViewEffect.Error
import com.marinj.shoppingwarfare.feature.history.detail.presentation.model.HistoryDetailViewState
import com.marinj.shoppingwarfare.feature.history.list.presentation.mapper.HistoryItemToUiHistoryItemMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryDetailViewModel @Inject constructor(
    private val getHistoryItemById: GetHistoryItemById,
    private val historyItemToUiHistoryItemMapper: HistoryItemToUiHistoryItemMapper,
    private val failureToStringMapper: FailureToStringMapper,
) : BaseViewModel<HistoryDetailEvent>() {

    private val _viewState = MutableStateFlow(HistoryDetailViewState())
    val viewState = _viewState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_DELAY),
        initialValue = HistoryDetailViewState(),
    )

    private val _viewEffect = Channel<HistoryDetailViewEffect>(Channel.BUFFERED)
    val viewEffect = _viewEffect.receiveAsFlow()

    override fun onEvent(event: HistoryDetailEvent) {
        when (event) {
            is OnGetHistoryDetail -> handleGetHistoryDetail(event.historyItemId)
        }
    }

    private fun handleGetHistoryDetail(historyItemId: String) = viewModelScope.launch {
        when (val result = getHistoryItemById(historyItemId)) {
            is Right -> _viewState.safeUpdate(
                _viewState.value.copy(
                    isLoading = false,
                    uiHistoryItem = historyItemToUiHistoryItemMapper.map(result.value)
                )
            )
            is Left -> _viewEffect.send(
                Error(errorMessage = failureToStringMapper.map(result.error))
            )
        }
    }
}
