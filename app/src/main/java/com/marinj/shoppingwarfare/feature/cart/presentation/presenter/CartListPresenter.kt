package com.marinj.shoppingwarfare.feature.cart.presentation.presenter

import com.marinj.shoppingwarfare.core.base.TIMEOUT_DELAY
import com.marinj.shoppingwarfare.core.presenter.BasePresenter
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.DeleteCartItem
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.ObserveCartItems
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.UpdateCartItemIsInBasket
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.UpdateCartItemQuantity
import com.marinj.shoppingwarfare.feature.cart.presentation.mapper.CartItemToUiCartItemMapper
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartListEvent
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartListState
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect
import com.marinj.shoppingwarfare.feature.cart.presentation.model.UiCartItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class CartListPresenter @AssistedInject constructor(
    @Assisted private val coroutineScope: CoroutineScope,
    private val observeCartItems: ObserveCartItems,
    private val deleteCartItem: DeleteCartItem,
    private val updateCartItemQuantity: UpdateCartItemQuantity,
    private val updateCartItemIsInBasket: UpdateCartItemIsInBasket,
    private val cartItemToUiCartItemMapper: CartItemToUiCartItemMapper,
) : BasePresenter<CartListEvent> {

    @AssistedFactory
    interface Factory {
        fun create(coroutineScope: CoroutineScope): CartListPresenter
    }

    private val isLoading = MutableStateFlow(true)
    private val uiCartItems = MutableStateFlow(emptyList<UiCartItem>())

    val state = combine(
        isLoading,
        uiCartItems,
        ::CartListState,
    ).stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_DELAY),
        initialValue = CartListState(),
    )

    private val _viewEffect = Channel<CartViewEffect>(Channel.BUFFERED)
    val viewEffect = _viewEffect.receiveAsFlow()

    override fun onEvent(event: CartListEvent) {
        when (event) {
            is CartListEvent.DeleteCartItem -> handleDeleteCartItem(event.uiCartItem)
            is CartListEvent.CartItemQuantityChanged -> handleCartItemQuantityChanged(
                event.cartItemToUpdate,
                event.newQuantity,
            )
            is CartListEvent.ItemAddedToBasket -> handleItemAddedToBasket(event.cartItem)
            CartListEvent.ObserveCartItems -> handleObserveCartItems()
        }
    }

    private fun handleObserveCartItems() = observeCartItems()
        .onStart { isLoading.update { true } }
        .catch { handleGetCartItemsError() }
        .map(cartItemToUiCartItemMapper::map)
        .onEach { cartItems ->
            isLoading.update { false }
            uiCartItems.update { cartItems }
        }
        .launchIn(coroutineScope)

    private suspend fun handleGetCartItemsError() {
        isLoading.update { false }
        _viewEffect.send(CartViewEffect.Error("Failed to fetch cart items, please try again later."))
    }

    private fun handleDeleteCartItem(uiCartItem: UiCartItem.Content) = coroutineScope.launch {
        deleteCartItem(uiCartItem.id).fold(
            ifLeft = { _viewEffect.send(CartViewEffect.Error("Failed to delete ${uiCartItem.name}, please try again later.")) },
            ifRight = { _viewEffect.send(CartViewEffect.CartViewItemDeleted(uiCartItem.name)) },
        )
    }

    private fun handleCartItemQuantityChanged(
        uiCartItem: UiCartItem.Content,
        newQuantity: Int,
    ) = coroutineScope.launch {
        updateCartItemQuantity(uiCartItem.id, newQuantity).fold(
            ifLeft = { _viewEffect.send(CartViewEffect.Error("Failed to update ${uiCartItem.name}, please try again later")) },
            ifRight = { Timber.d("${uiCartItem.name} successfully updated with $newQuantity quantity") },
        )
    }

    private fun handleItemAddedToBasket(
        uiCartItem: UiCartItem.Content,
    ) = coroutineScope.launch {
        updateCartItemIsInBasket(uiCartItem.id, !uiCartItem.isInBasket).fold(
            ifLeft = { _viewEffect.send(CartViewEffect.Error("Failed to update ${uiCartItem.name}, please try again later")) },
            ifRight = { Timber.d("${uiCartItem.name} successfully updated with ${!uiCartItem.isInBasket} isInBasket status") },
        )
    }
}
