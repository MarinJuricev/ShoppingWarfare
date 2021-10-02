package com.marinj.shoppingwarfare.core.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.marinj.shoppingwarfare.core.viewmodel.badge.BadgeEvent.StartObservingBadgesCount
import com.marinj.shoppingwarfare.core.viewmodel.badge.BadgeViewModel
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarViewModel
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
                    if (screen == BottomNavigationItem.Cart) {
                        val badgeViewModel: BadgeViewModel = hiltViewModel()
                        val viewState by badgeViewModel.viewState.collectAsState()

                        LaunchedEffect(key1 = Unit) {
                            badgeViewModel.onEvent(StartObservingBadgesCount)
                        }

                        ShoppingWarfareBadgedBottomNavigationItem(
                            screen,
                            currentDestination,
                            viewState.cartBadgeCount,
                            navController,
                        )
                    } else {
                        ShoppingWarfareBottomNavigationItem(
                            screen,
                            currentDestination,
                            navController,
                        )
                    }
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
