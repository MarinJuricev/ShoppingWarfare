package com.marinj.shoppingwarfare.feature.cart.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.base.TIMEOUT_DELAY
import com.marinj.shoppingwarfare.core.ext.combine
import com.marinj.shoppingwarfare.core.mapper.FailureToStringMapper
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.CheckoutCart
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.DeleteCartItem
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.ObserveCartItems
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.UpdateCartItemIsInBasket
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.UpdateCartItemQuantity
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.ValidateReceiptPath
import com.marinj.shoppingwarfare.feature.cart.presentation.mapper.CartItemToUiCartItemMapper
import com.marinj.shoppingwarfare.feature.cart.presentation.mapper.UiCartItemToCartItemMapper
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.CartItemQuantityChanged
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.CartNameUpdated
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.CartTabPositionUpdated
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.CheckoutClicked
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.ItemAddedToBasket
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.OnGetCartItems
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.ReceiptCaptureError
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.ReceiptCaptureSuccess
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartTab
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect.CartViewCheckoutCompleted
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect.CartViewItemDeleted
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect.Error
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewState
import com.marinj.shoppingwarfare.feature.cart.presentation.model.ReceiptStatus
import com.marinj.shoppingwarfare.feature.cart.presentation.model.UiCartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val observeCartItems: ObserveCartItems,
    private val deleteCartItem: DeleteCartItem,
    private val updateCartItemQuantity: UpdateCartItemQuantity,
    private val updateCartItemIsInBasket: UpdateCartItemIsInBasket,
    private val checkoutCart: CheckoutCart,
    private val validateReceiptPath: ValidateReceiptPath,
    private val failureToStringMapper: FailureToStringMapper,
    private val cartItemToUiCartItemMapper: CartItemToUiCartItemMapper,
    private val uiCartItemToCartItemMapper: UiCartItemToCartItemMapper,
) : BaseViewModel<CartEvent>() {

    private val isLoading = MutableStateFlow(true)
    private val receiptStatus = MutableStateFlow<ReceiptStatus>(ReceiptStatus.Empty)
    private val cartName = MutableStateFlow("")
    private val isPremiumUser = MutableStateFlow(false)
    private val selectedTabPosition = MutableStateFlow(0)
    private val cartTabs = MutableStateFlow(
        listOf(
            CartTab(0, R.string.pending_tab),
            CartTab(1, R.string.approved_tab),
        ),
    )
    private val uiCartItems = MutableStateFlow(emptyList<UiCartItem>())

    val viewState = combine(
        isLoading,
        receiptStatus,
        cartName,
        isPremiumUser,
        selectedTabPosition,
        cartTabs,
        uiCartItems,
        ::CartViewState,
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_DELAY),
        initialValue = CartViewState(),
    )

    private val _viewEffect = Channel<CartViewEffect>(Channel.BUFFERED)
    val viewEffect = _viewEffect.receiveAsFlow()

    override fun onEvent(event: CartEvent) {
        when (event) {
            OnGetCartItems -> handleGetCartItems()
            ReceiptCaptureError -> receiptStatus.update { ReceiptStatus.Error }
            CheckoutClicked -> handleCheckoutClicked()
            is CartEvent.DeleteCartItem -> handleDeleteCartItem(event.cartItem)
            is CartItemQuantityChanged -> handleCartItemQuantityChanged(
                event.cartItemToUpdate,
                event.newQuantity,
            )

            is ReceiptCaptureSuccess -> receiptStatus.update { validateReceiptPath(event.receiptPath) }
            is CartNameUpdated -> cartName.update { event.updatedCartName }
            is ItemAddedToBasket -> handleItemAddedToBasket(event.cartItem)
            is CartTabPositionUpdated -> selectedTabPosition.update { event.updatedCartTabPosition }
        }
    }

    private fun handleGetCartItems() = viewModelScope.launch {
        observeCartItems()
            .onStart { isLoading.update { true } }
            .catch { handleGetCartItemsError() }
            .map(cartItemToUiCartItemMapper::map)
            .collect { cartItems ->
                isLoading.update { false }
                uiCartItems.update { cartItems }
            }
    }

    private fun handleCheckoutClicked() = viewModelScope.launch {
        val viewState = viewState.value
        checkoutCart(
            cartItems = uiCartItemToCartItemMapper.map(viewState.uiCartItems),
            cartName = viewState.cartName,
            receiptPath = viewState.receiptStatus.receiptPath,
        ).fold(
            ifLeft = { _viewEffect.send(Error(failureToStringMapper.map(it))) },
            ifRight = { _viewEffect.send(CartViewCheckoutCompleted) },
        )
    }

    private suspend fun handleGetCartItemsError() {
        isLoading.update { false }
        _viewEffect.send(Error("Failed to fetch cart items, please try again later."))
    }

    private fun handleDeleteCartItem(uiCartItem: UiCartItem.Content) = viewModelScope.launch {
        deleteCartItem(uiCartItem.id).fold(
            ifLeft = { _viewEffect.send(Error("Failed to delete ${uiCartItem.name}, please try again later.")) },
            ifRight = { _viewEffect.send(CartViewItemDeleted(uiCartItem.name)) },
        )
    }

    private fun handleCartItemQuantityChanged(
        uiCartItem: UiCartItem.Content,
        newQuantity: Int,
    ) = viewModelScope.launch {
        updateCartItemQuantity(uiCartItem.id, newQuantity).fold(
            ifLeft = { _viewEffect.send(Error("Failed to update ${uiCartItem.name}, please try again later")) },
            ifRight = { Timber.d("${uiCartItem.name} successfully updated with $newQuantity quantity") },
        )
    }

    private fun handleItemAddedToBasket(
        uiCartItem: UiCartItem.Content,
    ) = viewModelScope.launch {
        updateCartItemIsInBasket(uiCartItem.id, !uiCartItem.isInBasket).fold(
            ifLeft = { _viewEffect.send(Error("Failed to update ${uiCartItem.name}, please try again later")) },
            ifRight = { Timber.d("${uiCartItem.name} successfully updated with ${!uiCartItem.isInBasket} isInBasket status") },
        )
    }
}
