package com.marinj.shoppingwarfare.feature.category.list.presentation

import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareLoadingIndicator
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CategoryTopBar
import com.marinj.shoppingwarfare.feature.category.list.presentation.components.CategoryGrid
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryEvent
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryEvent.GetCategories
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryEvent.NavigateToCreateCategory
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryEvent.UndoCategoryDeletion
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryViewEffect.DeleteCategoryView
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryViewEffect.Error
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryViewState
import com.marinj.shoppingwarfare.feature.category.list.presentation.viewmodel.CategoryViewModel
import com.marinj.shoppingwarfare.ui.ShoppingWarfareEmptyScreen

@Composable
fun CategoryScreen(
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    setupTopBar: (TopBarEvent) -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    context: Context = LocalContext.current,
) {
    val viewState by categoryViewModel.viewState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        categoryViewModel.onEvent(GetCategories)
        setupTopBar(
            CategoryTopBar(
                onActionClick = {
                    categoryViewModel.onEvent(NavigateToCreateCategory)
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.create_category),
                    tint = Color.White,
                )
            },
        )
    }

    LaunchedEffect(key1 = categoryViewModel.viewEffect) {
        categoryViewModel.viewEffect.collect { categoryEffect ->
            when (categoryEffect) {
                is DeleteCategoryView -> scaffoldState.snackbarHostState.showSnackbar(
                    context.getString(
                        R.string.category_deleted,
                        categoryEffect.uiCategory.title,
                    ),
                    actionLabel = context.getString(R.string.undo),
                ).also { snackBarResult ->
                    if (snackBarResult == SnackbarResult.ActionPerformed) {
                        categoryViewModel.onEvent(UndoCategoryDeletion(categoryEffect.uiCategory))
                    }
                }

                is Error -> scaffoldState.snackbarHostState.showSnackbar(categoryEffect.errorMessage)
            }
        }
    }

    CategoryScreen(
        viewState = viewState,
        onEvent = categoryViewModel::onEvent,
        scaffoldState = scaffoldState,
    )
}

@Composable
private fun CategoryScreen(
    viewState: CategoryViewState,
    onEvent: (CategoryEvent) -> Unit,
    scaffoldState: ScaffoldState,
) {
    Scaffold(
        scaffoldState = scaffoldState,
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            AnimatedContent(
                targetState = viewState.categories.isEmpty(),
                label = "CategoryScreen",
            ) {
                when (it) {
                    true -> ShoppingWarfareEmptyScreen(message = stringResource(R.string.empty_category_message))
                    false -> CategoryGrid(
                        categoryList = viewState.categories,
                        onCategoryEvent = onEvent,
                    )
                }
            }

            if (viewState.isLoading) {
                ShoppingWarfareLoadingIndicator()
            }
        }
    }
}
