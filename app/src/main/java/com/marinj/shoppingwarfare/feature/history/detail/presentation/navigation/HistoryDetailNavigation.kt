package com.marinj.shoppingwarfare.feature.history.detail.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.marinj.shoppingwarfare.core.navigation.NavigationDestination

const val HISTORY_ID_PARAM = "historyId"

private const val HISTORY_DETAIL_ROOT = "historyDetail"
private const val HISTORY_DETAIL_ROUTE =
    "$HISTORY_DETAIL_ROOT/{$HISTORY_ID_PARAM}"

object HistoryDetailNavigation : NavigationDestination {

    override fun route(): String = HISTORY_DETAIL_ROUTE

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(HISTORY_ID_PARAM) { type = NavType.StringType },
        )

    fun createHistoryDetailRoute(historyItemId: String) = "$HISTORY_DETAIL_ROOT/$historyItemId"
}
