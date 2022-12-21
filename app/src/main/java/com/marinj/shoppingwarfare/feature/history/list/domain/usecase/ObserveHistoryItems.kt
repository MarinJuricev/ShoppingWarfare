package com.marinj.shoppingwarfare.feature.history.list.domain.usecase

import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryItem
import com.marinj.shoppingwarfare.feature.history.list.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveHistoryItems @Inject constructor(
    private val historyRepository: HistoryRepository,
) {

    operator fun invoke(): Flow<List<HistoryItem>> =
        historyRepository.observeHistoryItems()
}
