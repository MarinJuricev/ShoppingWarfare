package com.marinj.shoppingwarfare.feature.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.marinj.shoppingwarfare.core.viewmodel.TopBarEvent

@Composable
fun HistoryPage(
    setupTopBar: (TopBarEvent) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        setupTopBar(TopBarEvent.HistoryTopBar())
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("History")
    }
}
