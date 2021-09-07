package com.marinj.shoppingwarfare.feature.categorydetail.data.mapper

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalCategoryProduct
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.CategoryProduct
import javax.inject.Inject

class LocalToDomainCategoryItemMapper @Inject constructor() : Mapper<CategoryProduct, LocalCategoryProduct> {

    override suspend fun map(origin: LocalCategoryProduct): CategoryProduct {
        return with(origin) {
            CategoryProduct(
                categoryProductId,
                name
            )
        }
    }
}
