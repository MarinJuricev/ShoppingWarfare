package com.marinj.shoppingwarfare.feature.category.data.mapper

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.category.data.model.LocalCategory
import com.marinj.shoppingwarfare.feature.category.domain.model.Category
import javax.inject.Inject

class DomainToLocalCategoryMapper @Inject constructor() : Mapper<LocalCategory, Category> {

    override suspend fun map(origin: Category): LocalCategory {
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
