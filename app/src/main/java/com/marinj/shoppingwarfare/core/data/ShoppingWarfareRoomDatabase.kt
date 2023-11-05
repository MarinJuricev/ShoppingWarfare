package com.marinj.shoppingwarfare.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.marinj.shoppingwarfare.feature.cart.data.model.LocalCartItem
import com.marinj.shoppingwarfare.feature.history.list.data.datasource.HistoryDaoTypeConverters
import com.marinj.shoppingwarfare.feature.history.list.data.model.LocalHistoryItem

@Database(
    entities = [
        LocalCartItem::class,
        LocalHistoryItem::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(HistoryDaoTypeConverters::class)
abstract class ShoppingWarfareRoomDatabase : RoomDatabase(), ShoppingWarfareDatabase
