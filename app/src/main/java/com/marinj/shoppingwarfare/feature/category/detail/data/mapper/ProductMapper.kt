package com.marinj.shoppingwarfare.feature.category.detail.data.mapper

import com.marinj.shoppingwarfare.db.LocalProduct
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product.Companion.Product

fun LocalProduct.toDomain() = Product(
    id = id,
    categoryId = categoryId,
    categoryName = categoryName,
    name = name,
)

fun Product.toLocal() = LocalProduct(
    id = id.value,
    categoryId = categoryId.value,
    categoryName = categoryName.value,
    name = name.value,
)
