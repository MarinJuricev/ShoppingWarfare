package com.marinj.shoppingwarfare.feature.category.createcategory.presentation.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.base.TIMEOUT_DELAY
import com.marinj.shoppingwarfare.core.mapper.FailureToStringMapper
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEvent
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEvent.OnBackgroundColorChanged
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEvent.OnCategoryNameChanged
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEvent.OnCreateCategoryClicked
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEvent.OnTitleColorChanged
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryViewEffect
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryViewEffect.CreateCategoryViewFailure
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryViewEffect.CreateCategoryViewSuccess
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryViewState
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.CreateCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCategoryViewModel @Inject constructor(
    private val createCategory: CreateCategory,
    private val failureToStringMapper: FailureToStringMapper,
) : BaseViewModel<CreateCategoryEvent>() {

    private val _createCategoryViewState = MutableStateFlow(CreateCategoryViewState())
    val createCategoryViewState = _createCategoryViewState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_DELAY),
        initialValue = CreateCategoryViewState(),
    )

    private val _createCategoryEffect = Channel<CreateCategoryViewEffect>(Channel.BUFFERED)
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
        _createCategoryViewState.update { viewState ->
            viewState.copy(
                categoryName = categoryText,
            )
        }
    }

    private fun handleBackgroundColorChanged(selectedColor: Color) {
        _createCategoryViewState.update { viewState ->
            viewState.copy(
                backgroundColor = selectedColor,
            )
        }
    }

    private fun handleTitleColorChanged(selectedColor: Color) {
        _createCategoryViewState.update { viewState ->
            viewState.copy(
                titleColor = selectedColor,
            )
        }
    }

    private fun handleCategoryClicked() = viewModelScope.launch {
        val categoryName = _createCategoryViewState.value.categoryName
        val categoryColor = _createCategoryViewState.value.backgroundColor
        val titleColor = _createCategoryViewState.value.titleColor

        createCategory(categoryName, categoryColor?.toArgb(), titleColor?.toArgb()).fold(
            ifLeft = { _createCategoryEffect.send(CreateCategoryViewFailure(failureToStringMapper.map(it))) },
            ifRight = { _createCategoryEffect.send(CreateCategoryViewSuccess) },
        )
    }
}
