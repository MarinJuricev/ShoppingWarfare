package com.marinj.shoppingwarfare.feature.createcategory.presentation.viewmodel

import com.marinj.shoppingwarfare.core.base.BaseViewModel

class CreateCategoryViewModel : BaseViewModel<CreateCategoryEvent>() {

    override fun onEvent(event: CreateCategoryEvent) {
        when (event) {
            is CreateCategoryEvent.OnCategoryNameChanged -> TODO()
            is CreateCategoryEvent.OnColorChanged -> TODO()
            CreateCategoryEvent.OnCreateCategoryClicked -> TODO()
        }
    }
}