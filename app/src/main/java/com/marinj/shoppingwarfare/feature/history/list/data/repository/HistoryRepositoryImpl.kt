package com.marinj.shoppingwarfare.feature.history.list.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.feature.history.list.data.datasource.HistoryDao
import com.marinj.shoppingwarfare.feature.history.list.data.model.toLocal
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryItem
import com.marinj.shoppingwarfare.feature.history.list.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao,
) : HistoryRepository {

    override fun observeHistoryItems(): Flow<List<HistoryItem>> =
        historyDao.observeHistoryItems().map { localHistoryItems ->
            localHistoryItems.mapNotNull { localHistoryItem ->
                localHistoryItem.toDomain().getOrNull()
            }
        }

    override suspend fun upsertHistoryItem(
        historyItem: HistoryItem,
    ): Either<Failure, Unit> = historyDao.upsertHistoryItem(historyItem.toLocal()).right()

    override suspend fun getHistoryItemById(id: String): Either<Failure, HistoryItem> {
        return when (val result = historyDao.getHistoryItemById(id)) {
            null -> ErrorMessage("No historyItem present with the id: $id").left()
            else -> result.toDomain()
        }
    }

    override suspend fun dropHistory(): Either<Failure, Unit> =
        historyDao.deleteHistory().right()
}
