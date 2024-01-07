package com.marinj.shoppingwarfare.feature.history.list.data.datasource

import com.marinj.shoppingwarfare.feature.history.list.data.model.LocalHistoryItem
import kotlinx.coroutines.flow.Flow

interface HistoryDao {

    fun observeHistoryItems(): Flow<List<LocalHistoryItem>>

    suspend fun upsertHistoryItem(entity: LocalHistoryItem)

    suspend fun getHistoryItemById(id: String): LocalHistoryItem?

    suspend fun deleteHistory()
}
