package com.marinj.shoppingwarfare.feature.history.list.data.datasource

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.marinj.shoppingwarfare.core.dispatcher.DispatcherProvider
import com.marinj.shoppingwarfare.db.Database
import com.marinj.shoppingwarfare.db.LocalHistoryCartItem
import com.marinj.shoppingwarfare.feature.history.list.data.mapper.toDomain
import com.marinj.shoppingwarfare.feature.history.list.data.mapper.toLocalHistoryItem
import com.marinj.shoppingwarfare.feature.history.list.data.model.LocalHistoryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryDaoImpl @Inject constructor(
    private val database: Database,
    private val dispatcherProvider: DispatcherProvider,
) : HistoryDao {
    override fun observeHistoryItems(): Flow<List<LocalHistoryItem>> =
        database.transactionWithResult {
            database.historyQueries
                .selectAllHistoryItems()
                .asFlow()
                .mapToList(dispatcherProvider.io)
                .map { historyItem ->
                    with(database) { toLocalHistoryItem(historyItem) }
                }
        }

    override suspend fun upsertHistoryItem(
        entity: LocalHistoryItem,
    ) = database
        .historyQueries
        .upsert(
            id = entity.historyItemId,
            receiptPath = entity.receiptPath,
            timestamp = entity.timestamp,
            cartName = entity.cartName,
        )

    override suspend fun getHistoryItemById(
        id: String,
    ): LocalHistoryItem? = database
        .historyQueries
        .selectLocalHistoryItemById(id)
        .executeAsOneOrNull()
        ?.let { historyItem ->
            with(database) { toLocalHistoryItem(listOf(historyItem)).first() }
        }


    override suspend fun deleteHistory() = database
        .historyQueries
        .deleteAllFromLocalHistoryItem()
}

