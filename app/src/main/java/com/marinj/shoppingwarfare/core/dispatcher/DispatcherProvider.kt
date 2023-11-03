package com.marinj.shoppingwarfare.core.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

data class DispatcherProvider(
    val io: CoroutineDispatcher,
    val main: CoroutineDispatcher,
    val computation: CoroutineDispatcher,
)
