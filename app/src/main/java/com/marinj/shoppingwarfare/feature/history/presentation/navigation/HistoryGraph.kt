package com.marinj.shoppingwarfare.feature.history.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.marinj.shoppingwarfare.core.components.BottomNavigationItem
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.feature.history.HistoryPage

const val HISTORY_ROOT = "historyRoot"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.buildHistoryGraph(
    sendTopBar: (TopBarEvent) -> Unit,
) {
    navigation(
        startDestination = BottomNavigationItem.History.route,
        route = HISTORY_ROOT,
    ) {
        composable(BottomNavigationItem.History.route) {
            HistoryPage(
                setupTopBar = sendTopBar,
            )
        }
    }
}
