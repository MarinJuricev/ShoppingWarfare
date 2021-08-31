package com.marinj.shoppingwarfare.feature.category.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "localCategory")
data class LocalCategory(
    @PrimaryKey
    val id: String,
    val title: String,
    val backgroundColor: Int,
    val titleColor: Int,
)
