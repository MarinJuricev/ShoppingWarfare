package com.marinj.shoppingwarfare.feature.category.detail.data.mapper

import com.marinj.shoppingwarfare.feature.category.detail.data.model.LocalProduct
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import javax.inject.Inject

class DomainToLocalCategoryItemMapper @Inject constructor() {

    fun map(origin: Product): LocalProduct {
        return with(origin) {
            LocalProduct(
                productId = id,
                categoryProductId = categoryId,
                categoryName = categoryName,
                name = name,
            )
        }
    }
}
