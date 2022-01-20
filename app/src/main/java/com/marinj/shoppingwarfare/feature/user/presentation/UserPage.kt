package com.marinj.shoppingwarfare.feature.user.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.UserTopBar
import com.marinj.shoppingwarfare.feature.user.presentation.components.UserContent
import com.marinj.shoppingwarfare.feature.user.presentation.model.UserEvent
import com.marinj.shoppingwarfare.feature.user.presentation.viewmodel.UserViewModel

val UserEvents: ProvidableCompositionLocal<(UserEvent) -> Unit> = compositionLocalOf { {} }

@Composable
fun UserPage(
    userViewModel: UserViewModel = hiltViewModel(),
    setupTopBar: (TopBarEvent) -> Unit,
) {

    LaunchedEffect(key1 = Unit) {
        setupTopBar(UserTopBar())
    }

    CompositionLocalProvider(UserEvents provides userViewModel::onEvent) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            UserContent()
        }
    }
}
