package com.marinj.shoppingwarfare.feature.categorydetail.data.mapper

import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalCategoryProducts
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.Product
import javax.inject.Inject

class LocalCategoryProductsListToDomainProductMapper @Inject constructor() {

    fun map(origin: List<LocalCategoryProducts>): List<Product> {
        return origin.flatMap { localCategoryProducts ->
            localCategoryProducts.productList.map { localProduct ->
                Product(
                    id = localProduct.productId,
                    categoryId = localProduct.categoryProductId,
                    categoryName = localProduct.categoryName,
                    name = localProduct.name,
                )
            }
        }
    }
}
