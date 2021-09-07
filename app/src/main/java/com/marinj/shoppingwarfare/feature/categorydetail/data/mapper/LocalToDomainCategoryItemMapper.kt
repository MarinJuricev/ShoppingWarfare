package com.marinj.shoppingwarfare.feature.categorydetail.data.mapper

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalProduct
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.Product
import javax.inject.Inject

class LocalToDomainCategoryItemMapper @Inject constructor() : Mapper<Product, LocalProduct> {

    override suspend fun map(origin: LocalProduct): Product {
        return with(origin) {
            Product(
                productId,
                categoryProductId,
                name
            )
        }
    }
}
