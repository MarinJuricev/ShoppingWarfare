package com.marinj.shoppingwarfare.feature.playground

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.ui.PrimaryElevatedButton

@Composable
fun BudgetExample() {
    var isColumn: Boolean by remember { mutableStateOf(true) }

    LookaheadScope {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            val primaryComp = remember {
                movableContentOf {
                    PrimaryElevatedButton(modifier = Modifier.animatePosition(), text = "Hello", onClick = { /*TODO*/ })
                }
            }
            val secondaryComp = remember {
                movableContentOf {
                    PrimaryElevatedButton(modifier = Modifier.animatePosition(), text = "World", onClick = { /*TODO*/ })
                }
            }

            val box = remember {
                movableContentOf {
                    Box(
                        modifier = Modifier
                            .animatePosition()
                            .animateBounds(Modifier.size(if (isColumn) 50.dp else 100.dp))
                            .background(Color.Cyan),
                    )
                }
            }

            if (isColumn) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    primaryComp()
                    box()
                    secondaryComp()
                }
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    primaryComp()
                    secondaryComp()
                    box()
                }
            }

            PrimaryElevatedButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                text = "Toggle",
                onClick = { isColumn = !isColumn },
            )
        }
    }
}
