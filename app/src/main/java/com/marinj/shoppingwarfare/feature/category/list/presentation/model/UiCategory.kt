package com.marinj.shoppingwarfare.feature.category.list.presentation.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category.Companion.Category

data class UiCategory(
    val id: String,
    val title: String,
    val backgroundColor: Color,
    val titleColor: Color,
) {
    fun toDomain() = Category(
        id = id,
        title = title,
        backgroundColor = backgroundColor.toArgb(),
        titleColor = titleColor.toArgb(),
    )
}
fun Category.toUi() = UiCategory(
    id = id.value,
    title = title.value,
    backgroundColor = Color(backgroundColor.value),
    titleColor = Color(titleColor.value),
)
