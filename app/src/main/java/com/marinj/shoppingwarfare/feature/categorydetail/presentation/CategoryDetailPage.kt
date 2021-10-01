package com.marinj.shoppingwarfare.feature.categorydetail.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.marinj.shoppingwarfare.R.string
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareEmptyScreen
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareLoadingIndicator
import com.marinj.shoppingwarfare.core.ext.expandOrCollapse
import com.marinj.shoppingwarfare.core.viewmodel.TopBarEvent
import com.marinj.shoppingwarfare.core.viewmodel.TopBarEvent.CategoryDetailTopBar
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.components.CreateCategoryProduct
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.components.ProductList
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEffect
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEffect.Error
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEffect.ProductDeleted
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEvent.OnGetCategoryProducts
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEvent.RestoreProductDeletion
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.viewmodel.CategoryDetailViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

const val CATEGORY_ID = "categoryId"
const val CATEGORY_NAME = "categoryName"
const val CATEGORY_DETAIL_ROUTE = "categoryDetail/{$CATEGORY_ID}/{$CATEGORY_NAME}"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryDetailPage(
    categoryId: String,
    categoryName: String,
    setupTopBar: (TopBarEvent) -> Unit,
    categoryDetailViewModel: CategoryDetailViewModel = hiltViewModel(),
    bottomSheetScaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
) {
    val coroutineScope = rememberCoroutineScope()
    val currentContext = LocalContext.current
    val viewState by categoryDetailViewModel.viewState.collectAsState()

    LaunchedEffect(key1 = categoryId) {
        setupTopBar(
            CategoryDetailTopBar(
                onActionClick = {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.expandOrCollapse()
                    }
                },
                icon = {
                    Icon(
                        imageVector = Filled.Add,
                        contentDescription = stringResource(id = string.create_category_item),
                        tint = Color.White,
                    )
                }
            )
        )
        categoryDetailViewModel.onEvent(OnGetCategoryProducts(categoryId))
    }

    LaunchedEffect(key1 = categoryDetailViewModel.viewEffect) {
        categoryDetailViewModel.viewEffect.collect { viewEffect ->
            when (viewEffect) {
                is Error -> bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                    message = viewEffect.errorMessage,
                    actionLabel = currentContext.getString(string.dismiss)
                )
                is ProductDeleted -> bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                    message = currentContext.getString(
                        string.deleted_item,
                        viewEffect.product.name,
                    ),
                    actionLabel = currentContext.getString(string.undo)
                )
                is CategoryDetailEffect.AddedToCart -> bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                    message = currentContext.getString(
                        string.cart_item_added,
                        viewEffect.product.name,
                    ),
                )
            }.also { snackBarResult ->
                if (viewEffect is ProductDeleted && snackBarResult == SnackbarResult.ActionPerformed) {
                    categoryDetailViewModel.onEvent(RestoreProductDeletion(viewEffect.product))
                }
            }
        }
    }

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            CreateCategoryProduct(
                categoryId = categoryId,
                categoryName = categoryName,
                onCategoryDetailEvent = { categoryDetailEvent ->
                    coroutineScope.launch {
                        bottomSheetScaffoldState.expandOrCollapse()
                        categoryDetailViewModel.onEvent(categoryDetailEvent)
                    }
                },
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
