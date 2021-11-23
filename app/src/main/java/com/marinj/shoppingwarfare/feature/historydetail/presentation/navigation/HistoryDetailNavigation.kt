package com.marinj.shoppingwarfare.feature.historydetail.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.marinj.shoppingwarfare.core.navigation.NavigationDestination

const val HISTORY_ID_PARAM = "historyId"
const val HISTORY_NAME_PARAM = "historyName"

private const val HISTORY_DETAIL_ROOT = "historyDetail"
private const val HISTORY_DETAIL_ROUTE =
    "$HISTORY_DETAIL_ROOT/{$HISTORY_ID_PARAM}/{$HISTORY_NAME_PARAM}"

object HistoryDetailNavigation : NavigationDestination {

    override fun route(): String = HISTORY_DETAIL_ROUTE

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(HISTORY_ID_PARAM) { type = NavType.StringType },
            navArgument(HISTORY_NAME_PARAM) { type = NavType.StringType },
        )

    fun createHistoryDetailRoute(
        historyItemId: String,
        historyName: String
    ) = "$HISTORY_DETAIL_ROOT/$historyItemId/$historyName"
}
