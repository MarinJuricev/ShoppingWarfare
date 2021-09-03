package com.marinj.shoppingwarfare.feature.categorydetail.presentation.viewmodel

import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEvent
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEvent.OnCreateCategoryItem
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEvent.OnGetCategoryItems
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor() : BaseViewModel<CategoryDetailEvent>() {

    private val _viewState = MutableStateFlow(CategoryDetailViewState())
    val viewState = _viewState.asStateFlow()

    override fun onEvent(event: CategoryDetailEvent) {
        when (event) {
            is OnGetCategoryItems -> handleGetCategoryItems(event.categoryId)
            is OnCreateCategoryItem -> handleCreateCategoryItem(event.categoryItemName)
        }
    }

    private fun handleGetCategoryItems(categoryId: String) {
    }

    private fun handleCreateCategoryItem(categoryItemName: String) {
    }
}
