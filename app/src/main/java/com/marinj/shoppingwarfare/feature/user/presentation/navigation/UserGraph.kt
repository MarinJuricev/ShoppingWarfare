package com.marinj.shoppingwarfare.feature.user.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.marinj.shoppingwarfare.core.components.BottomNavigationItem
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.feature.user.presentation.UserPage

const val USER_ROOT = "userRoot"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.buildUserGraph(
    sendTopBar: (TopBarEvent) -> Unit,
) {
    navigation(
        startDestination = BottomNavigationItem.User.route,
        route = USER_ROOT,
    ) {
        composable(BottomNavigationItem.User.route) {
            UserPage(
                setupTopBar = sendTopBar,
            )
        }
    }
}
