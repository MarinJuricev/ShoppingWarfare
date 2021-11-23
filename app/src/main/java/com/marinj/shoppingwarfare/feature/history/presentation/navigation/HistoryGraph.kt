package com.marinj.shoppingwarfare.feature.history.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.feature.history.presentation.HistoryPage
import com.marinj.shoppingwarfare.feature.historydetail.presentation.HistoryDetailPage
import com.marinj.shoppingwarfare.feature.historydetail.presentation.navigation.HistoryDetailNavigation

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
            HistoryDetailPage(
                setupTopBar = sendTopBar,
            )
        }
    }
}
