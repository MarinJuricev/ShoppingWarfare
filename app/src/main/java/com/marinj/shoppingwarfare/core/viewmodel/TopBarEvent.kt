package com.marinj.shoppingwarfare.core.viewmodel

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable

sealed class TopBarEvent {
    data class CategoryTopBar(
        @StringRes val title: Int,
        val onActionClick: () -> Unit,
        val icon: @Composable () -> Unit,
    ) : TopBarEvent()

    data class CreateCategoryTopBar(
        @StringRes val title: Int,
        @StringRes val subTitle: Int,
    ) : TopBarEvent()
}
