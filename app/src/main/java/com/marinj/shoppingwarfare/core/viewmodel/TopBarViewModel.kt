package com.marinj.shoppingwarfare.core.viewmodel

import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.ext.safeUpdate
import com.marinj.shoppingwarfare.core.viewmodel.TopBarEvent.CategoryTopBar
import com.marinj.shoppingwarfare.core.viewmodel.TopBarEvent.CreateCategoryTopBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TopBarViewModel @Inject constructor() : BaseViewModel<TopBarEvent>() {

    private val _viewState = MutableStateFlow(TopBarViewState())
    val viewState = _viewState.asStateFlow()

    override fun onEvent(event: TopBarEvent) {
        when (event) {
            is CategoryTopBar -> handleCategoryTopBar(event)
            is CreateCategoryTopBar -> handleCreateCategoryTopBar(event)
        }
    }

    private fun handleCategoryTopBar(event: CategoryTopBar) {
        _viewState.safeUpdate(
            TopBarViewState(
                title = event.title,
                subTitle = null,
                icon = event.icon,
                onActionClick = event.onActionClick,
            )
        )
    }

    private fun handleCreateCategoryTopBar(event: CreateCategoryTopBar) {
        _viewState.safeUpdate(
            TopBarViewState(
                title = event.title,
                subTitle = event.subTitle,
                icon = null,
                onActionClick = null,
            )
        )
    }
}
