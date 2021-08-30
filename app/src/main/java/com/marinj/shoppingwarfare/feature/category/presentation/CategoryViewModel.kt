package com.marinj.shoppingwarfare.feature.category.presentation

import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.ext.safeUpdate
import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.category.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.domain.usecase.GetCategories
import com.marinj.shoppingwarfare.feature.category.presentation.model.CategoryEffect
import com.marinj.shoppingwarfare.feature.category.presentation.model.CategoryEvent
import com.marinj.shoppingwarfare.feature.category.presentation.model.CategoryEvent.*
import com.marinj.shoppingwarfare.feature.category.presentation.model.CategoryViewState
import com.marinj.shoppingwarfare.feature.category.presentation.model.UiCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategories: GetCategories,
    private val categoryToUiCategoryMapper: Mapper<UiCategory, Category>,
) : BaseViewModel<CategoryEvent>() {

    private val _categoryViewState = MutableStateFlow(CategoryViewState())
    val categoryViewState: StateFlow<CategoryViewState> = _categoryViewState

    private val _categoryEffect = Channel<CategoryEffect>()
    val categoryEffect = _categoryEffect.receiveAsFlow()

    override fun onEvent(event: CategoryEvent) {
        when (event) {
            GetCategories -> handleGetGroceries()
            is DeleteCategory -> handleDeleteCategory(event.categoryName)
            is NavigateToCategoryDetail -> handleNavigateToCategoryDetail(event.categoryName)
        }
    }

    private fun handleGetGroceries() = viewModelScope.launch {
        updateIsLoading(isLoading = true)
        getCategories()
            .catch { TODO("Add a simple oneshot SnackBar in the categoryEffect") }
            .map { categoryList ->
                categoryList.map { category ->
                    categoryToUiCategoryMapper.map(
                        category
                    )
                }
            }
            .onCompletion { updateIsLoading(false) }
            .collect { uiCategoryList ->
                _categoryViewState.safeUpdate(
                    _categoryViewState.value.copy(
                        categories = uiCategoryList
                    )
                )
            }
    }

    private fun updateIsLoading(isLoading: Boolean) {
        _categoryViewState.safeUpdate(
            _categoryViewState.value.copy(
                isLoading = isLoading
            )
        )
    }

    private fun handleDeleteCategory(categoryName: String) = viewModelScope.launch {
        _categoryEffect.send(CategoryEffect.DeleteCategory(categoryName))
    }

    private fun handleNavigateToCategoryDetail(categoryName: String) = viewModelScope.launch {
        _categoryEffect.send(CategoryEffect.(categoryName))
    }
}
