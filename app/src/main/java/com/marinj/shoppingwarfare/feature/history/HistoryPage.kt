package com.marinj.shoppingwarfare.feature.history

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.marinj.shoppingwarfare.core.viewmodel.TopBarEvent

@Composable
fun HistoryPage(
    setupTopBar: (TopBarEvent) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        setupTopBar(TopBarEvent.HistoryTopBar())
    }

    Text("History")
}
