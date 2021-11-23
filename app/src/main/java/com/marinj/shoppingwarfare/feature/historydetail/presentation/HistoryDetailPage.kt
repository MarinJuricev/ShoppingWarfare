package com.marinj.shoppingwarfare.feature.historydetail.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.HistoryDetailTopBar
import com.marinj.shoppingwarfare.feature.historydetail.presentation.viewmodel.HistoryDetailViewModel

@Composable
fun HistoryDetailPage(
    setupTopBar: (TopBarEvent) -> Unit,
    historyDetailViewModel: HistoryDetailViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = Unit) {
        setupTopBar(HistoryDetailTopBar())
    }
}
