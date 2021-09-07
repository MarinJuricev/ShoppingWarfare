package com.marinj.shoppingwarfare.feature.categorydetail.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.marinj.shoppingwarfare.R.string
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareEmptyScreen
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareLoadingIndicator
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareTopBar
import com.marinj.shoppingwarfare.core.ext.expandOrCollapse
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.components.CreateCategoryProduct
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.components.ProductList
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEvent.OnGetCategoryProducts
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.viewmodel.CategoryDetailViewModel
import kotlinx.coroutines.launch

const val CATEGORY_ID = "categoryId"
const val CATEGORY_DETAIL_ROUTE = "categoryDetail/{$CATEGORY_ID}"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryDetailPage(
    categoryId: String,
    categoryDetailViewModel: CategoryDetailViewModel = hiltViewModel(),
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val viewState by categoryDetailViewModel.viewState.collectAsState()

    LaunchedEffect(key1 = categoryId) {
        categoryDetailViewModel.onEvent(OnGetCategoryProducts(categoryId))
    }

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            CreateCategoryProduct(
                categoryId = categoryId,
                onCategoryDetailEvent = { categoryDetailEvent ->
                    coroutineScope.launch {
                        bottomSheetScaffoldState.expandOrCollapse()
                        categoryDetailViewModel.onEvent(categoryDetailEvent)
                    }
                },
            )
        },
        topBar = {
            ShoppingWarfareTopBar(
                onActionClick = {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.expandOrCollapse()
                    }
                },
                iconContent = {
                    Icon(
                        imageVector = Filled.Add,
                        contentDescription = stringResource(id = string.create_category_item),
                        tint = Color.White,
                    )
                }
            )
        },
        sheetPeekHeight = 0.dp,
    ) {
        when {
            viewState.isLoading -> ShoppingWarfareLoadingIndicator()
            viewState.products.isEmpty() -> ShoppingWarfareEmptyScreen(
                message = stringResource(
                    string.empty_category_detail_message
                )
            )
            viewState.products.isNotEmpty() -> ProductList(
                viewState.products,
                categoryDetailViewModel::onEvent,
            )
        }
    }
}

