package com.marinj.shoppingwarfare.core.viewmodel.badge

import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.ext.safeUpdate
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.ObserveCartItemsCount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BadgeViewModel @Inject constructor(
    private val observeCartItemsCount: ObserveCartItemsCount,
) : BaseViewModel<BadgeEvent>() {

    private val _viewState = MutableStateFlow(BadgeViewState())
    val viewState = _viewState.asStateFlow()

    override fun onEvent(event: BadgeEvent) {
        when (event) {
            BadgeEvent.StartObservingBadgesCount -> handleStartObservingBadgesCount()
        }
    }

    private fun handleStartObservingBadgesCount() = viewModelScope.launch {
        observeCartItemsCount().collect { newBadgeCount ->
            _viewState.safeUpdate(
                _viewState.value.copy(
                    cartBadgeCount = newBadgeCount
                )
            )
        }
    }
}
