package com.marinj.shoppingwarfare.feature.playground

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.marinj.shoppingwarfare.core.components.BottomNavigationItem
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.ui.PrimaryElevatedButton

fun NavGraphBuilder.buildPlaygroundGraph(
    sendTopBar: (TopBarEvent) -> Unit,
) {
    navigation(
        startDestination = BottomNavigationItem.Playground.route,
        route = PLAYGROUND_ROOT,
    ) {
        composable(BottomNavigationItem.Playground.route) {
            LaunchedEffect(key1 = Unit) {
                sendTopBar(TopBarEvent.UserTopBar())
            }
//            LookaheadWithDisappearingMovableContentDemo()
//            LookaheadWithFlowRowDemo()
            BudgetExample()
        }
    }
}

private const val PLAYGROUND_ROOT = "playgroundRoot"

