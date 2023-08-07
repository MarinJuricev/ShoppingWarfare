package com.marinj.shoppingwarfare.ui

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SWCard(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier,
        content = content,
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
        ),
    )
}

@Preview
@Composable
fun SWCardPreview() {
    SWCard {
        TextBodyLarge(
            modifier = Modifier.padding(8.dp),
            text = "Card Preview",
        )
    }
}
