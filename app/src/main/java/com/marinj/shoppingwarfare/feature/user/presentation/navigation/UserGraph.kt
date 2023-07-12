package com.marinj.shoppingwarfare.feature.user.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.marinj.shoppingwarfare.core.components.BottomNavigationItem
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.feature.user.presentation.UserScreen

const val USER_ROOT = "userRoot"

fun NavGraphBuilder.buildUserGraph(
    sendTopBar: (TopBarEvent) -> Unit,
) {
    navigation(
        startDestination = BottomNavigationItem.User.route,
        route = USER_ROOT,
    ) {
        composable(BottomNavigationItem.User.route) {
            UserScreen(
                setupTopBar = sendTopBar,
            )
        }
    }
}
