package com.marinj.shoppingwarfare.feature.category.list.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.ext.safeUpdate
import com.marinj.shoppingwarfare.core.navigation.NavigationEvent.Destination
import com.marinj.shoppingwarfare.core.navigation.Navigator
import com.marinj.shoppingwarfare.core.result.Either.Left
import com.marinj.shoppingwarfare.core.result.Either.Right
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.navigation.CreateCategoryDestination
import com.marinj.shoppingwarfare.feature.category.detail.presentation.navigation.CategoryDetailDestination
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.DeleteCategory
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.ObserveCategories
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.UndoCategoryDeletion
import com.marinj.shoppingwarfare.feature.category.list.presentation.mapper.CategoryToUiCategoryMapper
import com.marinj.shoppingwarfare.feature.category.list.presentation.mapper.UiCategoryToCategoryMapper
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryViewEffect
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryEvent
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryViewState
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.UiCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val observeCategories: ObserveCategories,
    private val deleteCategory: DeleteCategory,
    private val undoCategoryDeletion: UndoCategoryDeletion,
    private val categoryToUiCategoryMapper: CategoryToUiCategoryMapper,
    private val uiCategoryToCategoryMapper: UiCategoryToCategoryMapper,
    private val navigator: Navigator,
) : BaseViewModel<CategoryEvent>() {

    private val _categoryViewState = MutableStateFlow(CategoryViewState())
    val categoryViewState = _categoryViewState.asStateFlow()

    private val _categoryEffect = Channel<CategoryViewEffect>()
    val categoryEffect = _categoryEffect.receiveAsFlow()

    override fun onEvent(event: CategoryEvent) {
        when (event) {
            CategoryEvent.GetCategories -> handleGetGroceries()
            CategoryEvent.NavigateToCreateCategory -> handleNavigateToCreateCategory()
            is CategoryEvent.DeleteCategory -> handleDeleteCategory(event.uiCategory)
            is CategoryEvent.NavigateToCategoryDetail -> handleNavigateToCategoryDetail(
                event.categoryId,
                event.categoryName,
            )
            is CategoryEvent.UndoCategoryDeletion -> handleUndoCategoryDeletion(event.uiCategory)
        }
    }

    private fun handleGetGroceries() = viewModelScope.launch {
        observeCategories()
            .onStart { updateIsLoading(isLoading = true) }
            .catch { handleGetCategoriesError() }
            .map { categoryList ->
                categoryList.map { category -> categoryToUiCategoryMapper.map(category) }
            }
            .collect { uiCategoryList ->
                _categoryViewState.safeUpdate(
                    _categoryViewState.value.copy(
                        categories = uiCategoryList,
                        isLoading = false,
                    )
                )
            }
    }

    private fun handleNavigateToCreateCategory() = viewModelScope.launch {
        navigator.emitDestination(Destination(CreateCategoryDestination.route()))
    }

    private suspend fun handleGetCategoriesError() {
        updateIsLoading(isLoading = false)
        _categoryEffect.send(CategoryViewEffect.Error("Failed to fetch Categories, try again later."))
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
            is Right -> _categoryEffect.send(CategoryViewEffect.DeleteCategoryView(uiCategory))
            is Left -> _categoryEffect.send(CategoryViewEffect.Error("Error while deleting category."))
        }
    }

    private fun handleNavigateToCategoryDetail(
        categoryId: String,
        categoryName: String,
    ) = viewModelScope.launch {
        navigator.emitDestination(
            Destination(
                CategoryDetailDestination.createCategoryDetailRoute(
                    categoryId,
                    categoryName
                )
            )
        )
    }

    private fun handleUndoCategoryDeletion(uiCategory: UiCategory) = viewModelScope.launch {
        when (undoCategoryDeletion(uiCategoryToCategoryMapper.map(uiCategory))) {
            is Right -> Timber.d("${uiCategory.title} successfully restored")
            is Left -> _categoryEffect.send(CategoryViewEffect.Error("Couldn't undo category deletion."))
        }
    }
}
