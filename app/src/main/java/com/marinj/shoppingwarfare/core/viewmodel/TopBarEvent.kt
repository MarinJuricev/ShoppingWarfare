package com.marinj.shoppingwarfare.core.viewmodel

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.marinj.shoppingwarfare.R

sealed class TopBarEvent {
    data class CategoryTopBar(
        @StringRes val title: Int = R.string.category,
        val onActionClick: () -> Unit,
        val icon: @Composable () -> Unit,
    ) : TopBarEvent()

    data class CreateCategoryTopBar(
        @StringRes val title: Int = R.string.category,
        @StringRes val subTitle: Int = R.string.create_category,
    ) : TopBarEvent()

    data class CartTopBar(
        val isVisible: Boolean = false
    ) : TopBarEvent()

    data class HistoryTopBar(
        val isVisible: Boolean = false
    ) : TopBarEvent()
}
