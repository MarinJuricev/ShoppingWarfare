package com.marinj.shoppingwarfare.feature.cart.presentation.presenter

import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.presenter.BasePresenter
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartTab
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartTabEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class CartTabPresenter @AssistedInject constructor(
    @Assisted private val coroutineScope: CoroutineScope,
    private val cartTabs: List<CartTab> = listOf(
        CartTab(0, R.string.pending_tab),
        CartTab(1, R.string.approved_tab),
    ),
) : BasePresenter<CartTabEvent> {

    private val selectedTabPosition = MutableStateFlow(0)

    @AssistedFactory
    interface Factory {
        fun create(coroutineScope: CoroutineScope): CartTabPresenter
    }

    override fun onEvent(event: CartTabEvent) {
        TODO("Not yet implemented")
    }
}