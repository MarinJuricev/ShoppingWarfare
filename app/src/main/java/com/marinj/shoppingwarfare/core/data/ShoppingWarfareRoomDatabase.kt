package com.marinj.shoppingwarfare.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marinj.shoppingwarfare.feature.cart.data.model.LocalCartItem
import com.marinj.shoppingwarfare.feature.category.data.model.LocalCategory
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalProduct
import com.marinj.shoppingwarfare.feature.history.data.model.LocalHistoryItem

@Database(
    entities = [
        LocalCategory::class,
        LocalProduct::class,
        LocalCartItem::class,
        LocalHistoryItem::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class ShoppingWarfareRoomDatabase : RoomDatabase(), ShoppingWarfareDatabase
