package com.marinj.shoppingwarfare.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun SWScaffold(
    modifier: Modifier = Modifier,
    snackBarState: SnackBarState? = null,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }

    if (snackBarState != null) {
        LaunchedEffect(snackBarState) {
            snackBarHostState.showSnackbar(
                message = snackBarState.message,
                actionLabel = snackBarState.actionLabel,
            ).also { if (it == SnackbarResult.ActionPerformed) snackBarState.action() }
        }
    }

    Scaffold(
        modifier = modifier,
        content = content,
        topBar = topBar,
        snackbarHost = { if (snackBarState != null) SnackbarHost(snackBarHostState) },
        bottomBar = bottomBar,
    )
}

class SnackBarState(
    val message: String,
    val actionLabel: String,
    val action: () -> Unit = {},
)
