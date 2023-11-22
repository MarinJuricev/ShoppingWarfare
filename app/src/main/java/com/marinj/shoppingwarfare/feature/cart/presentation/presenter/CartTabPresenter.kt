package com.marinj.shoppingwarfare.feature.cart.presentation.presenter

import com.marinj.shoppingwarfare.core.base.TIMEOUT_DELAY
import com.marinj.shoppingwarfare.core.presenter.BasePresenter
import com.marinj.shoppingwarfare.feature.cart.presentation.mapper.UiCartTabItemsMapper
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartTabEvent
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartTabEvent.CartTabPositionUpdated
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartTabViewState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class CartTabPresenter @AssistedInject constructor(
    @Assisted private val coroutineScope: CoroutineScope,
    cartTabItemsMapper: UiCartTabItemsMapper,
) : BasePresenter<CartTabEvent> {
    @AssistedFactory
    interface Factory {
        fun create(coroutineScope: CoroutineScope): CartTabPresenter
    }


    private val selectedTabPosition = MutableStateFlow(0)
    private val cartTabItems = MutableStateFlow(cartTabItemsMapper.map())

    val state = combine(
        selectedTabPosition,
        cartTabItems,
    ) { selectedTabPosition, cartTabItems ->
        CartTabViewState(
            selectedIndex = selectedTabPosition,
            cartTabs = cartTabItems,
        )
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_DELAY),
        initialValue = CartTabViewState(),
    )

    override fun onEvent(event: CartTabEvent) {
        when (event) {
            is CartTabPositionUpdated -> selectedTabPosition.update { event.updatedCartTabPosition }
        }
    }
}