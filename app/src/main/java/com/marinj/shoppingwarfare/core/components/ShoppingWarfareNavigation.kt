package com.marinj.shoppingwarfare.core.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.marinj.shoppingwarfare.feature.cart.CartPage
import com.marinj.shoppingwarfare.feature.category.presentation.CategoryPage
import com.marinj.shoppingwarfare.feature.createcategory.presentation.CREATE_CATEGORY_ROUTE
import com.marinj.shoppingwarfare.feature.createcategory.presentation.CreateCategoryPage
import com.marinj.shoppingwarfare.feature.history.HistoryPage
import com.marinj.shoppingwarfare.feature.user.UserPage

@Composable
fun ShoppingWarfareNavigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                BottomNavigationItem.navigationItems.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(screen.iconId),
                                contentDescription = screen.route
                            )
                        },
                        label = { Text(stringResource(id = screen.resourceId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
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
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavigationItem.Category.route,
            Modifier.padding(innerPadding)
        ) {
            composable(BottomNavigationItem.Category.route) {
                EnterAnimation {
                    CategoryPage(
                        navigateToCreateCategory = { navController.navigate(CREATE_CATEGORY_ROUTE) }
                    )
                }
            }
            composable(CREATE_CATEGORY_ROUTE) {
                EnterAnimation {
                    CreateCategoryPage()
                }
            }
            composable(BottomNavigationItem.Cart.route) {
                EnterAnimation {
                    CartPage()
                }
            }
            composable(BottomNavigationItem.History.route) {
                EnterAnimation {
                    HistoryPage()
                }
            }
            composable(BottomNavigationItem.User.route) {
                EnterAnimation {
                    UserPage()
                }
            }
        }
    }
}
