package com.marinj.shoppingwarfare.feature.category.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareEmptyScreen
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareLoadingIndicator
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareTopBar
import com.marinj.shoppingwarfare.feature.category.presentation.components.CategoryGrid
import com.marinj.shoppingwarfare.feature.category.presentation.model.CategoryEffect.DeleteCategory
import com.marinj.shoppingwarfare.feature.category.presentation.model.CategoryEffect.Error
import com.marinj.shoppingwarfare.feature.category.presentation.model.CategoryEffect.NavigateToCategoryDetail
import com.marinj.shoppingwarfare.feature.category.presentation.model.CategoryEvent.GetCategories
import com.marinj.shoppingwarfare.feature.category.presentation.model.CategoryEvent.UndoCategoryDeletion
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun CategoryPage(
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    navigateToCreateCategory: () -> Unit,
    navigateToCategoryDetail: (String) -> Unit,
) {
    val viewState by categoryViewModel.categoryViewState.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    LaunchedEffect(
        key1 = Unit,
        block = {
            categoryViewModel.onEvent(GetCategories)
        }
    )

    LaunchedEffect(key1 = categoryViewModel.categoryEffect) {
        categoryViewModel.categoryEffect.collect { categoryEffect ->
            when (categoryEffect) {
                is DeleteCategory -> scaffoldState.snackbarHostState.showSnackbar(
                    context.getString(
                        R.string.category_deleted,
                        categoryEffect.uiCategory.title
                    ),
                    actionLabel = context.getString(R.string.undo),
                ).also { snackBarResult ->
                    if (snackBarResult == SnackbarResult.ActionPerformed) {
                        categoryViewModel.onEvent(UndoCategoryDeletion(categoryEffect.uiCategory))
                    }
                }
                is Error -> scaffoldState.snackbarHostState.showSnackbar(categoryEffect.errorMessage)
                is NavigateToCategoryDetail -> navigateToCategoryDetail(categoryEffect.categoryId)
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ShoppingWarfareTopBar(
                onActionClick = navigateToCreateCategory,
                iconContent = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(id = R.string.create_category),
                        tint = Color.White,
                    )
                }
            )
        },
    ) {
        when {
            viewState.isLoading -> ShoppingWarfareLoadingIndicator()
            viewState.categories.isEmpty() -> ShoppingWarfareEmptyScreen(message = stringResource(R.string.empty_category_message))
            viewState.categories.isNotEmpty() -> CategoryGrid(
                categoryList = viewState.categories,
                onCategoryEvent = categoryViewModel::onEvent,
            )
        }
    }
}
