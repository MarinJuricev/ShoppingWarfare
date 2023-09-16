package com.marinj.shoppingwarfare.feature.category.detail.data.model

import androidx.annotation.Keep
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class RemoteProduct(
    val id: String,
    val categoryId: String,
    val categoryName: String,
    val name: String,
) {
    fun toLocal() = LocalProduct(
        id = id,
        categoryId = categoryId,
        categoryName = categoryName,
        name = name,
    )
}

fun Product.toRemote() = RemoteProduct(
    id = id.value,
    categoryId = categoryId.value,
    categoryName = categoryName.value,
    name = name.value,
)
