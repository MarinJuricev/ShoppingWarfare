package com.marinj.shoppingwarfare.feature.category.list.presentation.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category

data class UiCategory(
    val id: String,
    val title: String,
    val backgroundColor: Color,
    val titleColor: Color,
) {
    fun toDomain() = Category.of(
        id = id,
        title = title,
        backgroundColor = backgroundColor.toArgb(),
        titleColor = backgroundColor.toArgb(),
    )

    companion object {
        fun Category.toUi() = UiCategory(
            id = id,
            title = title,
            backgroundColor = Color(backgroundColor),
            titleColor = Color(titleColor),
        )
    }
}
