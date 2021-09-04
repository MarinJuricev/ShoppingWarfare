package com.marinj.shoppingwarfare.feature.categorydetail.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.ext.safeUpdate
import com.marinj.shoppingwarfare.core.result.Either.Left
import com.marinj.shoppingwarfare.core.result.Either.Right
import com.marinj.shoppingwarfare.feature.categorydetail.domain.usecase.CreateCategoryItem
import com.marinj.shoppingwarfare.feature.categorydetail.domain.usecase.ObserveCategoryItems
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEffect
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEffect.CategoryItemCreated
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEffect.Error
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEvent
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEvent.OnCreateCategoryItem
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEvent.OnGetCategoryItems
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
    private val observeCategoryItems: ObserveCategoryItems,
    private val createCategoryItem: CreateCategoryItem,
) : BaseViewModel<CategoryDetailEvent>() {

    private val _viewState = MutableStateFlow(CategoryDetailViewState())
    val viewState = _viewState.asStateFlow()

    private val _viewEffect = Channel<CategoryDetailEffect>()
    private val viewEffect = _viewEffect.consumeAsFlow()

    override fun onEvent(event: CategoryDetailEvent) {
        when (event) {
            is OnGetCategoryItems -> handleGetCategoryItems(event.categoryId)
            is OnCreateCategoryItem -> handleCreateCategoryItem(event.categoryItemName)
        }
    }

    private fun handleGetCategoryItems(categoryId: String) = viewModelScope.launch {
        updateIsLoading(isLoading = true)
        observeCategoryItems(categoryId)
            .catch { handleGetCategoriesError() }
            .collect { categoryListItems ->
                _viewState.safeUpdate(
                    _viewState.value.copy(
                        categoryItems = categoryListItems,
                        isLoading = false,
                    )
                )
            }
    }

    private suspend fun handleGetCategoriesError() {
        updateIsLoading(isLoading = false)
        _viewEffect.send(Error("Failed to fetch category items, try again later."))
    }

    private fun handleCreateCategoryItem(categoryItemName: String) = viewModelScope.launch {
        when (createCategoryItem(categoryItemName)) {
            is Right -> _viewEffect.send(CategoryItemCreated)
            is Left -> _viewEffect.send(Error("Could not create $categoryItemName, try again later."))
        }
    }

    private fun updateIsLoading(isLoading: Boolean) {
        _viewState.safeUpdate(
            _viewState.value.copy(
                isLoading = isLoading
            )
        )
    }
}
