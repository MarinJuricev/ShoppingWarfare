package com.marinj.shoppingwarfare.feature.history.presentation.mapper

import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryItem
import com.marinj.shoppingwarfare.feature.history.presentation.model.UiHistoryItem
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

class HistoryItemToUiHistoryItemMapper @Inject constructor() {

    fun map(origin: HistoryItem): UiHistoryItem {
        return with(origin) {
            UiHistoryItem(
                id = id,
                receiptPath = receiptPath,
                date = formatDate(timestamp),
                cartName = cartName,
                historyCartItems = historyCartItems,
            )
        }
    }

    private fun formatDate(timestamp: Long): String =
        Instant.fromEpochMilliseconds(timestamp)
            .toLocalDateTime(TimeZone.UTC)
            .date
            .toString()
}
