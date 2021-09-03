package com.marinj.shoppingwarfare.core.data

import com.marinj.shoppingwarfare.feature.category.data.datasource.CategoryDao
import com.marinj.shoppingwarfare.feature.categorydetail.data.datasource.CategoryDetailDao

interface ShoppingWarfareDatabase {
    fun categoryDao(): CategoryDao
    fun categoryDetailDao(): CategoryDetailDao
}
