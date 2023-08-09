package com.marinj.shoppingwarfare.feature.history.detail.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.base.TIMEOUT_DELAY
import com.marinj.shoppingwarfare.core.mapper.FailureToStringMapper
import com.marinj.shoppingwarfare.core.navigation.NavigationEvent.NavigateUp
import com.marinj.shoppingwarfare.core.navigation.Navigator
import com.marinj.shoppingwarfare.feature.history.detail.domain.usecase.GetHistoryItemById
import com.marinj.shoppingwarfare.feature.history.detail.presentation.model.HistoryDetailEvent
import com.marinj.shoppingwarfare.feature.history.detail.presentation.model.HistoryDetailEvent.OnBackClicked
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryDetailViewModel @Inject constructor(
    private val getHistoryItemById: GetHistoryItemById,
    private val historyItemToUiHistoryItemMapper: HistoryItemToUiHistoryItemMapper,
    private val failureToStringMapper: FailureToStringMapper,
    private val navigator: Navigator,
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
            OnBackClicked -> viewModelScope.launch { navigator.emitDestination(NavigateUp) }
        }
    }

    private fun handleGetHistoryDetail(historyItemId: String) = viewModelScope.launch {
        getHistoryItemById(historyItemId).fold(
            ifLeft = { _viewEffect.send(Error(errorMessage = failureToStringMapper.map(it))) },
            ifRight = {
                _viewState.update { viewState ->
                    viewState.copy(
                        isLoading = false,
                        uiHistoryItem = historyItemToUiHistoryItemMapper.map(it),
                    )
                }
            },

            )
    }
}
