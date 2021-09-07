package com.marinj.shoppingwarfare.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ShoppingWarfareTopBar(
    onActionClick: () -> Unit,
    iconContent: @Composable () -> Unit,
) {
    TopAppBar {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End,
        ) {
            IconButton(
                onClick = onActionClick
            ) {
                iconContent()
            }
        }
    }
}
