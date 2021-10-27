package com.marinj.shoppingwarfare.core.navigation

import androidx.navigation.NamedNavArgument
import com.marinj.shoppingwarfare.feature.createcategory.presentation.CREATE_CATEGORY_ROUTE

sealed class NavigationAction(
    val destination: String,
    val arguments: List<NamedNavArgument> = emptyList()
)

object Category : NavigationAction(CREATE_CATEGORY_ROUTE)
object CreateCategory : NavigationAction(CREATE_CATEGORY_ROUTE)
