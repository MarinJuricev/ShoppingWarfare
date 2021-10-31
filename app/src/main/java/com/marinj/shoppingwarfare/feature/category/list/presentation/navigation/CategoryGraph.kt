package com.marinj.shoppingwarfare.feature.category.list.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.marinj.shoppingwarfare.core.components.BottomNavigationItem
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.feature.category.common.CATEGORY_DETAIL_ROUTE
import com.marinj.shoppingwarfare.feature.category.common.CategoryAction
import com.marinj.shoppingwarfare.feature.category.common.CategoryDetailAction.Companion.extractCategoryId
import com.marinj.shoppingwarfare.feature.category.common.CategoryDetailAction.Companion.extractCategoryName
import com.marinj.shoppingwarfare.feature.category.common.CreateCategoryAction
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.CreateCategoryPage
import com.marinj.shoppingwarfare.feature.category.detail.presentation.CategoryDetailPage
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
        composable(CategoryAction.route) {
            CategoryPage(
                setupTopBar = sendTopBar,
            )
        }
        composable(CreateCategoryAction.route) {
            CreateCategoryPage(
                navigateBack = { navController.popBackStack() },
                setupTopBar = sendTopBar,
            )
        }
        composable(CATEGORY_DETAIL_ROUTE) { backStackEntry ->
            val categoryId = backStackEntry.extractCategoryId()
            val categoryName = backStackEntry.extractCategoryName()

            CategoryDetailPage(
                categoryId = categoryId,
                categoryName = categoryName,
                setupTopBar = sendTopBar,
            )
        }
    }
}
