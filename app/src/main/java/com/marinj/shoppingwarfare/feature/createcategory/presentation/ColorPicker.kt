package com.marinj.shoppingwarfare.feature.createcategory.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf( //TODO Connect to some colors data
        Color.Blue,
        Color.Cyan,
        Color.DarkGray,
        Color.Gray,
        Color.Green,
        Color.LightGray,
        Color.Magenta,
        Color.Red,
    ),
) {
    var selectedColorPosition by remember { mutableStateOf(0) }

    LazyVerticalGrid(
        modifier = modifier,
        cells = GridCells.Adaptive(36.dp)
    ) {
        itemsIndexed(colors) { index, color ->
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(color)
                    .clickable { selectedColorPosition = index }
            ) {
                if (index == selectedColorPosition) {
                    Image(
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.Center),
                        painter = painterResource(id = R.drawable.check_icon),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}