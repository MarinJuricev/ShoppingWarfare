package com.marinj.shoppingwarfare.feature.category.presentation.components

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

@Composable
fun GroceryCard(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = MaterialTheme.typography.body1.color,
    backGroundColor: Color = MaterialTheme.colors.primary,
) {
    Card(
        modifier = modifier,
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
