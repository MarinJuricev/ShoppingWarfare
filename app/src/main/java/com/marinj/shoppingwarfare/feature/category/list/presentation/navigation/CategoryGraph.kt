package com.marinj.shoppingwarfare.feature.category.list.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.marinj.shoppingwarfare.core.components.BottomNavigationItem
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.CreateCategoryScreen
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.navigation.CreateCategoryDestination
import com.marinj.shoppingwarfare.feature.category.detail.presentation.CategoryDetailScreen
import com.marinj.shoppingwarfare.feature.category.detail.presentation.navigation.CATEGORY_ID_PARAM
import com.marinj.shoppingwarfare.feature.category.detail.presentation.navigation.CATEGORY_NAME_PARAM
import com.marinj.shoppingwarfare.feature.category.detail.presentation.navigation.CategoryDetailDestination
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
            route = CategoryDetailDestination.route(),
            arguments = CategoryDetailDestination.arguments,
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString(CATEGORY_ID_PARAM)
                ?: error("$CATEGORY_ID_PARAM was not provided to categoryDetailRoute")
            val categoryName = backStackEntry.arguments?.getString(CATEGORY_NAME_PARAM)
                ?: error("$CATEGORY_NAME_PARAM was not provided to categoryDetailRoute")

            CategoryDetailScreen(
                categoryId = categoryId,
                categoryName = categoryName,
                setupTopBar = sendTopBar,
            )
        }
    }
}
