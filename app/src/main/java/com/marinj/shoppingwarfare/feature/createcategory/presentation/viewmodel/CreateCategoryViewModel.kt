package com.marinj.shoppingwarfare.feature.createcategory.presentation.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.ext.safeUpdate
import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.createcategory.domain.usecase.CreateCategory
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEffect
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEffect.CreateCategorySuccess
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEvent
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEvent.OnBackgroundColorChanged
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEvent.OnCategoryNameChanged
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEvent.OnCreateCategoryClicked
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEvent.OnTitleColorChanged
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCategoryViewModel @Inject constructor(
    private val createCategory: CreateCategory,
    private val failureToCreateCategoryEffectMapper: Mapper<CreateCategoryEffect, Failure>,
) : BaseViewModel<CreateCategoryEvent>() {

    private val _createCategoryViewState = MutableStateFlow(CreateCategoryViewState())
    val createCategoryViewState: StateFlow<CreateCategoryViewState> = _createCategoryViewState

    private val _createCategoryEffect = Channel<CreateCategoryEffect>()
    val createCategoryEffect = _createCategoryEffect.receiveAsFlow()

    override fun onEvent(event: CreateCategoryEvent) {
        when (event) {
            is OnCategoryNameChanged -> handleCategoryNameChanged(event.categoryText)
            is OnBackgroundColorChanged -> handleBackgroundColorChanged(event.selectedColor)
            is OnTitleColorChanged -> handleTitleColorChanged(event.selectedColor)
            OnCreateCategoryClicked -> handleCategoryClicked()
        }
    }

    private fun handleCategoryNameChanged(categoryText: String) {
        _createCategoryViewState.safeUpdate(
            _createCategoryViewState.value.copy(
                categoryName = categoryText
            )
        )
    }

    private fun handleBackgroundColorChanged(selectedColor: Color) {
        _createCategoryViewState.safeUpdate(
            _createCategoryViewState.value.copy(
                backgroundColor = selectedColor
            )
        )
    }

    private fun handleTitleColorChanged(selectedColor: Color) {
        _createCategoryViewState.safeUpdate(
            _createCategoryViewState.value.copy(
                titleColor = selectedColor
            )
        )
    }

    private fun handleCategoryClicked() = viewModelScope.launch {
        val categoryName = _createCategoryViewState.value.categoryName
        val categoryColor = _createCategoryViewState.value.backgroundColor
        val titleColor = _createCategoryViewState.value.titleColor
        when (val result = createCategory(categoryName, categoryColor?.toArgb(), titleColor?.toArgb())) {
            is Either.Left -> _createCategoryEffect.send(
                failureToCreateCategoryEffectMapper.map(result.error)
            )
            is Either.Right -> _createCategoryEffect.send(CreateCategorySuccess)
        }
    }
}
