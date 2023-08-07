package com.marinj.shoppingwarfare.feature.category.list.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.marinj.shoppingwarfare.core.components.BottomNavigationItem
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.CreateCategoryScreen
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.navigation.CreateCategoryDestination
import com.marinj.shoppingwarfare.feature.category.detail.presentation.ProductScreen
import com.marinj.shoppingwarfare.feature.category.detail.presentation.navigation.CATEGORY_ID_PARAM
import com.marinj.shoppingwarfare.feature.category.detail.presentation.navigation.CATEGORY_NAME_PARAM
import com.marinj.shoppingwarfare.feature.category.detail.presentation.navigation.ProductDestination
import com.marinj.shoppingwarfare.feature.category.list.presentation.CategoryScreen

const val CATEGORY_ROOT = "categoryRoot"

fun NavGraphBuilder.buildCategoryGraph(
    navController: NavController,
    sendTopBar: (TopBarEvent) -> Unit,
) {
    navigation(
        startDestination = BottomNavigationItem.Category.route,
        route = CATEGORY_ROOT,
    ) {
        composable(route = CategoryDestination.route()) {
            CategoryScreen(
                setupTopBar = sendTopBar,
            )
        }
        composable(route = CreateCategoryDestination.route()) {
            CreateCategoryScreen(
                navigateBack = { navController.popBackStack() },
                setupTopBar = sendTopBar,
            )
        }
        composable(
            route = ProductDestination.route(),
            arguments = ProductDestination.arguments,
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString(CATEGORY_ID_PARAM)
                ?: error("$CATEGORY_ID_PARAM was not provided to categoryDetailRoute")
            val categoryName = backStackEntry.arguments?.getString(CATEGORY_NAME_PARAM)
                ?: error("$CATEGORY_NAME_PARAM was not provided to categoryDetailRoute")

            ProductScreen(
                categoryId = categoryId,
                categoryName = categoryName,
                setupTopBar = sendTopBar,
            )
        }
    }
}
