package com.marinj.shoppingwarfare.feature.categorydetail.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "localProduct")
data class LocalProduct(
    @PrimaryKey
    val productId: String,
    val categoryProductId: String,
    val categoryName: String,
    val name: String,
)
