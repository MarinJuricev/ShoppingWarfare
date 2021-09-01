package com.marinj.shoppingwarfare.feature.category.presentation

import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.ext.safeUpdate
import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.core.result.Either.Left
import com.marinj.shoppingwarfare.core.result.Either.Right
import com.marinj.shoppingwarfare.feature.category.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.domain.usecase.DeleteCategoryUseCase
import com.marinj.shoppingwarfare.feature.category.domain.usecase.GetCategories
import com.marinj.shoppingwarfare.feature.category.domain.usecase.UndoCategoryDeletionUseCase
import com.marinj.shoppingwarfare.feature.category.presentation.model.CategoryEffect
import com.marinj.shoppingwarfare.feature.category.presentation.model.CategoryEvent
import com.marinj.shoppingwarfare.feature.category.presentation.model.CategoryEvent.*
import com.marinj.shoppingwarfare.feature.category.presentation.model.CategoryViewState
import com.marinj.shoppingwarfare.feature.category.presentation.model.UiCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategories: GetCategories,
    private val deleteCategory: DeleteCategoryUseCase,
    private val undoCategoryDeletion: UndoCategoryDeletionUseCase,
    private val categoryToUiCategoryMapper: Mapper<UiCategory, Category>,
    private val uiCategoryToCategoryMapper: Mapper<Category, UiCategory>,
) : BaseViewModel<CategoryEvent>() {

    private val _categoryViewState = MutableStateFlow(CategoryViewState())
    val categoryViewState: StateFlow<CategoryViewState> = _categoryViewState

    private val _categoryEffect = Channel<CategoryEffect>()
    val categoryEffect = _categoryEffect.receiveAsFlow()

    override fun onEvent(event: CategoryEvent) {
        when (event) {
            GetCategories -> handleGetGroceries()
            is DeleteCategory -> handleDeleteCategory(event.uiCategory)
            is NavigateToCategoryDetail -> handleNavigateToCategoryDetail(event.categoryName)
            is UndoCategoryDeletion -> handleUndoCategoryDeletion(event.uiCategory)
        }
    }

    private fun handleGetGroceries() = viewModelScope.launch {
        updateIsLoading(isLoading = true)
        getCategories()
            .catch { handleGetCategoriesError() }
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

    private fun handleGetCategoriesError() = viewModelScope.launch {
        _categoryEffect.send(CategoryEffect.Error("Failed to fetch Categories, try again later."))
    }

    private fun updateIsLoading(isLoading: Boolean) {
        _categoryViewState.safeUpdate(
            _categoryViewState.value.copy(
                isLoading = isLoading
            )
        )
    }

    private fun handleDeleteCategory(uiCategory: UiCategory) = viewModelScope.launch {
        when (deleteCategory(uiCategory.id)) {
            is Right -> _categoryEffect.send(CategoryEffect.DeleteCategory(uiCategory))
            is Left -> _categoryEffect.send(CategoryEffect.Error("Error while deleting category."))
        }
    }

    private fun handleNavigateToCategoryDetail(categoryName: String) = viewModelScope.launch {
        _categoryEffect.send(CategoryEffect.NavigateToCategoryDetail(categoryName))
    }

    private fun handleUndoCategoryDeletion(uiCategory: UiCategory) = viewModelScope.launch {
        when (undoCategoryDeletion(uiCategoryToCategoryMapper.map(uiCategory))) {
            is Right -> Timber.d("${uiCategory.title} successfully restored")
            is Left -> _categoryEffect.send(CategoryEffect.Error("Couldn't undo category deletion."))
        }
    }
}
