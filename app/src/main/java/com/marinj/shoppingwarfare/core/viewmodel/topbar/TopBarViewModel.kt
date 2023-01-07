package com.marinj.shoppingwarfare.core.viewmodel.topbar

import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.base.TIMEOUT_DELAY
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CartTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.ProductTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CategoryTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CreateCategoryTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.HistoryDetailTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.HistoryTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.UserTopBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TopBarViewModel @Inject constructor() : BaseViewModel<TopBarEvent>() {

    private val _viewState = MutableStateFlow<TopBarViewState>(NoSearchBarTopBarViewState())
    val viewState = _viewState.stateIn(
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
        _viewState.update {
            NoSearchBarTopBarViewState(
                title = event.title,
                icon = event.icon,
                onActionClick = event.onActionClick,
            )
        }
    }

    private fun handleCreateCategoryTopBar(event: CreateCategoryTopBar) {
        _viewState.update {
            NoSearchBarTopBarViewState(
                title = event.title,
                subTitle = event.subTitle,
            )
        }
    }

    private fun handleCategoryDetailTopBar(event: ProductTopBar) {
        _viewState.update {
            NoSearchBarTopBarViewState(
                title = event.title,
                subTitle = event.subTitle,
                icon = event.icon,
                onActionClick = event.onActionClick,
            )
        }
    }

    private fun handleCartTopBar(event: CartTopBar) {
        _viewState.update {
            NoSearchBarTopBarViewState(
                isTopBarVisible = event.isVisible,
            )
        }
    }

    private fun handleHistoryTopBar(event: HistoryTopBar) {
        _viewState.update {
            SearchTopBarViewState(
                searchText = event.searchTextUpdated,
                isSearchEnabled = event.isSearchEnabled,
                onTextChange = event.onTextChange,
                onActionClick = event.onActionClick,
            )
        }
    }

    private fun handleHistoryDetailTopBar(event: HistoryDetailTopBar) {
        _viewState.update {
            NoSearchBarTopBarViewState(
                title = event.title,
                subTitle = event.subTitle,
            )
        }
    }

    private fun handleUserTopBar(event: UserTopBar) {
        _viewState.update {
            NoSearchBarTopBarViewState(
                isTopBarVisible = event.isVisible,
            )
        }
    }
}
