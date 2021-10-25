package com.marinj.shoppingwarfare.feature.history.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marinj.shoppingwarfare.feature.history.data.model.LocalHistoryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("SELECT * FROM localHistoryItem")
    fun observeHistoryItems(): Flow<List<LocalHistoryItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertHistoryItem(entity: LocalHistoryItem): Long

    @Query("DELETE FROM localHistoryItem")
    suspend fun deleteHistory()
}
