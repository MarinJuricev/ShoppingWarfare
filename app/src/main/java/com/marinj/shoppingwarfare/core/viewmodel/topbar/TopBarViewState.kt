package com.marinj.shoppingwarfare.core.viewmodel.topbar

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

// TODO: Separate TopBarViewState into a sealed interface/class where we have a viewState for a search toolbar and a "regular" one
sealed class TopBarViewState(val isVisible: Boolean = true)

data class SearchTopBarViewState(
    val isSearchEnabled: Boolean = false,
    val onActionClick: (() -> Unit)? = null,
    val onTextChange: (String) -> Unit = {},
    val searchText: State<String>? = null,
    val isTopBarVisible: Boolean = true,
) : TopBarViewState(isVisible = isTopBarVisible)

data class NoSearchBarTopBarViewState(
    @StringRes val title: Int? = null,
    @StringRes val subTitle: Int? = null,
    val icon: (@Composable () -> Unit)? = null,
    val onActionClick: (() -> Unit)? = null,
    val isTopBarVisible: Boolean = true,
) : TopBarViewState(isVisible = isTopBarVisible)
