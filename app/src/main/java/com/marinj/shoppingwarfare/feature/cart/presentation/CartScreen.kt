package com.marinj.shoppingwarfare.feature.cart.presentation

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.marinj.shoppingwarfare.R.string
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareLoadingIndicator
import com.marinj.shoppingwarfare.core.ext.expandOrCollapse
import com.marinj.shoppingwarfare.core.ext.openAppSystemSettings
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CartTopBar
import com.marinj.shoppingwarfare.feature.cart.presentation.components.CartCameraPermission
import com.marinj.shoppingwarfare.feature.cart.presentation.components.CartCheckoutInfo
import com.marinj.shoppingwarfare.feature.cart.presentation.components.CartItemList
import com.marinj.shoppingwarfare.feature.cart.presentation.components.CartTabs
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect.CartViewItemDeleted
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect.Error
import com.marinj.shoppingwarfare.feature.cart.presentation.presenter.CartListPresenter
import com.marinj.shoppingwarfare.feature.cart.presentation.presenter.CartStatusPresenter
import com.marinj.shoppingwarfare.feature.cart.presentation.presenter.CartTabPresenter
import com.marinj.shoppingwarfare.feature.cart.presentation.viewmodel.CartViewModel
import com.marinj.shoppingwarfare.ui.ShoppingWarfareEmptyScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CartScreen(
    cartViewModel: CartViewModel = hiltViewModel(),
    bottomSheetScaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    context: Context = LocalContext.current,
    setupTopBar: (TopBarEvent) -> Unit,
) {
    LaunchedEffect(Unit) {
        setupTopBar(CartTopBar())
        cartViewModel.viewEffect.collect { cartEffect ->
            when (cartEffect) {
                is CartViewItemDeleted -> bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                    context.getString(
                        string.cart_item_deleted,
                        cartEffect.cartItemName,
                    ),
                )

                is Error -> bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                    message = cartEffect.errorMessage,
                )

                CartViewEffect.CartViewCheckoutCompleted -> bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                    context.getString(string.cart_success_message),
                )
            }
        }
    }

    CartScreen(
        cartListPresenter = cartViewModel.cartListPresenter,
        cartStatusPresenter = cartViewModel.cartStatusPresenter,
        cartTabPresenter = cartViewModel.cartTabPresenter,
        bottomSheetScaffoldState = bottomSheetScaffoldState,
        context = context,
    )
}

@Composable
fun CartScreen(
    cartStatusPresenter: CartStatusPresenter,
    cartListPresenter: CartListPresenter,
    cartTabPresenter: CartTabPresenter,
    bottomSheetScaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    context: Context = LocalContext.current,
) {
    val cartStatusState by cartStatusPresenter.state.collectAsState()
    val cartListState by cartListPresenter.state.collectAsState()

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            CartCameraPermission(
                onCartEvent = { cartEvent ->
                    coroutineScope.launch {
                        bottomSheetScaffoldState.expandOrCollapse()
                    }
                    cartStatusPresenter.onEvent(cartEvent)
                },
            ) {
                context.openAppSystemSettings()
            }
        },
        sheetPeekHeight = 0.dp,
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
//            CartTabs(
//                selectedTabPosition = cartTabPresenter.selectedTabPosition,
//                cartTabs = viewState.cartTabs,
//                onCartEvent = cartViewModel::onEvent,
//            )
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .fillMaxWidth(),
            ) {
                when {
                    cartListState.uiCartItems.isEmpty() -> ShoppingWarfareEmptyScreen(
                        message = stringResource(
                            string.empty_cart_message,
                        ),
                    )

                    else -> CartItemList(
                        uiCartItems = cartListState.uiCartItems,
                        onCartEvent = cartListPresenter::onEvent,
                    )
                }

                if (cartListState.isLoading) {
                    ShoppingWarfareLoadingIndicator()
                }
            }
            CartCheckoutInfo(
                viewState = cartStatusState,
                onReceiptClick = {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.expandOrCollapse()
                    }
                },
                uiCartItems = cartListState.uiCartItems,
                onCartEvent = cartStatusPresenter::onEvent,
            )
        }
    }
}