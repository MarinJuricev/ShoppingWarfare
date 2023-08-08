package com.marinj.shoppingwarfare.feature.category.createcategory.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.ui.TextBodyMedium

@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    title: String,
    onColorChanged: (Color) -> Unit,
    selectedColor: Color?,
    colors: List<Color>,
) {
    Column(modifier = modifier) {
        TextBodyMedium(title, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        LazyVerticalGrid(
            columns = GridCells.Adaptive(36.dp),
        ) {
            itemsIndexed(colors) { index, newColor ->
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(newColor)
                        .clickable { onColorChanged(colors[index]) },
                ) {
                    if (selectedColor == newColor) {
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
}
