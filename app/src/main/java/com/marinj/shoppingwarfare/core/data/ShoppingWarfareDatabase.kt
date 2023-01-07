package com.marinj.shoppingwarfare.core.data

import com.marinj.shoppingwarfare.feature.cart.data.datasource.CartDao
import com.marinj.shoppingwarfare.feature.category.detail.data.datasource.local.ProductDao
import com.marinj.shoppingwarfare.feature.category.list.data.datasource.local.CategoryDao
import com.marinj.shoppingwarfare.feature.history.list.data.datasource.HistoryDao

interface ShoppingWarfareDatabase {
    fun categoryDao(): CategoryDao
    fun productDao(): ProductDao
    fun cartDao(): CartDao
    fun historyDao(): HistoryDao
}
