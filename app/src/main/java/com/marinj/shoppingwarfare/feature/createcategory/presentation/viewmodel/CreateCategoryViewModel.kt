package com.marinj.shoppingwarfare.feature.createcategory.presentation.viewmodel

import androidx.compose.ui.graphics.Color
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEvent
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CreateCategoryViewModel @Inject constructor() : BaseViewModel<CreateCategoryEvent>() {

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
        _createCategoryViewState.compareAndSet(
            _createCategoryViewState.value,
            _createCategoryViewState.value.copy(
                categoryName = categoryText
            )
        )
    }

    private fun handleColorChanged(selectedColor: Color) {
        _createCategoryViewState.compareAndSet(
            _createCategoryViewState.value,
            _createCategoryViewState.value.copy(
                selectedColor = selectedColor
            )
        )
    }

    private fun handleCategoryClicked() {

    }
}