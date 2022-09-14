package com.marinj.shoppingwarfare.feature.category.list.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryEvent
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.UiCategory

@Composable
fun CategoryGrid(
    categoryList: List<UiCategory>,
    onCategoryEvent: (CategoryEvent) -> Unit,
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(categoryList) { uiCategory ->
            CategoryCard(
                modifier = Modifier
                    .padding(16.dp)
                    .height(200.dp),
                uiCategory = uiCategory,
                backGroundColor = uiCategory.backgroundColor,
                textColor = uiCategory.titleColor,
                onCategoryEvent = onCategoryEvent,
            )
        }
    }
}
