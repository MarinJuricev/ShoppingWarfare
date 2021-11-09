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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarViewState

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
                androidx.compose.animation.AnimatedVisibility(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    visible = topBarViewState.isSearchEnabled,
                    enter = fadeIn() + slideInHorizontally(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessVeryLow,
                            visibilityThreshold = IntOffset.VisibilityThreshold,
                        ),
                    ),
                    exit = fadeOut(),
                ) {
                    ShoppingWarfareSearchTopBar(
                        topBarViewState = topBarViewState,
                    )
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = topBarViewState.isSearchEnabled.not(),
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
                    ShoppingWarfareNonSearchTopBar(
                        topBarSpring = topBarSpring,
                        topBarViewState = topBarViewState
                    )
                }
            }
        }
    }
}

@Composable
fun ShoppingWarfareSearchTopBar(
    topBarViewState: TopBarViewState
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        AnimatedVisibility(
            visible = topBarViewState.isSearchEnabled,
            enter = fadeIn() + slideInHorizontally(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessVeryLow,
                    visibilityThreshold = IntOffset.VisibilityThreshold,
                ),
            ),
            exit = fadeOut(),
        ) {
            IconButton(
                modifier = Modifier.wrapContentWidth(),
                onClick = { topBarViewState.onActionClick?.invoke() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.search_history),
                    tint = Color.White,
                )
            }
        }
        topBarViewState.searchText?.let { searchText ->
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = searchText.value,
                onValueChange = topBarViewState.onTextChange,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White,
                    textColor = MaterialTheme.colors.onSurface,
                )
            )
        }
    }
}

@Composable
private fun ShoppingWarfareNonSearchTopBar(
    topBarSpring: FiniteAnimationSpec<IntSize>,
    topBarViewState: TopBarViewState
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(animationSpec = topBarSpring)
                .padding(8.dp)
        ) {
            topBarViewState.title?.let {
                Text(text = stringResource(id = it), style = MaterialTheme.typography.body1)
            }
            topBarViewState.subTitle?.let {
                Text(stringResource(id = it), style = MaterialTheme.typography.body2)
            }
        }
        AnimatedVisibility(
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
