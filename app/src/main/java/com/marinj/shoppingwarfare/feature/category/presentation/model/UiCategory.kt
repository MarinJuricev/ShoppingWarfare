package com.marinj.shoppingwarfare.feature.category.presentation.model

import androidx.annotation.DrawableRes

data class UiCategory(
    private val id: Int,
    private val title: String,
    @DrawableRes private val iconId: Int
)
