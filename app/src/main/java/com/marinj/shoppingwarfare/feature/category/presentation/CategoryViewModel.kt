package com.marinj.shoppingwarfare.feature.category.presentation

import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.ext.safeUpdate
import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.category.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.domain.usecase.GetCategories
import com.marinj.shoppingwarfare.feature.category.presentation.model.CategoryEvent
import com.marinj.shoppingwarfare.feature.category.presentation.model.CategoryViewState
import com.marinj.shoppingwarfare.feature.category.presentation.model.UiCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategories: GetCategories,
    private val categoryToUiCategoryMapper: Mapper<UiCategory, Category>,
) : BaseViewModel<CategoryEvent>() {

    private val _groceryViewState = MutableStateFlow(CategoryViewState())
    val categoryViewState: StateFlow<CategoryViewState> = _groceryViewState

    override fun onEvent(event: CategoryEvent) {
        when (event) {
            CategoryEvent.GetCategories -> handleGetGroceries()
        }
    }

    private fun handleGetGroceries() = viewModelScope.launch {
        updateIsLoading(isLoading = true)
        getCategories()
            .catch { TODO() }
            .map { categoryList -> categoryList.map { category -> categoryToUiCategoryMapper.map(category) } }
            .onCompletion { updateIsLoading(false) }
            .collect { uiCategoryList ->
                _groceryViewState.safeUpdate(
                    _groceryViewState.value.copy(
                        categories = uiCategoryList
                    )
                )
            }
    }

    private fun updateIsLoading(isLoading: Boolean) {
        _groceryViewState.safeUpdate(
            _groceryViewState.value.copy(
                isLoading = isLoading
            )
        )
    }

}
