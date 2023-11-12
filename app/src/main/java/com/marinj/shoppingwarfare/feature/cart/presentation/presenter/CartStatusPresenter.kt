package com.marinj.shoppingwarfare.feature.cart.presentation.presenter

import com.marinj.shoppingwarfare.core.base.TIMEOUT_DELAY
import com.marinj.shoppingwarfare.core.mapper.FailureToStringMapper
import com.marinj.shoppingwarfare.core.presenter.BasePresenter
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.CheckoutCart
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.ValidateReceiptPath
import com.marinj.shoppingwarfare.feature.cart.presentation.mapper.UiCartItemToCartItemMapper
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartStatusEvent
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartStatusEvent.CartNameUpdated
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartStatusEvent.CheckoutClicked
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartStatusEvent.ReceiptCaptureError
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartStatusEvent.ReceiptCaptureSuccess
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartStatusState
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect
import com.marinj.shoppingwarfare.feature.cart.presentation.model.ReceiptStatus
import com.marinj.shoppingwarfare.feature.cart.presentation.model.UiCartItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartStatusPresenter @AssistedInject constructor(
    @Assisted val coroutineScope: CoroutineScope,
    private val checkoutCart: CheckoutCart,
    private val validateReceiptPath: ValidateReceiptPath,
    private val failureToStringMapper: FailureToStringMapper,
    private val uiCartItemToCartItemMapper: UiCartItemToCartItemMapper,
) : BasePresenter<CartStatusEvent> {

    private val receiptStatus = MutableStateFlow<ReceiptStatus>(ReceiptStatus.Empty)
    private val cartName = MutableStateFlow("")

    val state = combine(
        receiptStatus,
        cartName,
        ::CartStatusState,
    ).stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_DELAY),
        initialValue = CartStatusState(),
    )

    private val _viewEffect = Channel<CartViewEffect>(Channel.BUFFERED)
    val viewEffect = _viewEffect.receiveAsFlow()
    override fun onEvent(event: CartStatusEvent) {
        when (event) {
            ReceiptCaptureError -> receiptStatus.update { ReceiptStatus.Error }
            is CartNameUpdated -> cartName.update { event.updatedCartName }
            is CheckoutClicked -> handleCheckoutClicked(event.uiCartItems)
            is ReceiptCaptureSuccess -> receiptStatus.update { validateReceiptPath(event.receiptPath) }
        }
    }

    private fun handleCheckoutClicked(
        cartItems: List<UiCartItem>,
    ) = coroutineScope.launch {
        checkoutCart(
            cartItems = uiCartItemToCartItemMapper.map(cartItems),
            cartName = cartName.value,
            receiptPath = receiptStatus.value.receiptPath,
        ).fold(
            ifLeft = { _viewEffect.send(CartViewEffect.Error(failureToStringMapper.map(it))) },
            ifRight = { _viewEffect.send(CartViewEffect.CartViewCheckoutCompleted) },
        )
    }
}
