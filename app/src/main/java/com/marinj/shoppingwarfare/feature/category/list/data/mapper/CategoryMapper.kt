package com.marinj.shoppingwarfare.feature.category.list.data.mapper

import com.marinj.shoppingwarfare.db.LocalCategory
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category.Companion.Category

fun LocalCategory.toDomain() = Category(
    id,
    title,
    backgroundColor.toInt(),
    titleColor.toInt(),
)

fun Category.toLocal() = LocalCategory(
    id = id.value,
    title = title.value,
    backgroundColor = backgroundColor.value.toLong(),
    titleColor = titleColor.value.toLong(),
)
