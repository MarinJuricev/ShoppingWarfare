package com.marinj.shoppingwarfare.feature.categorydetail.data.mapper

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalCategoryProduct
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.CategoryProduct
import javax.inject.Inject

class DomainToLocalCategoryItemMapper @Inject constructor() : Mapper<LocalCategoryProduct, CategoryProduct> {

    override suspend fun map(origin: CategoryProduct): LocalCategoryProduct {
        return with(origin) {
            LocalCategoryProduct(
                id,
                name,
            )
        }
    }
}
