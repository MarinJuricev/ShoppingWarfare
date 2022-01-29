package com.marinj.shoppingwarfare.feature.cart.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "localCartItem")
data class LocalCartItem(
    @PrimaryKey
    val cartItemId: String,
    val categoryName: String,
    val name: String,
    val quantity: Int,
    val isInBasket: Boolean,
)
