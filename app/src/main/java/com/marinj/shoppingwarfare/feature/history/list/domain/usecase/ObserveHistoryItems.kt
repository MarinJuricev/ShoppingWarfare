package com.marinj.shoppingwarfare.feature.history.list.domain.usecase

import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryItem
import kotlinx.coroutines.flow.Flow

interface ObserveHistoryItems {
    operator fun invoke(): Flow<List<HistoryItem>>
}