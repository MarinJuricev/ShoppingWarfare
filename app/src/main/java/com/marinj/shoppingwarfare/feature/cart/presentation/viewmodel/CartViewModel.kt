package com.marinj.shoppingwarfare.feature.cart.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartListEvent
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect
import com.marinj.shoppingwarfare.feature.cart.presentation.presenter.CartListPresenter
import com.marinj.shoppingwarfare.feature.cart.presentation.presenter.CartStatusPresenter
import com.marinj.shoppingwarfare.feature.cart.presentation.presenter.CartTabPresenter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    cartListPresenterFactory: CartListPresenter.Factory,
    cartStatusPresenterFactory: CartStatusPresenter.Factory,
    cartTabPresenterFactory: CartTabPresenter.Factory,
) : ViewModel() {

    val cartListPresenter = cartListPresenterFactory.create(viewModelScope).also {
        it.onEvent(CartListEvent.ObserveCartItems)
    }
    val cartStatusPresenter = cartStatusPresenterFactory.create(viewModelScope)
    val cartTabPresenter = cartTabPresenterFactory.create(viewModelScope)

    val viewEffect: Flow<CartViewEffect> = merge(
        cartListPresenter.viewEffect,
        cartStatusPresenter.viewEffect,
    )
}
