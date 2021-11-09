package com.marinj.shoppingwarfare.core.viewmodel.topbar

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

// TODO: Separate TopBarViewState into a sealed interface/class where we have a viewState for a search toolbar and a "regular" one
data class TopBarViewState(
    @StringRes val title: Int? = null,
    @StringRes val subTitle: Int? = null,
    val icon: (@Composable () -> Unit)? = null,
    val onActionClick: (() -> Unit)? = null,
    val isVisible: Boolean = true,
    val isSearchEnabled: Boolean = false,
    val searchText: State<String>? = null,
    val onTextChange: (String) -> Unit = {},
)
