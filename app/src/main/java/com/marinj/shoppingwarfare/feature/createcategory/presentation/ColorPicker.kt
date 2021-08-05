package com.marinj.shoppingwarfare.feature.createcategory.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColorPicker(modifier: Modifier = Modifier) {
    val listOfColors = listOf(
        Color.Black,
        Color.Blue,
        Color.Cyan,
        Color.DarkGray,
        Color.Gray,
        Color.Green,
        Color.LightGray,
        Color.Magenta,
        Color.Red,
    )

    LazyVerticalGrid(
        modifier = modifier,
        cells = GridCells.Adaptive(36.dp)
    ) {
        items(listOfColors) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(it)
            )
        }
    }
}