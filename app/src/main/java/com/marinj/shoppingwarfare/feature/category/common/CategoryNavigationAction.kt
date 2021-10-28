package com.marinj.shoppingwarfare.feature.category.common

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.marinj.shoppingwarfare.core.navigation.NavigationAction

const val CATEGORY_ID = "categoryId"
const val CATEGORY_NAME = "categoryName"
const val CATEGORY_DETAIL_ROUTE = "categoryDetail/{$CATEGORY_ID}/{$CATEGORY_NAME}"

sealed class CategoryNavigationAction(
    destination: String,
    arguments: List<NamedNavArgument> = emptyList()
) : NavigationAction(destination, arguments) {
    object Category : CategoryNavigationAction("category")
    object CreateCategory : CategoryNavigationAction("createCategory")
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
    }
}
