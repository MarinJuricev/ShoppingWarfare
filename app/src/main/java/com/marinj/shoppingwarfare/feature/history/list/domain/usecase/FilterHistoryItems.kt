package com.marinj.shoppingwarfare.feature.history.list.domain.usecase

import com.marinj.shoppingwarfare.feature.history.list.presentation.model.UiHistoryItem
import javax.inject.Inject

class FilterHistoryItems @Inject constructor() {

    operator fun invoke(
        listToFilter: List<UiHistoryItem>,
        searchQuery: String,
    ): List<UiHistoryItem> {
        return if (searchQuery.isBlank()) {
            listToFilter
        } else
            listToFilter.filter { uiHistoryItem ->
                uiHistoryItem.cartName.startsWith(
                    searchQuery,
                    ignoreCase = true
                )
            }
    }
}
