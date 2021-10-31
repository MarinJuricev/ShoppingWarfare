package com.marinj.shoppingwarfare.feature.category.common

import androidx.navigation.NavBackStackEntry
import com.marinj.shoppingwarfare.core.navigation.NavigationAction

const val CATEGORY_ROUTE = "category"

const val CREATE_CATEGORY_ROUTE = "createCategory"

const val CATEGORY_ID = "categoryId"
const val CATEGORY_NAME = "categoryName"
const val CATEGORY_DETAIL_ROUTE = "categoryDetail/{$CATEGORY_ID}/{$CATEGORY_NAME}"

// TODO Introduce CategoryNavigationAction to separate actions and navigators ?

object CategoryAction : NavigationAction() {
    override val route: String = CATEGORY_ROUTE
    override val destination: String
        get() = route
}

object CreateCategoryAction : NavigationAction() {
    override val route: String = CREATE_CATEGORY_ROUTE
    override val destination: String
        get() = route
}

data class CategoryDetailAction(
    val categoryId: String,
    val categoryName: String,
) : NavigationAction() {
    override val route: String = CATEGORY_DETAIL_ROUTE
    override val destination: String = "categoryDetail/$categoryId/$categoryName"

    companion object {
        fun NavBackStackEntry.extractCategoryId(): String =
            arguments?.getString(CATEGORY_ID)
                ?: error("$CATEGORY_ID was not provided to categoryDetailRoute")

        fun NavBackStackEntry.extractCategoryName(): String =
            arguments?.getString(CATEGORY_NAME)
                ?: error("$CATEGORY_NAME was not provided to categoryDetailRoute")
    }
}
