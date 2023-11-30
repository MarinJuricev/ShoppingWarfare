package com.marinj.shoppingwarfare.feature.playground

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Preview
@Composable
fun LookaheadWithDisappearingMovableContentDemo() {
    LookaheadScope {
        val isCompact by produceState(initialValue = false) {
            while (true) {
                delay(3000)
                value = !value
            }
        }
        Column {
            Box(
                Modifier
                    .padding(start = 50.dp, top = 200.dp, bottom = 100.dp)
            ) {
                val icon = remember {
                    movableContentOf<Boolean> {
                        MyIcon(it)
                    }
                }
                val title = remember {
                    movableContentOf<Boolean> {
                        Title(visible = it, Modifier.animatePosition())
                    }
                }
                val details = remember {
                    movableContentOf<Boolean> {
                        Details(visible = it)
                    }
                }

                Row(
                    Modifier
                        .background(Color.Yellow)
                        .animateContentSize(), verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isCompact) {
                        icon(true)
                        Column {
                            title(true)
                            details(true)
                        }
                    } else {
                        icon(false)
                        Column {
                            title(true)
                            details(false)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MyIcon(visible: Boolean, modifier: Modifier = Modifier) {
    AnimatedVisibility(
        visible,
        enter = fadeIn(),
        exit = fadeOut() + slideOutHorizontally { -it },
        modifier = modifier
    ) {
        Box(
            modifier
                .size(40.dp)
                .background(color = Color.Red, CircleShape)
        )
    }
}

@Composable
fun Title(visible: Boolean, modifier: Modifier = Modifier) {
    AnimatedVisibility(visible, enter = fadeIn(), exit = fadeOut(), modifier = modifier) {
        Text("Text", modifier, fontSize = 30.sp)
    }
}

@Composable
fun Details(visible: Boolean, modifier: Modifier = Modifier) {
    AnimatedVisibility(
        visible, enter = fadeIn(),
        exit = fadeOut() + slideOutVertically { it },
        modifier = modifier
    ) {
        Text("Detailed Text", fontSize = 18.sp)
    }
}

