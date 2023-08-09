package com.marinj.shoppingwarfare.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.marinj.shoppingwarfare.designsystem.R

@Composable
fun BackIcon(
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.surface,
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(R.string.navigate_back),
            tint = tint,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BackIconPreview() {
    BackIcon {
        // No-op
    }
}