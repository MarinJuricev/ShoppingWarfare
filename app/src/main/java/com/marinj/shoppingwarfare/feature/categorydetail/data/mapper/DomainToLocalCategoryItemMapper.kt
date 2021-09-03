package com.marinj.shoppingwarfare.feature.categorydetail.data.mapper

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalCategoryItem
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.CategoryItem
import javax.inject.Inject

class DomainToLocalCategoryItemMapper @Inject constructor() : Mapper<LocalCategoryItem, CategoryItem> {

    override suspend fun map(origin: CategoryItem): LocalCategoryItem {
        return with(origin) {
            LocalCategoryItem(
                id,
                name,
            )
        }
    }
}