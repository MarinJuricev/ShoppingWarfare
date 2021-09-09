package com.marinj.shoppingwarfare.feature.categorydetail.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.Product
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEvent
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.model.CategoryDetailEvent.OnProductClicked

@Composable
fun ProductList(
    products: List<Product>,
    onCategoryDetailEvent: (CategoryDetailEvent) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp)
    ) {
        items(products) { product ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 8.dp),
            ) {
                Column(
                    modifier = Modifier
                        .clickable { onCategoryDetailEvent(OnProductClicked(product)) }
                        .padding(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.product_name),
                        style = MaterialTheme.typography.body2,
                        color = if (MaterialTheme.colors.isLight) Color.LightGray else Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.body1,
                    )
                }
            }
        }
    }
}
