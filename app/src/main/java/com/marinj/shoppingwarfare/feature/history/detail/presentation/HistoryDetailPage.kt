package com.marinj.shoppingwarfare.feature.history.detail.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.HistoryDetailTopBar
import com.marinj.shoppingwarfare.feature.history.detail.presentation.model.HistoryDetailEvent.OnGetHistoryDetail
import com.marinj.shoppingwarfare.feature.history.detail.presentation.viewmodel.HistoryDetailViewModel

@Composable
fun HistoryDetailPage(
    historyItemId: String,
    setupTopBar: (TopBarEvent) -> Unit,
    historyDetailViewModel: HistoryDetailViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = Unit) {
        setupTopBar(HistoryDetailTopBar())
        historyDetailViewModel.onEvent(OnGetHistoryDetail(historyItemId))
    }
}
