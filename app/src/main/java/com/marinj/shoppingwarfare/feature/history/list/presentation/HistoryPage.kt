package com.marinj.shoppingwarfare.feature.history.list.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareEmptyScreen
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareLoadingIndicator
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.HistoryTopBar
import com.marinj.shoppingwarfare.feature.history.list.presentation.components.HistoryList
import com.marinj.shoppingwarfare.feature.history.list.presentation.model.HistoryEvent.OnGetHistoryItems
import com.marinj.shoppingwarfare.feature.history.list.presentation.model.HistoryEvent.OnSearchTriggered
import com.marinj.shoppingwarfare.feature.history.list.presentation.model.HistoryEvent.OnSearchUpdated
import com.marinj.shoppingwarfare.feature.history.list.presentation.viewmodel.HistoryViewModel

@Composable
fun HistoryScreen(
    setupTopBar: (TopBarEvent) -> Unit,
    historyViewModel: HistoryViewModel = hiltViewModel()
) {
    val viewState by historyViewModel.viewState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        historyViewModel.onEvent(OnGetHistoryItems)

        setupTopBar(
            HistoryTopBar(
                onTextChange = { newHistoryText ->
                    historyViewModel.onEvent(OnSearchUpdated(newHistoryText))
                },
                searchTextUpdated = { viewState.searchText },
            ) {
                historyViewModel.onEvent(OnSearchTriggered)
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                viewState.isLoading -> ShoppingWarfareLoadingIndicator()
                viewState.historyItems.isEmpty() -> ShoppingWarfareEmptyScreen(
                    message = stringResource(R.string.empty_history_message)
                )
                viewState.historyItems.isNotEmpty() -> HistoryList(
                    historyItems = viewState.historyItems,
                    onHistoryEvent = historyViewModel::onEvent
                )
            }
        }
    }
}
