package com.marinj.shoppingwarfare.feature.category.detail.data.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class RemoteProduct(
    val productId: String,
    val categoryId: String,
    val categoryName: String,
    val name: String,
) {
    fun toLocal() = LocalProduct(
        productId = productId,
        categoryProductId = categoryId,
        categoryName = categoryName,
        name = name,
    )
}
