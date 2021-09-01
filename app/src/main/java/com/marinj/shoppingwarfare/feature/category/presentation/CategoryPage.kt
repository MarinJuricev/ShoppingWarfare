package com.marinj.shoppingwarfare.feature.category.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.feature.category.presentation.components.GroceryCard
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
            TopAppBar {
                Text(stringResource(id = R.string.app_name))
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToCreateCategory,
            ) {
                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(id = R.string.create_category),
                        tint = Color.White,
                    )
                }
            }
        }
    ) {
        // TODO: Implement empty screen
        LazyVerticalGrid(cells = GridCells.Fixed(2)) {
            items(viewState.categories) { uiCategory ->
                GroceryCard(
                    modifier = Modifier
                        .padding(16.dp)
                        .height(200.dp),
                    uiCategory = uiCategory,
                    backGroundColor = uiCategory.backgroundColor,
                    textColor = uiCategory.titleColor,
                    onCategoryEvent = categoryViewModel::onEvent,
                )
            }
        }
    }
}
