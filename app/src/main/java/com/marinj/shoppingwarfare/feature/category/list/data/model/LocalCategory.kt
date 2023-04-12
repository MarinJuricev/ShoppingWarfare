package com.marinj.shoppingwarfare.feature.category.list.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category.Companion.Category

@Entity(tableName = "localCategory")
data class LocalCategory(
    @PrimaryKey
    val categoryId: String,
    val title: String,
    val backgroundColor: Int,
    val titleColor: Int,
) {
    fun toDomain() = Category(
        categoryId,
        title,
        backgroundColor,
        titleColor,
    )
}

fun Category.toLocal() = LocalCategory(
    categoryId = id.value,
    title = title.value,
    backgroundColor = backgroundColor.value,
    titleColor = titleColor.value,
)
