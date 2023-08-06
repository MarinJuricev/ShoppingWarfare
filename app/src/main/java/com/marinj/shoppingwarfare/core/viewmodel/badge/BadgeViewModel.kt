package com.marinj.shoppingwarfare.core.viewmodel.badge

import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.base.TIMEOUT_DELAY
import com.marinj.shoppingwarfare.core.viewmodel.badge.BadgeEvent.StartObservingBadgesCount
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.ObserveCartItemsCount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BadgeViewModel @Inject constructor(
    private val observeCartItemsCount: ObserveCartItemsCount,
) : BaseViewModel<BadgeEvent>() {

    private val badgeCount = MutableStateFlow<Int?>(null)

    val viewState = badgeCount
        .mapLatest { updatedBadgeCount -> BadgeViewState(cartBadgeCount = updatedBadgeCount) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_DELAY),
            initialValue = BadgeViewState(),
        )

    override fun onEvent(event: BadgeEvent) {
        when (event) {
            StartObservingBadgesCount -> handleStartObservingBadgesCount()
        }
    }

    private fun handleStartObservingBadgesCount() = observeCartItemsCount()
        .onEach { newBadgeCount ->
            badgeCount.update { newBadgeCount }
        }.launchIn(viewModelScope)
}
