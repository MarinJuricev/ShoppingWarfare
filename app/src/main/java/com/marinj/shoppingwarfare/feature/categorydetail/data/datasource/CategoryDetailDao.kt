package com.marinj.shoppingwarfare.feature.categorydetail.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalCategoryItem
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalCategoryItems
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDetailDao {

    @Transaction
    @Query("SELECT * FROM localCategory")
    fun observeCategoryItemsForGivenCategoryId(): Flow<List<LocalCategoryItems>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCategoryItem(entity: LocalCategoryItem): Long

    @Query("DELETE FROM localCategoryItem WHERE categoryItemId == :id")
    suspend fun deleteCategoryItemById(id: String)
}
