package com.marinj.shoppingwarfare.feature.playground

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.marinj.shoppingwarfare.core.components.BottomNavigationItem
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent

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
