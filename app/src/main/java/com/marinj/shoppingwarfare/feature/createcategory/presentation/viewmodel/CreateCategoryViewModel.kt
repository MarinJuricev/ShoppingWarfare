package com.marinj.shoppingwarfare.feature.createcategory.presentation.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.ext.safeUpdate
import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.domain.model.Category
import com.marinj.shoppingwarfare.feature.createcategory.domain.repository.CreateCategoryRepository
import com.marinj.shoppingwarfare.feature.createcategory.domain.validator.CategoryValidator
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEvent
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCategoryViewModel @Inject constructor(
    private val categoryValidator: CategoryValidator,
    private val createCategoryRepository: CreateCategoryRepository,
    private val failureToStringMapper: Mapper<String, Failure>,
) : BaseViewModel<CreateCategoryEvent>() {

    private val _createCategoryViewState = MutableStateFlow(CreateCategoryViewState())
    val createCategoryViewState: StateFlow<CreateCategoryViewState> = _createCategoryViewState

    override fun onEvent(event: CreateCategoryEvent) {
        when (event) {
            is CreateCategoryEvent.OnCategoryNameChanged -> handleCategoryNameChanged(event.categoryText)
            is CreateCategoryEvent.OnColorChanged -> handleColorChanged(event.selectedColor)
            CreateCategoryEvent.OnCreateCategoryClicked -> handleCategoryClicked()
        }
    }

    private fun handleCategoryNameChanged(categoryText: String) {
        _createCategoryViewState.safeUpdate(
            _createCategoryViewState.value.copy(
                categoryName = categoryText
            )
        )
    }

    private fun handleColorChanged(selectedColor: Color) {
        _createCategoryViewState.safeUpdate(
            _createCategoryViewState.value.copy(
                selectedColor = selectedColor
            )
        )
    }

    private fun handleCategoryClicked() = viewModelScope.launch {
        val categoryName = _createCategoryViewState.value.categoryName
        when (val result = categoryValidator.validate(categoryName)) {
            is Either.Left -> updateError(result.error)
            is Either.Right -> createCategoryRepository.createCategory(
                Category(
                    _createCategoryViewState.value.categoryName,
                    _createCategoryViewState.value.selectedColor?.toArgb() ?: 0,
                )
            )
        }
    }

    private suspend fun updateError(error: Failure) {
        _createCategoryViewState.safeUpdate(
            _createCategoryViewState.value.copy(
                errorMessage = failureToStringMapper.map(error)
            )
        )
    }
}