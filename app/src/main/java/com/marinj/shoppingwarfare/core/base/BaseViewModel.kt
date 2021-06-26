package com.marinj.shoppingwarfare.core.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<E> : ViewModel() {
    abstract fun onEvent(event: E)
}
