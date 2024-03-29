package com.marinj.shoppingwarfare.feature.category.detail.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.marinj.shoppingwarfare.core.navigation.NavigationDestination

const val CATEGORY_ID_PARAM = "categoryId"
const val CATEGORY_NAME_PARAM = "categoryName"

private const val CATEGORY_DETAIL_ROOT = "categoryDetail"
private const val CATEGORY_DETAIL_ROUTE =
    "$CATEGORY_DETAIL_ROOT/{$CATEGORY_ID_PARAM}/{$CATEGORY_NAME_PARAM}"

object ProductDestination : NavigationDestination {

    override fun route(): String = CATEGORY_DETAIL_ROUTE

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(CATEGORY_ID_PARAM) { type = NavType.StringType },
            navArgument(CATEGORY_NAME_PARAM) { type = NavType.StringType },
        )

    fun createCategoryDetailRoute(
        categoryId: String,
        categoryName: String,
    ) = "$CATEGORY_DETAIL_ROOT/$categoryId/$categoryName"
}
