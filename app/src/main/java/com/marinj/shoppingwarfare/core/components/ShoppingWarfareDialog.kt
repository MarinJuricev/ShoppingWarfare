package com.marinj.shoppingwarfare.core.components

import androidx.annotation.StringRes
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.marinj.shoppingwarfare.R

@Composable
fun ShoppingWarfareDialog(
    @StringRes title: Int,
    @StringRes body: Int,
    @StringRes confirmMessage: Int = R.string.ok,
    @StringRes dismissMessage: Int = R.string.dismiss,
    onConfirmClick: (() -> Unit)? = null,
    onDismissClick: (() -> Unit)? = null,
    onDismissed: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissed,
        title = {
            Text(text = stringResource(id = title))
        },
        text = {
            Text(stringResource(id = body))
        },
        confirmButton = {
            Button(
                onClick = { onConfirmClick?.invoke() }
            ) {
                Text(stringResource(id = confirmMessage))
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismissClick?.invoke() }
            ) {
                Text(stringResource(id = dismissMessage))
            }
        }
    )
}