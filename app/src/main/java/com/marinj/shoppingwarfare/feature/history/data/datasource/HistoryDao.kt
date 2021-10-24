package com.marinj.shoppingwarfare.feature.history.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.marinj.shoppingwarfare.feature.history.data.model.LocalHistoryItem

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertHistoryItem(entity: LocalHistoryItem): Long
}
