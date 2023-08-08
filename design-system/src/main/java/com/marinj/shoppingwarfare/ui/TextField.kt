package com.marinj.shoppingwarfare.ui

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SWTextField(
    value: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
    onValueChange: (String) -> Unit,
    label: @Composable (() -> Unit)? = null,
) {
    TextField(
        value = value,
        modifier = modifier,
        singleLine = singleLine,
        onValueChange = onValueChange,
        label = label,
    )
}

@Preview(showBackground = true)
@Composable
fun SWTextFieldPreview() {
    var textState by remember { mutableStateOf("") }
    SWTheme {
        SWTextField(
            value = textState,
            onValueChange = { textState = it },
            label = { TextBodyMedium(text = "Label") },
        )
    }
}
