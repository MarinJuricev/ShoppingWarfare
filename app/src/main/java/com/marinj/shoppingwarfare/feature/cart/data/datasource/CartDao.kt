package com.marinj.shoppingwarfare.feature.cart.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marinj.shoppingwarfare.feature.cart.data.model.LocalCartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM localCartItem")
    fun observeCartItems(): Flow<List<LocalCartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCartItem(entity: LocalCartItem): Long

    @Query("DELETE FROM localCartItem WHERE cartItemId == :id")
    suspend fun deleteCartItemById(id: String)
}
