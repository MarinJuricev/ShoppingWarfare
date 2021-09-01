package com.marinj.shoppingwarfare.feature.categorydetail.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareLoadingIndicator
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEvent.GetCategoryItems
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.viewmodel.CategoryDetailViewModel

const val CATEGORY_ID = "categoryId"
const val CATEGORY_DETAIL_ROUTE = "categoryDetail/{$CATEGORY_ID}"

@Composable
fun CategoryDetailPage(
    categoryId: String,
    categoryDetailViewModel: CategoryDetailViewModel = hiltViewModel(),
) {
    val viewState by categoryDetailViewModel.viewState.collectAsState()

    LaunchedEffect(key1 = categoryId) {
        categoryDetailViewModel.onEvent(GetCategoryItems(categoryId))
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        if (viewState.isLoading) {
            ShoppingWarfareLoadingIndicator()
        }
    }
}
