package com.marinj.shoppingwarfare.feature.cart.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.base.TIMEOUT_DELAY
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.CartTabPositionUpdated
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartListEvent.CartItemQuantityChanged
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartListEvent.DeleteCartItem
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartListEvent.ItemAddedToBasket
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartListEvent.OnGetCartItems
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartStatusEvent.CartNameUpdated
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartStatusEvent.CheckoutClicked
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartStatusEvent.ReceiptCaptureError
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartStatusEvent.ReceiptCaptureSuccess
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartTab
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewState
import com.marinj.shoppingwarfare.feature.cart.presentation.presenter.CartListPresenter
import com.marinj.shoppingwarfare.feature.cart.presentation.presenter.CartStatusPresenter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    cartListPresenterFactory: CartListPresenter.Factory,
    cartStatusPresenterFactory: CartStatusPresenter.Factory,
) : BaseViewModel<CartEvent>() {

    val cartListPresenter = cartListPresenterFactory.create(viewModelScope)
    val cartStatusPresenter = cartStatusPresenterFactory.create(viewModelScope)

    private val selectedTabPosition = MutableStateFlow(0)
    private val cartTabs = MutableStateFlow(
        listOf(
            CartTab(0, R.string.pending_tab),
            CartTab(1, R.string.approved_tab),
        ),
    )

    val viewState = combine(
        cartListPresenter.state,
        cartStatusPresenter.state,
        selectedTabPosition,
        cartTabs,
    ) { cartListState, cartStatusState, selectedTabPosition, cartTabs ->
        CartViewState(
            selectedTabPosition = selectedTabPosition,
            cartTabs = cartTabs,
            isLoading = cartListState.isLoading,
            uiCartItems = cartListState.uiCartItems,
            receiptStatus = cartStatusState.receiptStatus,
            cartName = cartStatusState.cartName,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_DELAY),
        initialValue = CartViewState(),
    )

    val viewEffect: Flow<CartViewEffect> = merge(
        cartListPresenter.viewEffect,
        cartStatusPresenter.viewEffect,
    )

    override fun onEvent(event: CartEvent) {
        when (event) {
            is CartTabPositionUpdated -> selectedTabPosition.update { event.updatedCartTabPosition }
            is CartItemQuantityChanged -> cartListPresenter.onEvent(event)
            is DeleteCartItem -> cartListPresenter.onEvent(event)
            is ItemAddedToBasket -> cartListPresenter.onEvent(event)
            is OnGetCartItems -> cartListPresenter.onEvent(event)
            is CartNameUpdated -> cartStatusPresenter.onEvent(event)
            is CheckoutClicked -> cartStatusPresenter.onEvent(event)
            is ReceiptCaptureError -> cartStatusPresenter.onEvent(event)
            is ReceiptCaptureSuccess -> cartStatusPresenter.onEvent(event)
        }
    }
}
