package com.marinj.shoppingwarfare.feature.category.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.feature.category.presentation.model.CategoryEvent
import com.marinj.shoppingwarfare.feature.category.presentation.model.CategoryEvent.DeleteCategory
import com.marinj.shoppingwarfare.feature.category.presentation.model.CategoryEvent.NavigateToCategoryDetail
import com.marinj.shoppingwarfare.feature.category.presentation.model.UiCategory

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    uiCategory: UiCategory,
    textColor: Color = MaterialTheme.typography.body1.color,
    backGroundColor: Color = MaterialTheme.colors.primary,
    onCategoryEvent: (CategoryEvent) -> Unit,
) {
    Card(
        modifier = modifier
            .clickable {
                onCategoryEvent(NavigateToCategoryDetail(uiCategory.id))
            },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = backGroundColor,
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopEnd)
                    .clickable { onCategoryEvent(DeleteCategory(uiCategory)) },
                painter = painterResource(id = R.drawable.delete_icon),
                tint = if (MaterialTheme.colors.isLight) Color.LightGray else Color.White,
                contentDescription = stringResource(
                    R.string.deleted_item,
                    uiCategory.title,
                )
            )
            Text(
                text = uiCategory.title,
                color = textColor,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h4,
            )
        }
    }
}
