package com.marinj.shoppingwarfare.feature.category.list.presentation.mapper

import androidx.compose.ui.graphics.Color
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.UiCategory
import javax.inject.Inject

class CategoryToUiCategoryMapper @Inject constructor() {

    fun map(origin: Category): UiCategory {
        return with(origin) {
            UiCategory(
                id,
                title,
                Color(backgroundColor),
                Color(titleColor),
            )
        }
    }
}
