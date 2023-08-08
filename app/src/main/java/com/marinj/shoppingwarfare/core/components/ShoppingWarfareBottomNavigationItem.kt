package com.marinj.shoppingwarfare.core.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.marinj.shoppingwarfare.ui.SWNavigationItem
import com.marinj.shoppingwarfare.ui.TextBodyMedium

@Composable
fun RowScope.ShoppingWarfareBottomNavigationItem(
    screen: BottomNavigationItem,
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    SWNavigationItem(
        icon = {
            Icon(
                painter = painterResource(screen.iconId),
                contentDescription = screen.route,
            )
        },
        label = { TextBodyMedium(stringResource(id = screen.resourceId)) },
        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
        onClick = {
            navController.navigate(screen.route) {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
        },
    )
}
