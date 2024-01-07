package com.marinj.shoppingwarfare.core.data

import com.marinj.shoppingwarfare.feature.cart.data.datasource.CartDao

interface ShoppingWarfareDatabase {
    fun cartDao(): CartDao
}
