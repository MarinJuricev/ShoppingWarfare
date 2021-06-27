package com.marinj.shoppingwarfare.feature.category.data.mapper

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.category.data.model.LocalCategory
import com.marinj.shoppingwarfare.feature.category.domain.model.Category
import javax.inject.Inject

class LocalToDomainCategoryMapper @Inject constructor() : Mapper<Category, LocalCategory> {
    override suspend fun map(origin: LocalCategory): Category {
        return with(origin) {
            Category(
                title,
            )
        }
    }
}