package com.marinj.shoppingwarfare.feature.history.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.feature.history.presentation.HistoryPage

const val HISTORY_ROOT = "historyRoot"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.buildHistoryGraph(
    sendTopBar: (TopBarEvent) -> Unit,
) {
    navigation(
        startDestination = HistoryDestination.route(),
        route = HISTORY_ROOT,
    ) {
        composable(HistoryDestination.route()) {
            HistoryPage(
                setupTopBar = sendTopBar,
            )
        }
        composable(HistoryDetailNavigation.route()) {
            Text("History detail")
        }
    }
}
