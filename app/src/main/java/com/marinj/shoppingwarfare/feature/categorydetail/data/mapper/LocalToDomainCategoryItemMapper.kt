package com.marinj.shoppingwarfare.feature.categorydetail.data.mapper

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalCategoryItem
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.CategoryItem

class LocalToDomainCategoryItemMapper : Mapper<CategoryItem, LocalCategoryItem> {

    override suspend fun map(origin: LocalCategoryItem): CategoryItem {
        return with(origin) {
            CategoryItem(
                id,
                name
            )
        }
    }
}