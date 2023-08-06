package com.marinj.shoppingwarfare.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.designsystem.R

@Composable
fun ShoppingWarfareEmptyScreen(
    message: String,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    SWCard(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize()
            .padding(horizontal = 16.dp),
        containerColor = containerColor,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp),
        ) {
            TextDisplayMedium(
                text = stringResource(id = R.string.shrug_emoji),
                textAlign = TextAlign.Center,
                color = textColor,
            )
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            TextBodyLarge(
                text = message,
                textAlign = TextAlign.Center,
                color = textColor,
            )
        }
    }
}

@Preview
@Composable
fun ShoppingWarfareEmptyScreenPreview() {
    ShoppingWarfareEmptyScreen(
        message = "No items found",
    )
}