package com.marinj.shoppingwarfare.core.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection.EndToStart
import androidx.compose.material.DismissDirection.StartToEnd
import androidx.compose.material.DismissValue
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingWarfareSwipeToDismiss(
    onRightSwipe: () -> Unit,
    modifier: Modifier = Modifier,
    onLeftSwipe: (() -> Unit)? = null,
    foregroundContent: @Composable () -> Unit,
) {
    // https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary#swipetodismiss
    val dismissState = rememberDismissState(
        confirmStateChange = { dismissValue ->
            if (dismissValue == DismissValue.DismissedToStart) {
                onRightSwipe()
            } else if (dismissValue == DismissValue.DismissedToEnd) {
                onLeftSwipe?.invoke()
            }
            dismissValue != DismissValue.DismissedToEnd
        }
    )
    SwipeToDismiss(
        state = dismissState,
        modifier = Modifier.padding(vertical = 8.dp),
        directions = setOf(EndToStart),
        dismissThresholds = {
            FractionalThreshold(0.25f)
        },
        background = {
            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    DismissValue.Default -> Color.LightGray
                    DismissValue.DismissedToEnd -> Color.Green
                    DismissValue.DismissedToStart -> Color.Red
                }
            )
            val alignment = when (direction) {
                StartToEnd -> Alignment.CenterStart
                EndToStart -> Alignment.CenterEnd
            }
            val icon = when (direction) {
                StartToEnd -> Icons.Default.Done
                EndToStart -> Icons.Default.Delete
            }
            val scale by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
            )
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = alignment
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier.scale(scale)
                )
            }
        },
        dismissContent = {
            Card(
                modifier = modifier,
                elevation = animateDpAsState(
                    if (dismissState.dismissDirection != null) 4.dp else 2.dp
                ).value
            ) {
                foregroundContent()
            }
        }
    )
}
