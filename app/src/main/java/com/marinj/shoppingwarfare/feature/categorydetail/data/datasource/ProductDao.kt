package com.marinj.shoppingwarfare.feature.categorydetail.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalCategoryProducts
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Transaction
    @Query("SELECT * FROM localCategory")
    fun observeProductsForGivenCategoryId(): Flow<List<LocalCategoryProducts>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProduct(entity: LocalProduct): Long

    @Query("DELETE FROM localProduct WHERE productId == :productId")
    suspend fun deleteProductById(productId: String)
}
