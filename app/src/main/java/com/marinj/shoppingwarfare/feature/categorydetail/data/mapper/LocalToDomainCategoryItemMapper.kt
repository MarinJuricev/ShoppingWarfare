package com.marinj.shoppingwarfare.feature.categorydetail.data.mapper

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalCategoryItem
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.CategoryItem
import javax.inject.Inject

class LocalToDomainCategoryItemMapper @Inject constructor() : Mapper<CategoryItem, LocalCategoryItem> {

    override suspend fun map(origin: LocalCategoryItem): CategoryItem {
        return with(origin) {
            CategoryItem(
                categoryItemId,
                name
            )
        }
    }
}