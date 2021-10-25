package com.marinj.shoppingwarfare.feature.history.data.repository

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.history.data.datasource.HistoryDao
import com.marinj.shoppingwarfare.feature.history.data.mapper.LocalToDomainHistoryItemMapper
import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryItem
import com.marinj.shoppingwarfare.feature.history.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao,
    private val localToDomainHistoryItemMapper: LocalToDomainHistoryItemMapper,
) : HistoryRepository {

    override fun observeHistoryItems(): Flow<List<HistoryItem>> =
        historyDao.observeHistoryItems().map { localHistoryItems ->
            localHistoryItems.map { localHistoryItem ->
                localToDomainHistoryItemMapper.map(localHistoryItem)
            }
        }

    override suspend fun upsertHistoryItem(
        historyItem: HistoryItem
    ): Either<Failure, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun dropHistory(): Either<Failure, Unit> {
        TODO("Not yet implemented")
    }
}
