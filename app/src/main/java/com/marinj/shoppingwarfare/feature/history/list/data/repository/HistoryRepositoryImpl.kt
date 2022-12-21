package com.marinj.shoppingwarfare.feature.history.list.data.repository

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.history.list.data.datasource.HistoryDao
import com.marinj.shoppingwarfare.feature.history.list.data.mapper.DomainToLocalHistoryItemMapper
import com.marinj.shoppingwarfare.feature.history.list.data.mapper.LocalToDomainHistoryItemMapper
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryItem
import com.marinj.shoppingwarfare.feature.history.list.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao,
    private val localToDomainHistoryItemMapper: LocalToDomainHistoryItemMapper,
    private val domainToLocalHistoryItemMapper: DomainToLocalHistoryItemMapper,
) : HistoryRepository {

    override fun observeHistoryItems(): Flow<List<HistoryItem>> =
        historyDao.observeHistoryItems().map { localHistoryItems ->
            localHistoryItems.map { localHistoryItem ->
                localToDomainHistoryItemMapper.map(localHistoryItem)
            }
        }

    override suspend fun upsertHistoryItem(
        historyItem: HistoryItem,
    ): Either<Failure, Unit> {
        val localHistoryItem = domainToLocalHistoryItemMapper.map(historyItem)
        return when (historyDao.upsertHistoryItem(localHistoryItem)) {
            0L -> ErrorMessage("Error while adding new historyItem").buildLeft()
            else -> Unit.buildRight()
        }
    }

    override suspend fun getHistoryItemById(id: String): Either<Failure, HistoryItem> {
        return when (val result = historyDao.getHistoryItemById(id)) {
            null -> ErrorMessage("No historyItem present with the id: $id").buildLeft()
            else -> localToDomainHistoryItemMapper.map(result).buildRight()
        }
    }

    override suspend fun dropHistory(): Either<Failure, Unit> =
        historyDao.deleteHistory().buildRight()
}
