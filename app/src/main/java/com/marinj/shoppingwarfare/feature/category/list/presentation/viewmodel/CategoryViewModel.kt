package com.marinj.shoppingwarfare.feature.category.list.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.base.TIMEOUT_DELAY
import com.marinj.shoppingwarfare.core.mapper.FailureToStringMapper
import com.marinj.shoppingwarfare.core.navigation.NavigationEvent.Destination
import com.marinj.shoppingwarfare.core.navigation.Navigator
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.navigation.CreateCategoryDestination
import com.marinj.shoppingwarfare.feature.category.detail.presentation.navigation.ProductDestination
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.DeleteCategory
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.ObserveCategories
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.UndoCategoryDeletion
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryEvent
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryEvent.GetCategories
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryEvent.NavigateToCategoryDetail
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryEvent.NavigateToCreateCategory
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryViewEffect
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryViewEffect.DeleteCategoryView
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryViewEffect.Error
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryViewState
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.UiCategory
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.UiCategory.Companion.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val observeCategories: ObserveCategories,
    private val deleteCategory: DeleteCategory,
    private val undoCategoryDeletion: UndoCategoryDeletion,
    private val failureToStringMapper: FailureToStringMapper,
    private val navigator: Navigator,
) : BaseViewModel<CategoryEvent>() {

    private val isLoading = MutableStateFlow(true)
    private val categories = MutableStateFlow(emptyList<UiCategory>())

    val viewState = combine(
        isLoading,
        categories,
    ) { isLoading, categories ->
        CategoryViewState(
            isLoading = isLoading,
            categories = categories,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_DELAY),
        initialValue = CategoryViewState(),
    )

    private val _viewEffect = Channel<CategoryViewEffect>(Channel.BUFFERED)
    val viewEffect = _viewEffect.receiveAsFlow()

    override fun onEvent(event: CategoryEvent) {
        when (event) {
            GetCategories -> handleGetGroceries()
            NavigateToCreateCategory -> handleNavigateToCreateCategory()
            is CategoryEvent.DeleteCategory -> handleDeleteCategory(event.uiCategory)
            is NavigateToCategoryDetail -> handleNavigateToCategoryDetail(
                event.categoryId,
                event.categoryName,
            )

            is CategoryEvent.UndoCategoryDeletion -> handleUndoCategoryDeletion(event.uiCategory)
        }
    }

    private fun handleGetGroceries() = viewModelScope.launch {
        observeCategories()
            .onStart { isLoading.update { true } }
            .catch { handleGetCategoriesError() }
            .map { categoryList ->
                categoryList.map { category -> category.toUi() }
            }
            .collect { uiCategoryList ->
                isLoading.update { false }
                categories.update { uiCategoryList }
            }
    }

    private fun handleNavigateToCreateCategory() = viewModelScope.launch {
        navigator.emitDestination(Destination(CreateCategoryDestination.route()))
    }

    private suspend fun handleGetCategoriesError() {
        isLoading.update { false }
        _viewEffect.send(Error("Failed to fetch Categories, try again later."))
    }

    private fun handleNavigateToCategoryDetail(
        categoryId: String,
        categoryName: String,
    ) = viewModelScope.launch {
        navigator.emitDestination(
            Destination(
                ProductDestination.createCategoryDetailRoute(
                    categoryId,
                    categoryName,
                ),
            ),
        )
    }

    private fun handleDeleteCategory(uiCategory: UiCategory) = viewModelScope.launch {
        deleteCategory(uiCategory.id).fold(
            ifLeft = { _viewEffect.send(Error("Error while deleting category.")) },
            ifRight = { _viewEffect.send(DeleteCategoryView(uiCategory)) },
        )
    }

    private fun handleUndoCategoryDeletion(
        uiCategory: UiCategory,
    ) = viewModelScope.launch {
        uiCategory.toDomain().fold(
            ifLeft = { _viewEffect.send(Error(failureToStringMapper.map(it))) },
            ifRight = { restoreCategory(it) },
        )
    }

    private suspend fun restoreCategory(
        category: Category,
    ) {
        undoCategoryDeletion(category).fold(
            ifLeft = { _viewEffect.send(Error("Couldn't undo category deletion.")) },
            ifRight = { Timber.d("${category.title} successfully restored") },
        )
    }
}
