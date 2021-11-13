package com.marinj.shoppingwarfare.core.viewmodel.topbar

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.marinj.shoppingwarfare.R

sealed interface TopBarEvent {
    data class CategoryTopBar(
        @StringRes val title: Int = R.string.category,
        val onActionClick: () -> Unit,
        val icon: @Composable () -> Unit,
    ) : TopBarEvent

    data class CreateCategoryTopBar(
        @StringRes val title: Int = R.string.category,
        @StringRes val subTitle: Int = R.string.create_category,
    ) : TopBarEvent

    data class CategoryDetailTopBar(
        @StringRes val title: Int = R.string.category,
        @StringRes val subTitle: Int = R.string.category_detail,
        val onActionClick: () -> Unit,
        val icon: @Composable () -> Unit,
    ) : TopBarEvent

    data class CartTopBar(
        val isVisible: Boolean = false
    ) : TopBarEvent

    data class HistoryTopBar(
        val isSearchEnabled: Boolean = true,
        val searchTextUpdated: () -> String,
        val onTextChange: (String) -> Unit,
        val onActionClick: () -> Unit,
    ) : TopBarEvent

    data class UserTopBar(
        val isVisible: Boolean = false
    ) : TopBarEvent
}
