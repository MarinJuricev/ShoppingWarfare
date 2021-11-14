package com.marinj.shoppingwarfare.feature.cart.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.ext.safeUpdate
import com.marinj.shoppingwarfare.core.mapper.FailureToStringMapper
import com.marinj.shoppingwarfare.core.result.Either.Left
import com.marinj.shoppingwarfare.core.result.Either.Right
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.CheckoutCart
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.DeleteCartItem
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.ObserveCartItems
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.UpdateCartItemQuantity
import com.marinj.shoppingwarfare.feature.cart.presentation.mapper.CartItemsToCartDataMapper
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.CartItemQuantityChanged
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.CartNameUpdated
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.CheckoutClicked
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.OnGetCartItems
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.ReceiptCaptureError
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.ReceiptCaptureSuccess
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect.CartViewCheckoutCompleted
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect.CartViewItemDeleted
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect.Error
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewState
import com.marinj.shoppingwarfare.feature.cart.presentation.model.ReceiptStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val observeCartItems: ObserveCartItems,
    private val deleteCartItem: DeleteCartItem,
    private val updateCartItemQuantity: UpdateCartItemQuantity,
    private val checkoutCart: CheckoutCart,
    private val cartItemsToCartDataMapper: CartItemsToCartDataMapper,
    private val failureToStringMapper: FailureToStringMapper,
) : BaseViewModel<CartEvent>() {

    private val _viewState = MutableStateFlow(CartViewState())
    val viewState = _viewState.asStateFlow()

    private val _viewEffect = Channel<CartViewEffect>()
    val viewEffect = _viewEffect.receiveAsFlow()

    override fun onEvent(event: CartEvent) {
        when (event) {
            OnGetCartItems -> handleGetCartItems()
            ReceiptCaptureError -> handleReceiptCaptureError()
            CheckoutClicked -> handleCheckoutClicked()
            is CartEvent.DeleteCartItem -> handleDeleteCartItem(event.cartItem)
            is CartItemQuantityChanged -> handleCartItemQuantityChanged(
                event.cartItemToUpdate,
                event.newQuantity
            )
            is ReceiptCaptureSuccess -> handleReceiptCaptureSuccess(event.receiptPath)
            is CartNameUpdated -> handleCartNameUpdated(event.updatedCartName)
        }
    }

    private fun handleGetCartItems() = viewModelScope.launch {
        observeCartItems()
            .onStart { updateIsLoading(isLoading = true) }
            .catch { handleGetCartItemsError() }
            .collect { cartItems ->
                _viewState.safeUpdate(
                    _viewState.value.copy(
                        cartData = cartItemsToCartDataMapper.map(cartItems),
                        cartItems = cartItems,
                        isLoading = false,
                    )
                )
            }
    }

    private fun handleReceiptCaptureError() {
        _viewState.safeUpdate(
            _viewState.value.copy(receiptStatus = ReceiptStatus.Error)
        )
    }

    private fun handleCheckoutClicked() = viewModelScope.launch {
        val viewState = viewState.value
        when (
            val result = checkoutCart(
                cartData = viewState.cartData,
                cartName = viewState.cartName,
                receiptPath = viewState.receiptStatus.receiptPath
            )
        ) {
            is Right -> _viewEffect.send(CartViewCheckoutCompleted)
            is Left -> _viewEffect.send(Error(failureToStringMapper.map(result.error)))
        }
    }

    private suspend fun handleGetCartItemsError() {
        updateIsLoading(false)
        _viewEffect.send(Error("Failed to fetch cart items, please try again later."))
    }

    private fun handleDeleteCartItem(cartItem: CartItem) = viewModelScope.launch {
        when (deleteCartItem(cartItem.id)) {
            is Right -> _viewEffect.send(CartViewItemDeleted(cartItem.name))
            is Left -> _viewEffect.send(Error("Failed to delete ${cartItem.name}, please try again later."))
        }
    }

    private fun handleCartItemQuantityChanged(
        cartItemToUpdate: CartItem,
        newQuantity: Int,
    ) = viewModelScope.launch {
        when (updateCartItemQuantity(cartItemToUpdate, newQuantity)) {
            is Right -> Timber.d("${cartItemToUpdate.name} successfully updated with $newQuantity quantity")
            is Left -> _viewEffect.send(Error("Failed to update ${cartItemToUpdate.name}, please try again later"))
        }
    }

    private fun handleReceiptCaptureSuccess(receiptPath: String) {
        _viewState.safeUpdate(
            _viewState.value.copy(receiptStatus = ReceiptStatus.Taken(receiptPath))
        )
    }

    private fun handleCartNameUpdated(updatedCartName: String) {
        _viewState.safeUpdate(
            _viewState.value.copy(cartName = updatedCartName)
        )
    }

    private fun updateIsLoading(isLoading: Boolean) {
        _viewState.safeUpdate(
            _viewState.value.copy(
                isLoading = isLoading
            )
        )
    }
}
