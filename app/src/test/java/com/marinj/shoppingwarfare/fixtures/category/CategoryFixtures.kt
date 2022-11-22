package com.marinj.shoppingwarfare.fixtures.category

import com.marinj.shoppingwarfare.feature.category.list.data.model.LocalCategory
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category

fun buildLocalCategory(
    providedCategoryId: String = "",
    providedTitle: String = "",
    providedBackgroundColor: Int = 0,
    providedTitleColor: Int = 0,
) = LocalCategory(
    categoryId = providedCategoryId,
    title = providedTitle,
    backgroundColor = providedBackgroundColor,
    titleColor = providedTitleColor,
)

fun buildCategory(
    providedId: String = "",
    providedTitle: String = "",
    providedBackgroundColor: Int = 0,
    providedTitleColor: Int = 0,
) = Category(
    id = providedId,
    title = providedTitle,
    backgroundColor = providedBackgroundColor,
    titleColor = providedTitleColor,
)
