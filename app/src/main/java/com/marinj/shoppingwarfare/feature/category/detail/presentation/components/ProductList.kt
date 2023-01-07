package com.marinj.shoppingwarfare.feature.category.detail.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductEvent

@Composable
fun ProductList(
    products: List<Product>,
    onCategoryDetailEvent: (ProductEvent) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
    ) {
        items(products) { product ->
            ProductCard(
                product = product,
                onCategoryDetailEvent = onCategoryDetailEvent,
            )
        }
    }
}
