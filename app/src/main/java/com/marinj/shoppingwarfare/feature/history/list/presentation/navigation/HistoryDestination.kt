package com.marinj.shoppingwarfare.feature.history.list.presentation.navigation

import com.marinj.shoppingwarfare.core.navigation.NavigationDestination

object HistoryDestination : NavigationDestination {

    override fun route(): String = HISTORY_ROUTE

    private const val HISTORY_ROUTE = "history"
}
