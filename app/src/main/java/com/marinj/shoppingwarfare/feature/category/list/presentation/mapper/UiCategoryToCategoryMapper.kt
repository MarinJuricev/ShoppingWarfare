package com.marinj.shoppingwarfare.feature.category.list.presentation.mapper

import androidx.compose.ui.graphics.toArgb
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.UiCategory
import javax.inject.Inject

class UiCategoryToCategoryMapper @Inject constructor() {

    fun map(origin: UiCategory): Category? = with(origin) {
        Category.of(
            id,
            title,
            backgroundColor.toArgb(),
            titleColor.toArgb(),
        )
    }
}
