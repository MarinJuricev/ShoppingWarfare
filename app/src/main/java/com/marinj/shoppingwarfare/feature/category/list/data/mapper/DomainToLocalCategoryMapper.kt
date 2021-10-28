package com.marinj.shoppingwarfare.feature.category.list.data.mapper

import com.marinj.shoppingwarfare.feature.category.list.data.model.LocalCategory
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import javax.inject.Inject

class DomainToLocalCategoryMapper @Inject constructor() {

    fun map(origin: Category): LocalCategory {
        return with(origin) {
            LocalCategory(
                categoryId = id,
                title = title,
                backgroundColor = backgroundColor,
                titleColor = titleColor,
            )
        }
    }
}
