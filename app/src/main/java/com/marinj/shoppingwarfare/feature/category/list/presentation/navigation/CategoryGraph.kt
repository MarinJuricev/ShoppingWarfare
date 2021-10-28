package com.marinj.shoppingwarfare.feature.category.list.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.marinj.shoppingwarfare.core.components.BottomNavigationItem
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.feature.category.common.Category
import com.marinj.shoppingwarfare.feature.category.common.CategoryDetail
import com.marinj.shoppingwarfare.feature.category.common.CategoryDetail.extractCategoryId
import com.marinj.shoppingwarfare.feature.category.common.CategoryDetail.extractCategoryName
import com.marinj.shoppingwarfare.feature.category.common.CreateCategory
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.CREATE_CATEGORY_ROUTE
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
        composable(Category.destination) {
            CategoryPage(
                navigateToCreateCategory = { navController.navigate(CREATE_CATEGORY_ROUTE) },
                navigateToCategoryDetail = { categoryId, categoryName ->
                    navController.navigate("categoryDetail/$categoryId/$categoryName")
                },
                setupTopBar = sendTopBar,
            )
        }
        composable(CreateCategory.destination) {
            CreateCategoryPage(
                navigateBack = { navController.popBackStack() },
                setupTopBar = sendTopBar,
            )
        }
        composable(CategoryDetail.destination) { backStackEntry ->
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
