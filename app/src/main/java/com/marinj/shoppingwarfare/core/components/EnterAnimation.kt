package com.marinj.shoppingwarfare.core.components

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EnterAnimation(content: @Composable AnimatedVisibilityScope.() -> Unit) {
    AnimatedVisibility(
        visibleState = remember { MutableTransitionState(false).apply { targetState = true } },
        modifier = Modifier,
        enter = fadeIn(initialAlpha = 0.3f),
        exit = fadeOut(),
        content = content,
    )
}