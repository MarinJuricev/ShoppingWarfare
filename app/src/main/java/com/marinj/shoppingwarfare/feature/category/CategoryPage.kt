package com.marinj.shoppingwarfare.feature.category

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.feature.category.components.GroceryCard

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
        }
    ) {
        LazyVerticalGrid(cells = GridCells.Fixed(2)) {
            items(6) {
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
