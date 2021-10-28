package com.marinj.shoppingwarfare.feature.category.common

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.marinj.shoppingwarfare.core.navigation.NavigationAction
import com.marinj.shoppingwarfare.core.navigation.NavigationArgument

const val CATEGORY_ROUTE = "category"

const val CREATE_CATEGORY_ROUTE = "createCategory"

const val CATEGORY_ID = "categoryId"
const val CATEGORY_NAME = "categoryName"
const val CATEGORY_DETAIL_ROUTE = "categoryDetail/{$CATEGORY_ID}/{$CATEGORY_NAME}"

sealed class CategoryNavigationAction(
    destination: String,
    arguments: List<NamedNavArgument> = emptyList(),
) : NavigationAction(destination, arguments)

object Category : CategoryNavigationAction(destination = CATEGORY_ROUTE) {
    override fun generateDestination(
        navigationArgument: NavigationArgument?
    ): String = CATEGORY_ROUTE
}

object CreateCategory : CategoryNavigationAction(destination = CREATE_CATEGORY_ROUTE) {
    override fun generateDestination(
        navigationArgument: NavigationArgument?
    ): String = CREATE_CATEGORY_ROUTE
}

object CategoryDetail : CategoryNavigationAction(
    destination = CATEGORY_DETAIL_ROUTE,
    arguments = listOf(
        navArgument(CATEGORY_ID) { type = NavType.StringType },
        navArgument(CATEGORY_NAME) { type = NavType.StringType }
    )
) {
    fun NavBackStackEntry.extractCategoryId(): String =
        arguments?.getString(CATEGORY_ID) ?: error("$CATEGORY_ID was not provided to categoryDetailRoute")

    fun NavBackStackEntry.extractCategoryName(): String =
        arguments?.getString(CATEGORY_NAME) ?: error("$CATEGORY_NAME was not provided to categoryDetailRoute")

    override fun generateDestination(navigationArgument: NavigationArgument?): String {
        if (navigationArgument !is CategoryDetailNavigationArgument)
            error("Invalid navigationArgument provided expected CategoryDetailNavigationArgument, but got $navigationArgument")

        return "$CATEGORY_DETAIL_ROUTE/${navigationArgument.categoryId}/${navigationArgument.categoryName}"
    }
}

