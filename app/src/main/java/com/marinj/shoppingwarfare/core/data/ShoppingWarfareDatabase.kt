package com.marinj.shoppingwarfare.core.data

import com.marinj.shoppingwarfare.feature.cart.data.datasource.CartDao
import com.marinj.shoppingwarfare.feature.category.data.datasource.CategoryDao
import com.marinj.shoppingwarfare.feature.categorydetail.data.datasource.ProductDao

interface ShoppingWarfareDatabase {
    fun categoryDao(): CategoryDao
    fun productDao(): ProductDao
    fun cartDao(): CartDao
}
