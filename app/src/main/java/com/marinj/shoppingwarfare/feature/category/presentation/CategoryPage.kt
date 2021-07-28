package com.marinj.shoppingwarfare.feature.category.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.feature.category.presentation.CategoryEvent.CreateCategory
import com.marinj.shoppingwarfare.feature.category.presentation.components.GroceryCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GroceryPage(
    categoryViewModel: CategoryViewModel = hiltViewModel(),
) {
    val viewState by categoryViewModel.categoryViewState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar {
                Text("AppName")
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { categoryViewModel.onEvent(CreateCategory) }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.create_category),
                    tint = Color.White
                )
            }
        }
    ) {
        LazyVerticalGrid(cells = GridCells.Fixed(2)) {
            items(12) {
                GroceryCard(
                    modifier = Modifier
                        .padding(16.dp)
                        .height(200.dp),
                    text = "Some",
                    imageId = R.drawable.category_icon,
                )
            }
        }
    }
}
