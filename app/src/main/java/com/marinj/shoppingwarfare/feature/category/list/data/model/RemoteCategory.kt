package com.marinj.shoppingwarfare.feature.category.list.data.model

import androidx.annotation.Keep
import com.marinj.shoppingwarfare.db.LocalCategory
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class RemoteCategory(
    val categoryId: String,
    val title: String,
    val backgroundColor: Int,
    val titleColor: Int,
) {
    fun toLocal() = LocalCategory(
        id = categoryId,
        title = title,
        backgroundColor = backgroundColor.toLong(),
        titleColor = titleColor.toLong(),
    )
}

fun Category.toRemote() = RemoteCategory(
    categoryId = id.value,
    title = title.value,
    backgroundColor = backgroundColor.value,
    titleColor = titleColor.value,
)
