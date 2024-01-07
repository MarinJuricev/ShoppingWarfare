package com.marinj.shoppingwarfare.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marinj.shoppingwarfare.feature.cart.data.model.LocalCartItem

@Database(
    entities = [
        LocalCartItem::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class ShoppingWarfareRoomDatabase : RoomDatabase(), ShoppingWarfareDatabase
