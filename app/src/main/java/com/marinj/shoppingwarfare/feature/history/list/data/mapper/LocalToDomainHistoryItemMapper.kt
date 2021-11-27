package com.marinj.shoppingwarfare.feature.history.list.data.mapper

import com.marinj.shoppingwarfare.feature.history.list.data.model.LocalHistoryItem
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryItem
import javax.inject.Inject

class LocalToDomainHistoryItemMapper @Inject constructor() {

    fun map(origin: LocalHistoryItem): HistoryItem {
        return with(origin) {
            HistoryItem(
                id = historyItemId,
                receiptPath = receiptPath,
                timestamp = timestamp,
                cartName = cartName,
                historyCartItems = historyCartItems,
            )
        }
    }
}
