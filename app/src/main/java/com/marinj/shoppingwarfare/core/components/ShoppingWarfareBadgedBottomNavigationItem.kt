package com.marinj.shoppingwarfare.core.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RowScope.ShoppingWarfareBadgedBottomNavigationItem(
    screen: BottomNavigationItem,
    currentDestination: NavDestination?,
    badgeCount: Int,
    navController: NavHostController
) {
    BottomNavigationItem(
        icon = {
            BadgedBox(badge = {
                Badge {
                    Text(
                        text = badgeCount.toString(),
                        color = MaterialTheme.colors.surface,
                    )
                }
            }) {
                Icon(
                    painter = painterResource(screen.iconId),
                    contentDescription = screen.route
                )
            }
        },
        label = { Text(stringResource(id = screen.resourceId)) },
        selected = currentDestination?.hierarchy?.any {
            it.route?.contains(screen.route) ?: false
        } == true,
        alwaysShowLabel = false,
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
        }
    )
}
