package com.marinj.shoppingwarfare.feature.category.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.marinj.shoppingwarfare.core.components.BottomNavigationItem
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.feature.category.presentation.CategoryPage
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.CATEGORY_DETAIL_ROUTE
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.CATEGORY_ID
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.CATEGORY_NAME
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.CategoryDetailPage
import com.marinj.shoppingwarfare.feature.createcategory.presentation.CREATE_CATEGORY_ROUTE
import com.marinj.shoppingwarfare.feature.createcategory.presentation.CreateCategoryPage

const val CATEGORY_ROOT = "categoryRoot"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.buildCategoryGraph(
    navController: NavController,
    sendTopBar: (TopBarEvent) -> Unit,
) {
    navigation(
        startDestination = BottomNavigationItem.Category.route,
        route = CATEGORY_ROOT,
    ) {
        composable(BottomNavigationItem.Category.route) {
            CategoryPage(
                navigateToCreateCategory = { navController.navigate(CREATE_CATEGORY_ROUTE) },
                navigateToCategoryDetail = { categoryId, categoryName ->
                    navController.navigate("categoryDetail/$categoryId/$categoryName")
                },
                setupTopBar = sendTopBar,
            )
        }
        composable(CREATE_CATEGORY_ROUTE) {
            CreateCategoryPage(
                navigateBack = { navController.popBackStack() },
                setupTopBar = sendTopBar,
            )
        }
        composable(CATEGORY_DETAIL_ROUTE) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString(CATEGORY_ID)
                ?: error("$CATEGORY_ID was not provided to categoryDetailRoute")
            val categoryName = backStackEntry.arguments?.getString(CATEGORY_NAME)
                ?: error("$CATEGORY_NAME was not provided to categoryDetailRoute")

            CategoryDetailPage(
                categoryId = categoryId,
                categoryName = categoryName,
                setupTopBar = sendTopBar,
            )
        }
    }
}
