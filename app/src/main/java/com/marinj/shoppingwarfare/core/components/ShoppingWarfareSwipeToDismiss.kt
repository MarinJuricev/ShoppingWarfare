package com.marinj.shoppingwarfare.core.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection.EndToStart
import androidx.compose.material.DismissDirection.StartToEnd
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingWarfareSwipeToDismiss(
    onRightSwipe: () -> Unit,
    modifier: Modifier = Modifier,
    onLeftSwipe: (() -> Unit)? = null,
    leftIcon: ImageVector = Icons.Default.Done,
    rightIcon: ImageVector = Icons.Default.Delete,
    foregroundContent: @Composable () -> Unit,
) {
    // Not ideal we'd like to have this state hoisted, but the problem in a LazyColumn is
    // that the item gets recomposed right away after we change it and we don't get the "nice"
    // animation... so this is a design tradeoff for a "nice" animation.
    var swipedLeft by rememberSaveable { mutableStateOf(false) }

    // https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary#swipetodismiss
    val dismissState = rememberDismissState(
        confirmStateChange = { dismissValue ->
            if (dismissValue == DismissValue.DismissedToStart) {
                onRightSwipe()
            } else if (dismissValue == DismissValue.DismissedToEnd) {
                swipedLeft = !swipedLeft
                onLeftSwipe?.invoke()
            }
            dismissValue != DismissValue.DismissedToEnd
        },
    )
    SwipeToDismiss(
        state = dismissState,
        modifier = Modifier.padding(vertical = 8.dp),
        directions = setOf(StartToEnd, EndToStart),
        dismissThresholds = {
            FractionalThreshold(0.25f)
        },
        background = {
            DismissBackground(dismissState, leftIcon, rightIcon)
        },
        dismissContent = {
            DismissContent(swipedLeft, dismissState, modifier, foregroundContent)
        },
    )
}

@Composable
private fun DismissBackground(
    dismissState: DismissState,
    leftIcon: ImageVector,
    rightIcon: ImageVector,
) {
    val direction = dismissState.dismissDirection ?: return
    val color by animateColorAsState(
        when (dismissState.targetValue) {
            DismissValue.Default -> Color.LightGray
            DismissValue.DismissedToEnd -> Color.Green
            DismissValue.DismissedToStart -> Color.Red
        },
    )
    val alignment = when (direction) {
        StartToEnd -> Alignment.CenterStart
        EndToStart -> Alignment.CenterEnd
    }
    val icon = when (direction) {
        StartToEnd -> leftIcon
        EndToStart -> rightIcon
    }
    val scale by animateFloatAsState(
        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f,
    )
    Box(
        Modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = 20.dp),
        contentAlignment = alignment,
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.scale(scale),
        )
    }
}

@Composable
private fun DismissContent(
    swipedLeft: Boolean,
    dismissState: DismissState,
    modifier: Modifier,
    foregroundContent: @Composable () -> Unit,
) {
    val backgroundColor by animateColorAsState(
        when (swipedLeft && dismissState.targetValue == DismissValue.Default) {
            true -> Color.Green
            false -> MaterialTheme.colors.surface
        },
        animationSpec = SpringSpec(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow,
        ),
    )

    Card(
        modifier = modifier,
        elevation = animateDpAsState(
            if (dismissState.dismissDirection != null) 4.dp else 2.dp,
        ).value,
        backgroundColor = backgroundColor,
    ) {
        foregroundContent()
    }
}
