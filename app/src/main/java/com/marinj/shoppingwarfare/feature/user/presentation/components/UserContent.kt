package com.marinj.shoppingwarfare.feature.user.presentation.components

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.marinj.shoppingwarfare.feature.user.presentation.LocalUserEvents
import com.marinj.shoppingwarfare.feature.user.presentation.model.UserEvent

@Composable
fun UserContent() {
    val eventHandler = LocalUserEvents.current ?: error("LocalUserEvents wasn't provided")

    Button(onClick = { eventHandler(UserEvent.End) }) {
        Text("User")
    }
}
