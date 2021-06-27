package com.marinj.shoppingwarfare.core.data

import com.marinj.shoppingwarfare.feature.category.data.datasource.CategoryDao

interface ShoppingWarfareDatabase {
    fun categoryDao(): CategoryDao
}
