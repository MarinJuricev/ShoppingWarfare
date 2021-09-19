package com.marinj.shoppingwarfare.feature.cart.data.datasource

import androidx.room.Dao
import androidx.room.Query
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM localCartItem")
    fun observeCartItems(): Flow<List<LocalProduct>>


}