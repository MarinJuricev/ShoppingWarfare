package com.marinj.shoppingwarfare.feature.history.detail.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareLoadingIndicator
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.HistoryDetailTopBar
import com.marinj.shoppingwarfare.feature.history.detail.presentation.components.HistoryDetailInfo
import com.marinj.shoppingwarfare.feature.history.detail.presentation.model.HistoryDetailEvent.OnGetHistoryDetail
import com.marinj.shoppingwarfare.feature.history.detail.presentation.model.HistoryDetailViewEffect.Error
import com.marinj.shoppingwarfare.feature.history.detail.presentation.viewmodel.HistoryDetailViewModel
import com.marinj.shoppingwarfare.feature.history.list.presentation.model.UiHistoryItem
import com.marinj.shoppingwarfare.ui.ShoppingWarfareEmptyScreen

@Composable
fun HistoryDetailScreen(
    historyItemId: String,
    setupTopBar: (TopBarEvent) -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    historyDetailViewModel: HistoryDetailViewModel = hiltViewModel(),
) {
    val viewState by historyDetailViewModel.viewState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        setupTopBar(HistoryDetailTopBar())
        historyDetailViewModel.onEvent(OnGetHistoryDetail(historyItemId))
    }

    LaunchedEffect(key1 = historyDetailViewModel.viewEffect) {
        historyDetailViewModel.viewEffect.collect { historyEffect ->
            when (historyEffect) {
                is Error -> scaffoldState.snackbarHostState.showSnackbar(historyEffect.errorMessage)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
    ) {
        when {
            viewState.isLoading -> ShoppingWarfareLoadingIndicator()
            viewState.uiHistoryItem == UiHistoryItem.DEFAULT ->
                ShoppingWarfareEmptyScreen(stringResource(R.string.history_detail_empty_message))
            else -> HistoryDetailInfo(viewState.uiHistoryItem)
        }
    }
}
