package com.marinj.shoppingwarfare.feature.category.list.presentation.components

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
import com.marinj.shoppingwarfare.core.theme.textColor
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryEvent
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryEvent.DeleteCategory
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryEvent.NavigateToCategoryDetail
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.UiCategory

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
                onCategoryEvent(NavigateToCategoryDetail(uiCategory.id, uiCategory.title))
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
                tint = MaterialTheme.textColor(),
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
