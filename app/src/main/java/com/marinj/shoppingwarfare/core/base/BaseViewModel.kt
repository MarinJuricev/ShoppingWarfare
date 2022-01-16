package com.marinj.shoppingwarfare.core.base

import androidx.lifecycle.ViewModel

const val TIMEOUT_DELAY = 5_000L

abstract class BaseViewModel<E> : ViewModel() {
    abstract fun onEvent(event: E)
}
