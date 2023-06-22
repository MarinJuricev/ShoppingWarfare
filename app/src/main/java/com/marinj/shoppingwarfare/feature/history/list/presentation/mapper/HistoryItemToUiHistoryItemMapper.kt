package com.marinj.shoppingwarfare.feature.history.list.presentation.mapper

import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryItem
import com.marinj.shoppingwarfare.feature.history.list.presentation.model.UiHistoryItem
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@ViewModelScoped
class HistoryItemToUiHistoryItemMapper @Inject constructor() {

    fun map(origin: HistoryItem): UiHistoryItem {
        return with(origin) {
            UiHistoryItem(
                id = id.value,
                receiptPath = receiptPath?.value,
                date = formatDate(timestamp),
                cartName = cartName.value,
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
