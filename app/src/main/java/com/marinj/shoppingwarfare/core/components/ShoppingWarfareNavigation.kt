package com.marinj.shoppingwarfare.core.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.marinj.shoppingwarfare.core.viewmodel.TopBarViewModel
import com.marinj.shoppingwarfare.feature.cart.presentation.CartPage
import com.marinj.shoppingwarfare.feature.category.presentation.CategoryPage
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.CATEGORY_DETAIL_ROUTE
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.CATEGORY_ID
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.CATEGORY_NAME
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.CategoryDetailPage
import com.marinj.shoppingwarfare.feature.createcategory.presentation.CREATE_CATEGORY_ROUTE
import com.marinj.shoppingwarfare.feature.createcategory.presentation.CreateCategoryPage
import com.marinj.shoppingwarfare.feature.history.HistoryPage
import com.marinj.shoppingwarfare.feature.user.UserPage

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ShoppingWarfareNavigation(
    navController: NavHostController = rememberAnimatedNavController(),
    topBarViewModel: TopBarViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = {
            ShoppingWarfareTopBar(topBarViewModel.viewState.collectAsState().value)
        },
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
        AnimatedNavHost(
            navController = navController,
            startDestination = BottomNavigationItem.Category.route,
            Modifier.padding(innerPadding)
        ) {
            composable(BottomNavigationItem.Category.route) {
                CategoryPage(
                    navigateToCreateCategory = { navController.navigate(CREATE_CATEGORY_ROUTE) },
                    navigateToCategoryDetail = { categoryId, categoryName ->
                        navController.navigate("categoryDetail/$categoryId/$categoryName")
                    },
                    setupTopBar = topBarViewModel::onEvent,
                )
            }
            composable(CREATE_CATEGORY_ROUTE) {
                CreateCategoryPage(
                    navigateBack = { navController.popBackStack() },
                    setupTopBar = topBarViewModel::onEvent,
                )
            }
            composable(CATEGORY_DETAIL_ROUTE) { backStackEntry ->
                val categoryId = backStackEntry.arguments?.getString(CATEGORY_ID)
                    ?: throw Exception("$CATEGORY_ID was not provided to categoryDetailRoute")
                val categoryName = backStackEntry.arguments?.getString(CATEGORY_NAME)
                    ?: throw Exception("$CATEGORY_NAME was not provided to categoryDetailRoute")

                CategoryDetailPage(
                    categoryId = categoryId,
                    categoryName = categoryName,
                    setupTopBar = topBarViewModel::onEvent,
                )
            }
            composable(BottomNavigationItem.Cart.route) {
                CartPage(
                    setupTopBar = topBarViewModel::onEvent,
                )
            }
            composable(BottomNavigationItem.History.route) {
                HistoryPage(
                    setupTopBar = topBarViewModel::onEvent,
                )
            }
            composable(BottomNavigationItem.User.route) {
                UserPage(
                    setupTopBar = topBarViewModel::onEvent,
                )
            }
        }
    }
}
