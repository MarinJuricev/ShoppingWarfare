package com.marinj.shoppingwarfare.feature.categorydetail.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.Product
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEvent

@Composable
fun ProductList(
    products: List<Product>,
    onCategoryDetailEvent: (CategoryDetailEvent) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp)
    ) {
        items(products) { product ->
            Text(
                modifier = Modifier.padding(4.dp),
                text = product.name,
                style = MaterialTheme.typography.h4,
            )
            Divider(color = MaterialTheme.colors.onBackground, thickness = 1.dp)
        }
    }
}
