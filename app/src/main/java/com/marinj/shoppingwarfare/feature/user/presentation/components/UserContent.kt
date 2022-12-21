package com.marinj.shoppingwarfare.feature.user.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun UserContent(
    listState: LazyListState = rememberLazyListState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {
//    val eventHandler = LocalUserEvents.current ?: error("LocalUserEvents wasn't provided")

//    Button(onClick = { eventHandler(UserEvent.End) }) {
//        Text("User")
//    }

    val showScrollToTop by remember {
        derivedStateOf {
            Timber.d("derivedState: lambda")
            listState.firstVisibleItemIndex != 0
        }
    }

//    val showScrollToTop by derivedStateOf {
//        Timber.d("derivedState: lambda")
//        listState.firstVisibleItemIndex != 0
//    }

    Column(
        modifier = Modifier.animateContentSize(),
    ) {
        LazyColumn(
            modifier = Modifier.weight(if (showScrollToTop) 0.95f else 1f),
            state = listState,
        ) {
            Timber.d("derivedState: lazyColumn recomposed")
            items(25) { index ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = index.toString(),
                    )
                }
            }
        }
        ScrollToTopButton(showScrollToTop, coroutineScope, listState)
    }
}

@Composable
private fun ColumnScope.ScrollToTopButton(
    showScrollToTop: Boolean,
    coroutineScope: CoroutineScope,
    listState: LazyListState,
) {
    AnimatedVisibility(visible = showScrollToTop) {
        Timber.d("derivedState: actual button")
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {
                coroutineScope.launch {
                    listState.animateScrollToItem(0)
                }
            },
        ) {
            Text("Scroll to top")
        }
    }
}
