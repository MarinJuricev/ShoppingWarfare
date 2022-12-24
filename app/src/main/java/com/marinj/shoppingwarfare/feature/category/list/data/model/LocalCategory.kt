package com.marinj.shoppingwarfare.feature.category.list.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category

@Entity(tableName = "localCategory")
data class LocalCategory(
    @PrimaryKey
    val categoryId: String,
    val title: String,
    val backgroundColor: Int,
    val titleColor: Int,
) {
    fun toDomain() = Category.of(
        categoryId,
        title,
        backgroundColor,
        titleColor,
    )
}

fun Category.toLocal() = LocalCategory(
    categoryId = id,
    title = title,
    backgroundColor = backgroundColor,
    titleColor = titleColor,
)
