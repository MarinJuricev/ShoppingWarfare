package com.marinj.shoppingwarfare.feature.category.list.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.marinj.shoppingwarfare.core.components.BottomNavigationItem
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.feature.category.createcategory.navigation.CreateCategoryDestination
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.CreateCategoryPage
import com.marinj.shoppingwarfare.feature.category.detail.navigation.CATEGORY_ID_PARAM
import com.marinj.shoppingwarfare.feature.category.detail.navigation.CATEGORY_NAME_PARAM
import com.marinj.shoppingwarfare.feature.category.detail.navigation.CategoryDetailDestination
import com.marinj.shoppingwarfare.feature.category.detail.presentation.CategoryDetailPage
import com.marinj.shoppingwarfare.feature.category.list.navigation.CategoryDestination
import com.marinj.shoppingwarfare.feature.category.list.presentation.CategoryPage

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
        composable(route = CategoryDestination.route()) {
            CategoryPage(
                setupTopBar = sendTopBar,
            )
        }
        composable(route = CreateCategoryDestination.route()) {
            CreateCategoryPage(
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

            CategoryDetailPage(
                categoryId = categoryId,
                categoryName = categoryName,
                setupTopBar = sendTopBar,
            )
        }
    }
}
