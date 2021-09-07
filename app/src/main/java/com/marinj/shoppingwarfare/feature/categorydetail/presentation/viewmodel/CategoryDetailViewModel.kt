package com.marinj.shoppingwarfare.feature.categorydetail.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.ext.safeUpdate
import com.marinj.shoppingwarfare.core.result.Either.Left
import com.marinj.shoppingwarfare.core.result.Either.Right
import com.marinj.shoppingwarfare.feature.categorydetail.domain.usecase.CreateProduct
import com.marinj.shoppingwarfare.feature.categorydetail.domain.usecase.ObserveCategoryProducts
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEffect
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEffect.CategoryItemCreated
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEffect.Error
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEvent
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEvent.OnCreateCategoryProduct
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEvent.OnGetCategoryProducts
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
    private val observeCategoryProducts: ObserveCategoryProducts,
    private val createProduct: CreateProduct,
) : BaseViewModel<CategoryDetailEvent>() {

    private val _viewState = MutableStateFlow(CategoryDetailViewState())
    val viewState = _viewState.asStateFlow()

    private val _viewEffect = Channel<CategoryDetailEffect>()
    private val viewEffect = _viewEffect.consumeAsFlow()

    override fun onEvent(event: CategoryDetailEvent) {
        when (event) {
            is OnGetCategoryProducts -> handleGetCategoryProducts(event.categoryId)
            is OnCreateCategoryProduct -> handleCreateCategoryProduct(event.categoryItemName)
        }
    }

    private fun handleGetCategoryProducts(categoryId: String) = viewModelScope.launch {
        updateIsLoading(isLoading = true)
        observeCategoryProducts(categoryId)
            .catch { handleGetCategoriesError() }
            .collect { categoryListItems ->
                _viewState.safeUpdate(
                    _viewState.value.copy(
                        categoryProducts = categoryListItems,
                        isLoading = false,
                    )
                )
            }
    }

    private suspend fun handleGetCategoriesError() {
        updateIsLoading(isLoading = false)
        _viewEffect.send(Error("Failed to fetch category items, try again later."))
    }

    private fun handleCreateCategoryProduct(categoryItemName: String) = viewModelScope.launch {
        when (createProduct(categoryItemName)) {
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
