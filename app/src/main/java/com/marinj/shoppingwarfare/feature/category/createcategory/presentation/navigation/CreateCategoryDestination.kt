package com.marinj.shoppingwarfare.feature.category.createcategory.presentation.navigation

import com.marinj.shoppingwarfare.core.navigation.NavigationDestination

object CreateCategoryDestination : NavigationDestination {

    override fun route(): String = CREATE_CATEGORY_ROUTE

    private const val CREATE_CATEGORY_ROUTE = "createCategory"
}
