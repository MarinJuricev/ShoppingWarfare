package com.marinj.shoppingwarfare.core.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.viewmodel.topbar.NoSearchBarTopBarViewState
import com.marinj.shoppingwarfare.core.viewmodel.topbar.SearchTopBarViewState
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarViewState
import com.marinj.shoppingwarfare.ui.SWTopAppBar
import com.marinj.shoppingwarfare.ui.TextBodyLarge
import com.marinj.shoppingwarfare.ui.TextBodyMedium

@ExperimentalAnimationApi
@Composable
fun ShoppingWarfareTopBar(topBarViewState: TopBarViewState) {
    val topBarSpring: FiniteAnimationSpec<IntSize> = spring(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessVeryLow,
    )

    AnimatedVisibility(
        visible = topBarViewState.isVisible,
        enter = expandVertically(animationSpec = topBarSpring),
        exit = shrinkVertically(animationSpec = topBarSpring),
    ) {
        SWTopAppBar(
            title = {
                when (topBarViewState) {
                    is NoSearchBarTopBarViewState -> NoSearchBarTopBarTitle(topBarSpring, topBarViewState)
                    is SearchTopBarViewState -> SearchBarTopBarTitle(topBarViewState)
                }
            },
            actions = {
                AnimatedContent(
                    targetState = topBarViewState,
                    transitionSpec = {
                        (
                            fadeIn(animationSpec = tween(1_000, delayMillis = 90)) +
                                scaleIn(initialScale = 0.92f, animationSpec = tween(1_000, delayMillis = 90))
                            )
                            .togetherWith(fadeOut(animationSpec = tween(90)))
                    },
                    label = "topBarActions",
                ) { targetState ->
                    when (targetState) {
                        is NoSearchBarTopBarViewState -> {
                            IconButton(
                                onClick = { targetState.onActionClick?.invoke() },
                            ) {
                                targetState.icon?.invoke()
                            }
                        }

                        is SearchTopBarViewState -> targetState.searchText?.let { searchText ->
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(0.8f),
                                value = searchText(),
                                singleLine = true,
                                placeholder = {
                                    TextBodyMedium(
                                        text = stringResource(id = R.string.history_search),
                                        color = Color.Gray,
                                    )
                                },
                                onValueChange = targetState.onTextChange,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    backgroundColor = Color.White,
                                    textColor = MaterialTheme.colors.onSurface,
                                ),
                            )
                        }
                    }
                }
            },
        )
    }
}

@Composable
private fun SearchBarTopBarTitle(topBarViewState: SearchTopBarViewState) {
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
            onClick = { topBarViewState.onActionClick?.invoke() },
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = stringResource(R.string.search_history),
                tint = Color.White,
            )
        }
    }
}

@Composable
private fun NoSearchBarTopBarTitle(
    topBarSpring: FiniteAnimationSpec<IntSize>,
    topBarViewState: NoSearchBarTopBarViewState,
) {
    Column(
        modifier = Modifier
            .animateContentSize(animationSpec = topBarSpring)
            .padding(8.dp),
    ) {
        topBarViewState.title?.let {
            TextBodyLarge(
                text = stringResource(id = it),
                color = MaterialTheme.colors.onPrimary,
            )
        }
        topBarViewState.subTitle?.let {
            TextBodyMedium(
                text = stringResource(id = it),
                color = MaterialTheme.colors.onPrimary,
            )
        }
    }
}
