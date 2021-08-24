package com.marinj.shoppingwarfare.feature.category.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "localCategory")
data class LocalCategory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val backgroundColor: Int,
    val titleColor: Int,
)
