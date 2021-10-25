package com.marinj.shoppingwarfare.feature.history.domain.repository

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.history.data.model.LocalHistoryItem
import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryItem
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun observeHistoryItems(): Flow<List<HistoryItem>>
    suspend fun upsertHistoryItem(historyItem: HistoryItem): Either<Failure, Unit>
    suspend fun dropHistory(): Either<Failure, Unit>
}
