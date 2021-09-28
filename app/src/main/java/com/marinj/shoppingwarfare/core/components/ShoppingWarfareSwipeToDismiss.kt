package com.marinj.shoppingwarfare.core.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShoppingWarfareSwipeToDismiss(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    foregroundContent: @Composable () -> Unit,
) {
    // https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary#swipetodismiss
    val dismissState = rememberDismissState(
        confirmStateChange = {
            onDismiss()
            it != DismissValue.DismissedToEnd
        }
    )
    SwipeToDismiss(
        state = dismissState,
        modifier = Modifier.padding(vertical = 4.dp),
        directions = setOf(DismissDirection.EndToStart),
        dismissThresholds = { direction ->
            FractionalThreshold(if (direction == DismissDirection.StartToEnd) 0.25f else 0.5f)
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
                DismissDirection.StartToEnd -> Alignment.CenterStart
                DismissDirection.EndToStart -> Alignment.CenterEnd
            }
            val icon = when (direction) {
                DismissDirection.StartToEnd -> Icons.Default.Done
                DismissDirection.EndToStart -> Icons.Default.Delete
            }
            val scale by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
            )
            val contentDescription = if (icon == Icons.Default.Delete) {
                stringResource(id = R.string.delete_item)
            } else {
                null
            }

            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = alignment
            ) {
                Icon(
                    icon,
                    contentDescription = contentDescription,
                    modifier = Modifier.scale(scale)
                )
            }
        },
        dismissContent = {
            Card(
                modifier = modifier,
                elevation = animateDpAsState(
                    if (dismissState.dismissDirection != null) 4.dp else 0.dp
                ).value
            ) {
                foregroundContent()
            }
        }
    )
}
