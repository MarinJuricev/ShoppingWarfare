package com.marinj.shoppingwarfare.feature.category.common

import com.marinj.shoppingwarfare.core.navigation.NavigationArgument

data class CategoryDetailNavigationArgument(
    val categoryId: String,
    val categoryName: String,
) : NavigationArgument
