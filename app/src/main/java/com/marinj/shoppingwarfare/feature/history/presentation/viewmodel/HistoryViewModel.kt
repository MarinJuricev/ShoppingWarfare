package com.marinj.shoppingwarfare.feature.history.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.ext.safeUpdate
import com.marinj.shoppingwarfare.feature.history.domain.usecase.ObserveHistoryItems
import com.marinj.shoppingwarfare.feature.history.presentation.model.HistoryEvent
import com.marinj.shoppingwarfare.feature.history.presentation.model.HistoryEvent.OnGetHistoryItems
import com.marinj.shoppingwarfare.feature.history.presentation.model.HistoryEvent.OnSearchUpdated
import com.marinj.shoppingwarfare.feature.history.presentation.model.HistoryViewEffect
import com.marinj.shoppingwarfare.feature.history.presentation.model.HistoryViewEffect.Error
import com.marinj.shoppingwarfare.feature.history.presentation.model.HistoryViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val observeHistoryItems: ObserveHistoryItems,
) : BaseViewModel<HistoryEvent>() {

    private val _viewState = MutableStateFlow(HistoryViewState())
    val viewState = _viewState.asStateFlow()

    private val _viewEffect = Channel<HistoryViewEffect>()
    val viewEffect = _viewEffect.receiveAsFlow()

    override fun onEvent(event: HistoryEvent) {
        when (event) {
            OnGetHistoryItems -> handleGetHistoryItems()
            is OnSearchUpdated -> handleSearchUpdated(event.newSearch)
        }
    }

    private fun handleGetHistoryItems() = viewModelScope.launch {
        observeHistoryItems()
            .onStart { updateIsLoading(isLoading = true) }
            .catch { handleGetHistoryItemsError() }
            .collect { historyItems ->
                _viewState.safeUpdate(
                    _viewState.value.copy(
                        historyItems = historyItems,
                        isLoading = false,
                    )
                )
            }
    }

    private fun handleSearchUpdated(newSearch: String) {
        _viewState.safeUpdate(
            _viewState.value.copy(searchText = newSearch)
        )
    }

    private suspend fun handleGetHistoryItemsError() {
        updateIsLoading(false)
        _viewEffect.send(Error("Failed to history items, please try again later."))
    }

    private fun updateIsLoading(isLoading: Boolean) {
        _viewState.safeUpdate(
            _viewState.value.copy(
                isLoading = isLoading
            )
        )
    }
}
