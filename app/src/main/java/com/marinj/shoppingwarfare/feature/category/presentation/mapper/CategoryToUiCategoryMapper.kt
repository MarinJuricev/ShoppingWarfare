package com.marinj.shoppingwarfare.feature.category.presentation.mapper

import androidx.compose.ui.graphics.Color
import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.category.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.presentation.model.UiCategory
import javax.inject.Inject

class CategoryToUiCategoryMapper @Inject constructor() : Mapper<UiCategory, Category> {

    override suspend fun map(origin: Category): UiCategory {
        return with(origin) {
            UiCategory(
                title,
                Color(backgroundColor)
            )
        }
    }
}