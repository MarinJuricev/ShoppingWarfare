package com.marinj.shoppingwarfare.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marinj.shoppingwarfare.feature.category.data.model.LocalCategory
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalCategoryProduct

@Database(
    entities = [
        LocalCategory::class,
        LocalCategoryProduct::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class ShoppingWarfareRoomDatabase : RoomDatabase(), ShoppingWarfareDatabase
