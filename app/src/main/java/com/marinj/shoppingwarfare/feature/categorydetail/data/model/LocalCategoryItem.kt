package com.marinj.shoppingwarfare.feature.categorydetail.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "localCategoryItem")
data class LocalCategoryItem(
    @PrimaryKey
    val categoryItemId: String,
    val name: String,
)
