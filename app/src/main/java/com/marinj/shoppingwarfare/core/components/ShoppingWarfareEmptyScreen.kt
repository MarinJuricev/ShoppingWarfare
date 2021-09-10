package com.marinj.shoppingwarfare.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.R

@Composable
fun ShoppingWarfareEmptyScreen(message: String) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
            .padding(horizontal = 16.dp),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.shrug_emoji),
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
            )
        }
    }
}
