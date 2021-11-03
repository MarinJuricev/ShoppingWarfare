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
import androidx.compose.ui.util.fastForEach
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.marinj.shoppingwarfare.core.navigation.NavigationEvent.Destination
import com.marinj.shoppingwarfare.core.navigation.NavigationEvent.NavigateBack
import com.marinj.shoppingwarfare.core.navigation.NavigationEvent.NavigateUp
import com.marinj.shoppingwarfare.core.navigation.Navigator
import com.marinj.shoppingwarfare.core.viewmodel.badge.BadgeEvent.StartObservingBadgesCount
import com.marinj.shoppingwarfare.core.viewmodel.badge.BadgeViewModel
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarViewModel
import com.marinj.shoppingwarfare.feature.cart.presentation.navigation.buildCartGraph
import com.marinj.shoppingwarfare.feature.category.list.presentation.navigation.CATEGORY_ROOT
import com.marinj.shoppingwarfare.feature.category.list.presentation.navigation.buildCategoryGraph
import com.marinj.shoppingwarfare.feature.history.presentation.navigation.buildHistoryGraph
import com.marinj.shoppingwarfare.feature.user.presentation.navigation.buildUserGraph
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ShoppingWarfareNavigation(
    navigator: Navigator,
    navController: NavHostController = rememberAnimatedNavController(),
    topBarViewModel: TopBarViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = Unit) {
        navigator.navigationEvent.collect { navigationEvent ->
            when (navigationEvent) {
                NavigateUp -> navController.navigateUp()
                NavigateBack -> navController.popBackStack()
                is Destination -> navController.navigate(
                    route = navigationEvent.destinations,
                    builder = navigationEvent.builder
                )
            }
        }
    }

    Scaffold(
        topBar = {
            ShoppingWarfareTopBar(topBarViewModel.viewState.collectAsState().value)
        },
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                BottomNavigationItem.navigationItems.fastForEach { screen ->
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
            startDestination = CATEGORY_ROOT,
            Modifier.padding(innerPadding)
        ) {
            buildCategoryGraph(navController, topBarViewModel::onEvent)
            buildCartGraph(topBarViewModel::onEvent)
            buildHistoryGraph(topBarViewModel::onEvent)
            buildUserGraph(topBarViewModel::onEvent)
        }
    }
}
