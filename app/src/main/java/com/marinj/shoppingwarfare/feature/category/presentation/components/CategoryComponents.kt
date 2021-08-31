package com.marinj.shoppingwarfare.feature.category.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.feature.category.presentation.model.CategoryEvent
import com.marinj.shoppingwarfare.feature.category.presentation.model.CategoryEvent.DeleteCategory
import com.marinj.shoppingwarfare.feature.category.presentation.model.CategoryEvent.NavigateToCategoryDetail

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GroceryCard(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = MaterialTheme.typography.body1.color,
    backGroundColor: Color = MaterialTheme.colors.primary,
    onCategoryEvent: (CategoryEvent) -> Unit,
) {
    Card(
        modifier = modifier
            .combinedClickable(
                onLongClick = { onCategoryEvent(DeleteCategory(text)) },
                onClick = { onCategoryEvent(NavigateToCategoryDetail(text)) },
            ),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = backGroundColor,
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = textColor,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h4,
            )
        }
    }
}
