package com.marinj.shoppingwarfare.core.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.core.viewmodel.TopBarViewState

@Composable
fun ShoppingWarfareTopBar(
    onActionClick: () -> Unit = {},
    iconContent: @Composable () -> Unit = {},
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


@ExperimentalAnimationApi
@Composable
fun ShoppingWarfareTopBar(topBarViewState: TopBarViewState) {
    val topBarSpring: FiniteAnimationSpec<IntSize> = spring(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessVeryLow,
    )

    AnimatedVisibility(
        visible = topBarViewState.isVisible,
        enter = expandVertically(
            animationSpec = topBarSpring
        ),
        exit = shrinkVertically(
            animationSpec = topBarSpring
        ),
    ) {
        TopAppBar {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterStart,
            ) {
                Column(
                    modifier = Modifier
                        .animateContentSize(
                            animationSpec = topBarSpring
                        )
                        .padding(8.dp)
                ) {
                    topBarViewState.title?.let {
                        Text(text = stringResource(id = it), style = MaterialTheme.typography.body1)
                    }
                    topBarViewState.subTitle?.let {
                        Text(stringResource(id = it), style = MaterialTheme.typography.body2)
                    }
                }

                androidx.compose.animation.AnimatedVisibility(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    visible = topBarViewState.icon != null,
                    enter = fadeIn() + slideInHorizontally(
                        initialOffsetX = { it / 2 },
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessVeryLow,
                            visibilityThreshold = IntOffset.VisibilityThreshold,
                        ),
                    ),
                    exit = fadeOut(),
                ) {
                    IconButton(
                        onClick = { topBarViewState.onActionClick?.invoke() }
                    ) {
                        topBarViewState.icon?.invoke()
                    }
                }
            }
        }
    }
}
