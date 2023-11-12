package com.marinj.shoppingwarfare.core.presenter

interface BasePresenter<T> {

    fun onEvent(event: T)
}

private const val TIMEOUT_DELAY = 5_000L
