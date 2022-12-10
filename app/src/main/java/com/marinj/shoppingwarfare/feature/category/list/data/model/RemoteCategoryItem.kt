package com.marinj.shoppingwarfare.feature.category.list.data.model

import androidx.annotation.Keep
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class RemoteCategoryItem(
    val categoryId: String,
    val title: String,
    val backgroundColor: Int,
    val titleColor: Int,
) {
    fun toLocal() = LocalCategory(
        categoryId = categoryId,
        title = title,
        backgroundColor = backgroundColor,
        titleColor = titleColor,
    )
}

fun Category.toRemote() = RemoteCategoryItem(
    categoryId = id,
    title = title,
    backgroundColor = backgroundColor,
    titleColor = titleColor,
)