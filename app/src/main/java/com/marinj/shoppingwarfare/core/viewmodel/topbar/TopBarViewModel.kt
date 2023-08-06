package com.marinj.shoppingwarfare.core.viewmodel.topbar

import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.base.TIMEOUT_DELAY
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CartTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CategoryTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CreateCategoryTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.HistoryDetailTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.HistoryTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.ProductTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.UserTopBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopBarViewModel @Inject constructor() : BaseViewModel<TopBarEvent>() {

    // Instead of using a MutableStateFlow, we use a MutableSharedFlow with an extraBufferCapacity to keep updates that are
    // not collected yet from the downstream.
    private val _viewState = MutableSharedFlow<TopBarViewState>(replay = 1, extraBufferCapacity = 10)
    val viewState = _viewState
        .buffer()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_DELAY),
            initialValue = NoSearchBarTopBarViewState(),
        )

    override fun onEvent(event: TopBarEvent) {
        when (event) {
            is CategoryTopBar -> handleCategoryTopBar(event)
            is CreateCategoryTopBar -> handleCreateCategoryTopBar(event)
            is ProductTopBar -> handleCategoryDetailTopBar(event)
            is CartTopBar -> handleCartTopBar(event)
            is HistoryTopBar -> handleHistoryTopBar(event)
            is HistoryDetailTopBar -> handleHistoryDetailTopBar(event)
            is UserTopBar -> handleUserTopBar(event)
        }
    }

    private fun handleCategoryTopBar(event: CategoryTopBar) {
        viewModelScope.launch {
            _viewState.emit(
                NoSearchBarTopBarViewState(
                    title = event.title,
                    icon = event.icon,
                    onActionClick = event.onActionClick,
                ),
            )
        }
    }

    private fun handleCreateCategoryTopBar(event: CreateCategoryTopBar) {
        viewModelScope.launch {
            _viewState.emit(
                NoSearchBarTopBarViewState(
                    title = event.title,
                    subTitle = event.subTitle,
                ),
            )
        }
    }

    private fun handleCategoryDetailTopBar(event: ProductTopBar) {
        viewModelScope.launch {
            _viewState.emit(
                NoSearchBarTopBarViewState(
                    title = event.title,
                    subTitle = event.subTitle,
                    icon = event.icon,
                    onActionClick = event.onActionClick,
                ),
            )
        }
    }

    private fun handleCartTopBar(event: CartTopBar) {
        viewModelScope.launch {
            _viewState.emit(
                NoSearchBarTopBarViewState(
                    isTopBarVisible = event.isVisible,
                ),
            )
        }
    }

    private fun handleHistoryTopBar(event: HistoryTopBar) {
        viewModelScope.launch {
            _viewState.emit(
                SearchTopBarViewState(
                    searchText = event.searchTextUpdated,
                    isSearchEnabled = event.isSearchEnabled,
                    onTextChange = event.onTextChange,
                    onActionClick = event.onActionClick,
                ),
            )
        }
    }

    private fun handleHistoryDetailTopBar(event: HistoryDetailTopBar) {
        viewModelScope.launch {
            _viewState.emit(
                NoSearchBarTopBarViewState(
                    title = event.title,
                    subTitle = event.subTitle,
                ),
            )
        }
    }

    private fun handleUserTopBar(event: UserTopBar) {
        viewModelScope.launch {
            _viewState.emit(
                NoSearchBarTopBarViewState(
                    isTopBarVisible = event.isVisible,
                ),
            )
        }
    }
}
