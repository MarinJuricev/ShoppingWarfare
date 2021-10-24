package com.marinj.shoppingwarfare.feature.history.data.repository

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.history.data.datasource.HistoryDao
import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryItem
import com.marinj.shoppingwarfare.feature.history.domain.repository.HistoryRepository
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao,
) : HistoryRepository {

    override suspend fun upsertHistoryItem(
        historyItem: HistoryItem
    ): Either<Failure, Unit> {
        TODO("Not yet implemented")
    }
}
