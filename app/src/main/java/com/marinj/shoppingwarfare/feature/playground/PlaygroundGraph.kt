package com.marinj.shoppingwarfare.feature.playground

import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.marinj.shoppingwarfare.core.components.BottomNavigationItem
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent

const val PLAYGROUND_ROOT = "playgroundRoot"

fun NavGraphBuilder.buildPlaygroundGraph(
    sendTopBar: (TopBarEvent) -> Unit,
) {
    navigation(
        startDestination = BottomNavigationItem.Playground.route,
        route = PLAYGROUND_ROOT,
    ) {
        composable(BottomNavigationItem.Playground.route) {
            LaunchedEffect(key1 = Unit) {
                sendTopBar(TopBarEvent.UserTopBar())
            }
            Text("Playground")
        }
    }
}
