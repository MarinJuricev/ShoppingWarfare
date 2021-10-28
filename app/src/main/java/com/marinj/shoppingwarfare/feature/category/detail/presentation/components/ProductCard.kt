package com.marinj.shoppingwarfare.feature.category.detail.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailEvent
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailEvent.OnProductClicked
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailEvent.OnProductDelete

@Composable
fun ProductCard(
    product: Product,
    onCategoryDetailEvent: (CategoryDetailEvent) -> Unit,
) {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(R.string.product_name),
                    style = MaterialTheme.typography.body2,
                    color = if (MaterialTheme.colors.isLight) Color.LightGray else Color.White
                )
                Icon(
                    modifier = Modifier
                        .clickable {
                            onCategoryDetailEvent(OnProductDelete(product))
                        }
                        .size(16.dp)
                        .align(Alignment.CenterVertically),
                    painter = painterResource(id = R.drawable.delete_icon),
                    tint = if (MaterialTheme.colors.isLight) Color.LightGray else Color.White,
                    contentDescription = stringResource(
                        R.string.delete_item,
                        product.name
                    )
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.name,
                style = MaterialTheme.typography.body1,
            )
        }
    }
}
