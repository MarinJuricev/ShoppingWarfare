package com.marinj.shoppingwarfare.core.data

import com.marinj.shoppingwarfare.feature.cart.data.datasource.CartDao
import com.marinj.shoppingwarfare.feature.category.detail.data.datasource.ProductDao
import com.marinj.shoppingwarfare.feature.category.list.data.datasource.CategoryDao
import com.marinj.shoppingwarfare.feature.history.data.datasource.HistoryDao

interface ShoppingWarfareDatabase {
    fun categoryDao(): CategoryDao
    fun productDao(): ProductDao
    fun cartDao(): CartDao
    fun historyDao(): HistoryDao
}
