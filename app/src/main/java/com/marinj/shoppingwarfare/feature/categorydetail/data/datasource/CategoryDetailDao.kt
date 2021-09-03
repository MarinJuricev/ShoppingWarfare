package com.marinj.shoppingwarfare.feature.categorydetail.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marinj.shoppingwarfare.feature.category.data.model.LocalCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDetailDao {

    @Query("SELECT * FROM localCategory")
    fun getCategories(): Flow<List<LocalCategory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCategory(entity: LocalCategory): Long

    @Query("DELETE FROM localCategory WHERE id == :id")
    suspend fun deleteCategoryById(id: String)
}
