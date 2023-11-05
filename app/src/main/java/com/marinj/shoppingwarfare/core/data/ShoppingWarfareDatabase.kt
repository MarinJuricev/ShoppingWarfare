package com.marinj.shoppingwarfare.core.data

import com.marinj.shoppingwarfare.feature.cart.data.datasource.CartDao
import com.marinj.shoppingwarfare.feature.history.list.data.datasource.HistoryDao

interface ShoppingWarfareDatabase {
    fun cartDao(): CartDao
    fun historyDao(): HistoryDao
}
