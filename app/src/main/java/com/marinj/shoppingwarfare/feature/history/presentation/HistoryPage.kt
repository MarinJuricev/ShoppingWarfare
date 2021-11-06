package com.marinj.shoppingwarfare.feature.history.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.HistoryTopBar
import com.marinj.shoppingwarfare.feature.history.presentation.model.HistoryEvent.OnGetHistoryItems
import com.marinj.shoppingwarfare.feature.history.presentation.viewmodel.HistoryViewModel

@Composable
fun HistoryPage(
    setupTopBar: (TopBarEvent) -> Unit,
    historyViewModel: HistoryViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        historyViewModel.onEvent(OnGetHistoryItems)
        setupTopBar(HistoryTopBar())
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("History")
    }
}
