package com.marinj.shoppingwarfare.core.viewmodel.topbar

import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.ext.safeUpdate
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CartTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CategoryDetailTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CategoryTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CreateCategoryTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.HistoryTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.UserTopBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TopBarViewModel @Inject constructor() : BaseViewModel<TopBarEvent>() {

    private val _viewState = MutableStateFlow<TopBarViewState>(NoSearchBarTopBarViewState())
    val viewState = _viewState.asStateFlow()

    override fun onEvent(event: TopBarEvent) {
        when (event) {
            is CategoryTopBar -> handleCategoryTopBar(event)
            is CreateCategoryTopBar -> handleCreateCategoryTopBar(event)
            is CategoryDetailTopBar -> handleCategoryDetailTopBar(event)
            is CartTopBar -> handleCartTopBar(event)
            is HistoryTopBar -> handleHistoryTopBar(event)
            is UserTopBar -> handleUserTopBar(event)
        }
    }

    private fun handleCategoryTopBar(event: CategoryTopBar) {
        _viewState.safeUpdate(
            NoSearchBarTopBarViewState(
                title = event.title,
                icon = event.icon,
                onActionClick = event.onActionClick,
            )
        )
    }

    private fun handleCreateCategoryTopBar(event: CreateCategoryTopBar) {
        _viewState.safeUpdate(
            NoSearchBarTopBarViewState(
                title = event.title,
                subTitle = event.subTitle,
            )
        )
    }

    private fun handleCategoryDetailTopBar(event: CategoryDetailTopBar) {
        _viewState.safeUpdate(
            NoSearchBarTopBarViewState(
                title = event.title,
                subTitle = event.subTitle,
                icon = event.icon,
                onActionClick = event.onActionClick,
            )
        )
    }

    private fun handleCartTopBar(event: CartTopBar) {
        _viewState.safeUpdate(
            NoSearchBarTopBarViewState(
                isTopBarVisible = event.isVisible,
            )
        )
    }

    private fun handleHistoryTopBar(event: HistoryTopBar) {
        _viewState.safeUpdate(
            SearchTopBarViewState(
                searchText = event.searchText,
                isSearchEnabled = event.isSearchEnabled,
                onTextChange = event.onTextChange,
                onActionClick = event.onActionClick,
            )
        )
    }

    private fun handleUserTopBar(event: UserTopBar) {
        _viewState.safeUpdate(
            NoSearchBarTopBarViewState(
                isTopBarVisible = event.isVisible
            )
        )
    }
}
