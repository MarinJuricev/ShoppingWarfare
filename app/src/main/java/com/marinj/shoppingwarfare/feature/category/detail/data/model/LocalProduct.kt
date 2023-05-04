package com.marinj.shoppingwarfare.feature.category.detail.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product.Companion.Product

@Entity(tableName = "localProduct")
data class LocalProduct(
    @PrimaryKey
    val productId: String,
    val categoryProductId: String,
    val categoryName: String,
    val name: String,
) {
    fun toDomain() = Product(
        id = productId,
        categoryId = categoryProductId,
        categoryName = categoryName,
        name = name,
    )
}

fun Product.toLocal() = LocalProduct(
    productId = id.value,
    categoryProductId = categoryId.value,
    categoryName = categoryName.value,
    name = name.value,
)
