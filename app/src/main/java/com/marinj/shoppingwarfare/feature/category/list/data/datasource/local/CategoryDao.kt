package com.marinj.shoppingwarfare.feature.category.list.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marinj.shoppingwarfare.feature.category.list.data.model.LocalCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM localCategory")
    fun observeCategories(): Flow<List<LocalCategory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCategory(entity: LocalCategory): Long

    @Query("DELETE FROM localCategory WHERE categoryId == :id")
    suspend fun deleteCategoryById(id: String)
}
