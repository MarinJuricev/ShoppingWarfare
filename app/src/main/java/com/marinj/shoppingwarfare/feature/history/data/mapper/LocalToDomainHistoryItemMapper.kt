package com.marinj.shoppingwarfare.feature.history.data.mapper

import com.marinj.shoppingwarfare.feature.history.data.model.LocalHistoryItem
import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryItem
import javax.inject.Inject

class LocalToDomainHistoryItemMapper @Inject constructor() {

    fun map(origin: LocalHistoryItem): HistoryItem {
        return with(origin) {
            HistoryItem(
                id = historyItemId,
                receiptPath = receiptPath,
                timestamp = timestamp,
                historyCartItems = historyCartItems,
            )
        }
    }
}