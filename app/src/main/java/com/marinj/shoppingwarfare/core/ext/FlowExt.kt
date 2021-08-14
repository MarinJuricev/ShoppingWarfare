package com.marinj.shoppingwarfare.core.ext

import kotlinx.coroutines.flow.MutableStateFlow

fun <T> MutableStateFlow<T>.safeUpdate(updatedValue: T) {
    this.compareAndSet(
        this.value,
        updatedValue,
    )
}