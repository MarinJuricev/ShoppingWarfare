package com.marinj.shoppingwarfare.feature.category.presentation.mapper

import androidx.compose.ui.graphics.toArgb
import com.marinj.shoppingwarfare.feature.category.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.presentation.model.UiCategory
import javax.inject.Inject

class UiCategoryToCategoryMapper @Inject constructor() {

    fun map(origin: UiCategory): Category {
        return with(origin) {
            Category(
                id,
                title,
                backgroundColor.toArgb(),
                titleColor.toArgb(),
            )
        }
    }
}
