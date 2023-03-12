package com.marinj.shoppingwarfare.feature.history.list.domain.repository

import arrow.core.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryItem
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun observeHistoryItems(): Flow<List<HistoryItem>>
    suspend fun upsertHistoryItem(historyItem: HistoryItem): Either<Failure, Unit>
    suspend fun getHistoryItemById(id: String): Either<Failure, HistoryItem>
    suspend fun dropHistory(): Either<Failure, Unit>
}
