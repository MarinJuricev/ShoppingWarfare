package com.marinj.shoppingwarfare.feature.history.list.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.feature.history.detail.presentation.HistoryDetailScreen
import com.marinj.shoppingwarfare.feature.history.detail.presentation.navigation.HISTORY_ID_PARAM
import com.marinj.shoppingwarfare.feature.history.detail.presentation.navigation.HistoryDetailNavigation
import com.marinj.shoppingwarfare.feature.history.list.presentation.HistoryScreen

const val HISTORY_ROOT = "historyRoot"

fun NavGraphBuilder.buildHistoryGraph(
    sendTopBar: (TopBarEvent) -> Unit,
) {
    navigation(
        startDestination = HistoryDestination.route(),
        route = HISTORY_ROOT,
    ) {
        composable(route = HistoryDestination.route()) {
            HistoryScreen(
                setupTopBar = sendTopBar,
            )
        }
        composable(
            route = HistoryDetailNavigation.route(),
            arguments = HistoryDetailNavigation.arguments,
        ) { backStackEntry ->
            val historyItemId = backStackEntry.arguments?.getString(HISTORY_ID_PARAM)
                ?: error("$HISTORY_ID_PARAM was not provided to historyDetailRoute")

            HistoryDetailScreen(
                historyItemId = historyItemId,
                setupTopBar = sendTopBar,
            )
        }
    }
}
