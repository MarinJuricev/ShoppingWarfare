package com.marinj.shoppingwarfare.feature.categorydetail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.R.string
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.CategoryItem
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEvent
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEvent.OnCreateCategoryItem

@Composable
fun CreateCategoryItem(
    onCategoryDetailEvent: (CategoryDetailEvent) -> Unit,
) {
    var categoryItemName by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            value = categoryItemName,
            onValueChange = { updatedCategoryItemName ->
                categoryItemName = updatedCategoryItemName
            },
            label = { Text(stringResource(R.string.category_item_name)) },
        )
        OutlinedButton(
            modifier = Modifier.padding(8.dp),
            onClick = {
                onCategoryDetailEvent(OnCreateCategoryItem(categoryItemName))
            },
        ) {
            Icon(
                imageVector = Filled.Add,
                contentDescription = stringResource(id = string.create_category_item),
                tint = MaterialTheme.colors.primary,
            )
        }
    }
}

@Preview
@Composable
fun CreateCategoryItemPreview(){
    CreateCategoryItem({})
}