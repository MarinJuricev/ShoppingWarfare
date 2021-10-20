package com.marinj.shoppingwarfare.feature.history.domain.repository

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryItem

interface HistoryRepository {
    suspend fun upsertHistoryItem(historyItem: HistoryItem): Either<Failure, Unit>
}
